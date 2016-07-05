package lvhong.tim.atm;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lvhong.tim.atm.adapter.ATMArrayAdapter;
import lvhong.tim.atm.cl.QuickActionDialog;
import lvhong.tim.atm.data.ATMNode;
import lvhong.tim.atm.data.QActionItem;
import lvhong.tim.atm.parserdata.JSONParser;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class _ketquatimkiem extends Activity{
		

	@Override
	public void finish() {
		super.finish();
	}

	private TextView txt_bank, txt_khuvuc;
	private ListView listView;
	private int kieutimkiem;
	private static String tennganhang, tentinh="", tenquan, tenduong;
	
	private static final int ID_KHOANGCACH     = 1;
	private static final int ID_DANHDAU   = 2;
	private static final int ID_VEDUONG = 3;
	private static final int ID_SMS   = 4;

	private LocationManager locationManager;
	private LocationListener locationListener;
	private Location location;
	
	private class task_TKQuan extends AsyncTask<Void, Void, ArrayList<ATMNode>>{
		String tennganhang,tenquan;
		ProgressDialog MyDialog = new ProgressDialog(_ketquatimkiem.this);
		
		public task_TKQuan(String tennganhang, String tenquan){
			this.tennganhang=tennganhang;
			this.tenquan = tenquan;
		}
		
		@Override
		protected ArrayList<ATMNode> doInBackground(Void... params) {
			ArrayList<ATMNode> arrayList_TKQuan = new ArrayList<ATMNode>();
			ArrayList<NameValuePair> _REQUEST = new ArrayList<NameValuePair>();
			_REQUEST.add(new BasicNameValuePair("tennganhang",tennganhang));
			_REQUEST.add(new BasicNameValuePair("tenquan",tenquan));
			try{
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost("http://qhoang.org/webservices/TKQuan.php");
				httpPost.setEntity(new UrlEncodedFormEntity(_REQUEST,"UTF-8"));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				arrayList_TKQuan = TimKiemQuan(httpEntity);
				}catch(Exception e){
			}
			return arrayList_TKQuan;
		}

		@Override
		protected void onPostExecute(ArrayList<ATMNode> result) {
			MyDialog.dismiss();
			ArrayAdapter<ATMNode> arrayAdapter_TKQuan = new ATMArrayAdapter(_ketquatimkiem.this,result);
			arrayAdapter_TKQuan.setDropDownViewResource(android.R.layout.simple_list_item_2);
			listView.setAdapter(arrayAdapter_TKQuan);
		}

		@Override
		protected void onPreExecute() {
			MyDialog.setMessage("Đang tìm kiếm...");
			MyDialog.show();
		}
	}

	private class task_TKDuong extends AsyncTask<Void, Void, ArrayList<ATMNode>>{
		String tentinh, tenduong;
		ProgressDialog MyDialog = new ProgressDialog(_ketquatimkiem.this);
		
		public task_TKDuong(String tentinh, String tenduong){
			this.tentinh = tentinh;
			this.tenduong = tenduong;
		}
		
		@Override
		protected ArrayList<ATMNode> doInBackground(Void... params) {
			ArrayList<ATMNode> arrayList_TKDuong = new ArrayList<ATMNode>();
			ArrayList<NameValuePair> _REQUEST = new ArrayList<NameValuePair>();
			_REQUEST.add(new BasicNameValuePair("tenduong",tenduong.toUpperCase()));
			try{
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost("http://qhoang.org/webservices/TKDuong.php");
				httpPost.setEntity(new UrlEncodedFormEntity(_REQUEST,"UTF-8"));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				String tenDuong = getTenDuong(httpEntity);
				arrayList_TKDuong = TimKiemDuong(tenDuong.replace(" ", "%20"),tentinh.replace(" ", "%20"));
			}catch(Exception e){	}
			return arrayList_TKDuong;			
		}

		@Override
		protected void onPostExecute(ArrayList<ATMNode> result) {
			MyDialog.dismiss();
			ArrayAdapter<ATMNode> arrayAdapter_TKDuong = new ATMArrayAdapter(_ketquatimkiem.this,result);
			listView.setAdapter(arrayAdapter_TKDuong);
		}

		@Override
		protected void onPreExecute() {
			MyDialog.setMessage("Đang tìm kiếm...");
			MyDialog.show();
		}
	}

	public ArrayList<ATMNode> TimKiemQuan(HttpEntity httpEntity){
		ArrayList<ATMNode> arrayList_ATM = new ArrayList<ATMNode>();
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = jsonParser.getJSONFromRESPONSE(httpEntity);
		int i = 0;
		try{
			JSONArray array_atm = jsonObject.getJSONArray("list_atm");
			for(i=0;i<array_atm.length();i++){
				JSONObject node = array_atm.getJSONObject(i);
				JSONObject c = node.getJSONObject("node_list_atm");
				
				ATMNode a = new ATMNode();
				a.set_tendd(c.getString("tendiadiem"));
				a.set_diachi(c.getString("diachi"));
				a.set_lat(c.getString("lat"));
				a.set_lng(c.getString("lng"));
				arrayList_ATM.add(a);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		return arrayList_ATM;
	}
	
	public GeoPoint getGeoPointFromAdd(String tenDuong, String tenTinh){
		Double _lat, _lng;
		String atm_lat = "", atm_lng="";
		String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+tenDuong+","+tenTinh+",Vietnam&sensor=true";
		JSONParser jsonParser = new JSONParser();
		JSONObject json = jsonParser.getJSONFromURL(url);
		int i=0;
		try{
			JSONArray array_Duong = json.getJSONArray("results");
			JSONObject c = array_Duong.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");			
			atm_lat = c.getString("lat");
			atm_lng = c.getString("lng");			
		}	catch(JSONException e){
			e.printStackTrace();
		}
		_lat = Double.parseDouble(atm_lat);
		_lng = Double.parseDouble(atm_lng);
		return new GeoPoint((int)(_lat * 1E6), (int) (_lng * 1E6));
	}
	
	public ArrayList<ATMNode> TimKiemDuong(String tenDuong, String tenTinh){
		ArrayList<ATMNode> arrayList_ATM = new ArrayList<ATMNode>();
		
		GeoPoint p = getGeoPointFromAdd(tenDuong, tenTinh);
		Double _lat = p.getLatitudeE6()/1E6;
		Double _lng = p.getLongitudeE6()/1E6;
		
		JSONParser jsonParser = new JSONParser();
		String url = "https://maps.googleapis.com/maps/api/place/search/json?location="+_lat+"%2C"+_lng+"&name=atm&radius=300&sensor=false&key=0ldnGXI3TNEPi_dixF4e8utg9HCQtBUVFrsPtnA";
		JSONObject json = jsonParser.getJSONFromURL(url);
		int i=0;
		try{
			JSONArray array_atm = json.getJSONArray("results");
			for(i=0;i<array_atm.length();i++){
				JSONObject c = array_atm.getJSONObject(i);

				ATMNode a = new ATMNode();
				a.set_tendd(c.getString("name"));
				a.set_diachi(c.getString("vicinity"));
				
				JSONObject geometry  = c.getJSONObject("geometry");
				JSONObject location = geometry.getJSONObject("location");
				String atm_lat = location.getString("lat");
				String atm_lng = location.getString("lng");
				a.set_lat(atm_lat);
				a.set_lng(atm_lng);
				arrayList_ATM.add(a);
			}
		}	catch(JSONException e){
			e.printStackTrace();
		}
		return arrayList_ATM;
	}
	
	public String getTenDuong(HttpEntity httpEntity){
		String tenDuong = "";
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = jsonParser.getJSONFromRESPONSE(httpEntity);
		try{
			JSONArray array_tenDuong = jsonObject.getJSONArray("list_duong");
			tenDuong = array_tenDuong.getJSONObject(0).getJSONObject("node_duong").getString("codau");
		}catch(JSONException e){
			e.printStackTrace();
		}
		return tenDuong;
	}
	
	public void updateLocation(Location location){
		this.location = location;
	}
	
	public String getDISTANCE(Location loc, ATMNode a){
		String khoangcach = "";
		
		Double lat_1 = loc.getLatitude();
		Double lng_1 = loc.getLongitude();
		String lat_2 = a.get_lat();
		String lng_2 = a.get_lng();
		
		JSONParser jsonParser = new JSONParser();
		String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+lat_1.toString()+","+lng_1.toString()+"&destination="+lat_2+","+lng_2+"&sensor=false";
		JSONObject json = jsonParser.getJSONFromURL(url);
		try{
			JSONArray array_ = json.getJSONArray("routes");
			JSONObject route = array_.getJSONObject(0);
			JSONArray legs = route.getJSONArray("legs");
			JSONObject leg = legs.getJSONObject(0);
			khoangcach = leg.getJSONObject("distance").getString("text");
		}catch(JSONException e){
			e.printStackTrace();
		}
		return khoangcach;
	}
	
	public Dialog sendSMS(String sms_content){
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		dialog.setTitle("Gửi SMS");
		dialog.setCancelable(true);
		
		View content = View.inflate(this, R.layout.dialogsms, null);
		final Spinner spn = (Spinner)content.findViewById(R.id.spn_contatcs);
	    final TextView tenso = (TextView)content.findViewById(R.id.txt_phone);
	    final Button btt_sendSMS = (Button)content.findViewById(R.id.btt_guiSMS);
	    final Button btt_boqua = (Button)content.findViewById(R.id.btt_boqua);
	    final EditText edt_noiding = (EditText)content.findViewById(R.id.edt_noidung1);
	    
	    edt_noiding.setText(sms_content);
	    
	    final ArrayList<String> ten = new ArrayList<String>();
	    final ArrayList<String> so = new ArrayList<String>();
	        
		Cursor phones = _ketquatimkiem.this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
			while (phones.moveToNext()){
				String phoneName=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				ten.add(phoneName);
				so.add(phoneNumber);
			}
		phones.close();
			
		ArrayAdapter<String> arrayAdapter_Contacts = new ArrayAdapter<String>(_ketquatimkiem.this,android.R.layout.simple_spinner_item,ten);
		arrayAdapter_Contacts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn.setAdapter(arrayAdapter_Contacts);
		spn.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				tenso.setText("Số ĐT:  "+so.get(arg2).toString());	
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		btt_sendSMS.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}		
		});
		
		btt_boqua.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}		
		});
		
		dialog.setContentView(content);
		return dialog;
	}
	
	public Intent backActivity(Context context){
		Intent intent = new Intent();
		super.finish();
		return intent;
	}
	
	private class task_getKQ extends AsyncTask<Void,Void,String>{
		Location loc;
		ATMNode node;
		ProgressDialog MyDialog = new ProgressDialog(_ketquatimkiem.this);
		public task_getKQ(Location loc, ATMNode node){
			this.loc = loc;
			this.node = node;
		}
		@Override
		protected String doInBackground(Void... arg0) {
			return getDISTANCE(loc,node);
		}

		@Override
		protected void onPostExecute(String result) {
			MyDialog.dismiss();
			Toast.makeText(getApplicationContext(), "Khoảng cách từ vị trí hiện tại tới máy ATM này là  "+result, Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onPreExecute() {
			MyDialog.setMessage("Đang tính khoảng cách.....");
			MyDialog.show();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ketquatimkiem);
		
		final ActionBar actionBar = (ActionBar)findViewById(R.id.actionBar);
		actionBar.setTitle("Kết quả tìm kiếm");
		final Action backAction = new IntentAction(this, _timkiem.createIntent(this), R.drawable.back);
	    	actionBar.addAction(backAction);
		final Action homeAction = new IntentAction(this, _main.createIntent(this), R.drawable.home);
		actionBar.addAction(homeAction);
		
		txt_bank = (TextView)findViewById(R.id.txt_bank);
		txt_khuvuc = (TextView)findViewById(R.id.txt_khuvuc);
		listView = (ListView)findViewById(R.id.listView);
		
		locationListener = new MyLocationListener();
		locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		/*
		Criteria criteria = new Criteria(); 
		criteria.setAccuracy(Criteria.ACCURACY_COARSE); 
		String bestProvider= locationManager.getBestProvider(criteria, true); 
		location = locationManager.getLastKnownLocation(bestProvider);
		updateLocation(location);
		*/
		
		//----ĐH Bach Khoa Da Nang------Dung de test
		location = new Location("tag");
		Double lat1 = 16.074269;
		Double lng1 = 108.149775;
		location.setLatitude(lat1);
		location.setLongitude(lng1);
		updateLocation(location);
		//----ĐH Bach Khoa Da Nang--------------------------
		Bundle bundle = getIntent().getExtras();
		kieutimkiem = bundle.getInt("kieutimkiem");

	//	boolean statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		if(kieutimkiem == 1){
				tentinh = bundle.getString("tentinh");
				tenduong = bundle.getString("tenduong");
				txt_bank.setText("ATM của tất cả các ngân hàng");
				txt_khuvuc.setText(tenduong);
				txt_khuvuc.setTextColor(-65536);
				
				task_TKDuong b = new task_TKDuong(tentinh,tenduong);
				b.execute();
			}else{
				tennganhang = bundle.getString("tennganhang");
				tenquan = bundle.getString("tenquan");
				txt_bank.setText(tennganhang);
				txt_khuvuc.setText(tenquan);
				txt_khuvuc.setTextColor(-65536);
				
				task_TKQuan a = new task_TKQuan(tennganhang,tenquan);
				a.execute();
			}
			
			final QActionItem Qkhoangcach 	= new QActionItem(ID_KHOANGCACH, "Khoảng cách", getResources().getDrawable(R.drawable.khoangcach));
			final QActionItem Qdanhdau 	= new QActionItem(ID_DANHDAU, "Đánh dấu", getResources().getDrawable(R.drawable.danhdau));
	        final QActionItem Qduongdi 	= new QActionItem(ID_VEDUONG, "Đường đi", getResources().getDrawable(R.drawable.duongdi));
	        final QActionItem Qsms 	= new QActionItem(ID_SMS, "Gửi SMS", getResources().getDrawable(R.drawable.sms));
	       
	        listView.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					
					final QuickActionDialog quickAction = new QuickActionDialog(arg1.getContext(), QuickActionDialog.VERTICAL);   
					final ATMNode a = (ATMNode)listView.getItemAtPosition(arg2);
					final Double lat_loc = location.getLatitude();
					final Double lng_loc = location.getLongitude();
					
			        quickAction.setOnActionItemClickListener(new QuickActionDialog.OnActionItemClickListener() {		
						@Override
						public void onItemClick(QuickActionDialog source, int pos, int actionId) {					
							if(actionId == ID_KHOANGCACH){
								task_getKQ task = new task_getKQ(location,a);
								task.execute();
							}
							if(actionId == ID_DANHDAU){
								Intent intent = new Intent("lvhong.tim.atm.KQMAP");
								Bundle bundle = new Bundle();
								
								bundle.putString("tenatm", a.get_tendd());
								bundle.putString("diachi", a.get_diachi());
								bundle.putString("latATM", a.get_lat());
								bundle.putString("lngATM", a.get_lng());
								bundle.putString("latLoc", lat_loc.toString());
								bundle.putString("lngLoc", lng_loc.toString());
								bundle.putInt("kieu", 1);
								intent.putExtras(bundle);
								startActivity(intent);
							}
							if(actionId == ID_VEDUONG){
								Intent intent = new Intent("lvhong.tim.atm.KQMAP");
								Bundle bundle = new Bundle();
															
								bundle.putString("tenatm", a.get_tendd());
								bundle.putString("diachi", a.get_diachi());
								bundle.putString("latATM", a.get_lat());
								bundle.putString("lngATM", a.get_lng());
								bundle.putString("latLoc", lat_loc.toString());
								bundle.putString("lngLoc", lng_loc.toString());
								bundle.putInt("kieu", 2);
								intent.putExtras(bundle);
								startActivity(intent);
							}
							if(actionId == ID_SMS){
								String sms_content = a.get_tendd()+"\n"+a.get_diachi();
								Dialog dialog= sendSMS(sms_content);
								dialog.show();
								dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.send);
							}
						}
					});
			        
			        quickAction.setOnDismissListener(new QuickActionDialog.OnDismissListener() {
						@Override
						public void onDismiss() {
						}
					});
			        quickAction.addActionItem(Qkhoangcach);
			        quickAction.addActionItem(Qdanhdau);
			        quickAction.addActionItem(Qduongdi);
			        quickAction.addActionItem(Qsms);
					quickAction.show(arg1);
				}
	        });
		}	

	public class MyLocationListener implements LocationListener{
		@Override
		public void onLocationChanged(Location arg0) {
			updateLocation(arg0);
		}
		@Override
		public void onProviderDisabled(String arg0) {
			Toast.makeText(getApplicationContext(),"Hãy bật GPS để sử dụng chức năng này!",Toast.LENGTH_SHORT ).show();
		}
		@Override
		public void onProviderEnabled(String arg0) {
		}
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		}	
	}
}
