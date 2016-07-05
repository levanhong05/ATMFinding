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

import lvhong.tim.atm.parserdata.JSONParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class _timkiem extends Activity{

	private TextView txt_quan;
	private EditText edt_tenduong;
	private Spinner spn_bank,spn_tinh,spn_quan;
	private CheckBox cbx_timkiem;
	private Button btt_timkiem;
	private static int kieutimkiem;
	
    private class Task_GetTinh extends AsyncTask<Void, Void, ArrayList<String>>{
    	ProgressDialog MyDialog = new ProgressDialog(_timkiem.this);
    	@Override
		protected void onPreExecute() {
    		MyDialog.setMessage("Đang load dữ liệu tỉnh!");
    		MyDialog.show();
		}

		@Override
		protected ArrayList<String> doInBackground(Void... params) {
    		ArrayList<String> arrayBankL = new ArrayList<String>();
    		String url="http://qhoang.org/webservices/get_list_provinces.php";
    		JSONParser jsonParser = new JSONParser();
    		JSONObject jsonObject = jsonParser.getJSONFromURL(url);
    		int i = 0;
    		try{
    			JSONArray array_provinces = jsonObject.getJSONArray("list_provinces");
    			for(i=0;i<array_provinces.length();i++){
    				JSONObject node = array_provinces.getJSONObject(i);
    				JSONObject c = node.getJSONObject("node_list_provinces");
    				String nameprovince = c.getString("tentinh");
    				arrayBankL.add(nameprovince);
    			}
    		}catch(JSONException e){
    			e.printStackTrace();
    		}
			return arrayBankL;
		}
    	
    	@Override
		protected void onPostExecute(ArrayList<String> result) {
    		MyDialog.dismiss();
	        ArrayAdapter<String> arrayAdapter_tinh = new ArrayAdapter<String>(_timkiem.this,android.R.layout.simple_spinner_item,result);
			arrayAdapter_tinh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spn_tinh.setAdapter(arrayAdapter_tinh);
		}
    }
    private class Task_GetBank extends AsyncTask<Void, Void, ArrayList<String>>{
    	ProgressDialog MyDialog = new ProgressDialog(_timkiem.this);
    	@Override
		protected void onPreExecute() {
    		MyDialog.setMessage("Đang load dữ liệu ngân hàng!");
    		MyDialog.show();
		}

		@Override
		protected ArrayList<String> doInBackground(Void... params) {
    		ArrayList<String> arrayBankL = new ArrayList<String>();
    		String url="http://qhoang.org/webservices/get_list_bank.php";
    		JSONParser jsonParser = new JSONParser();
    		JSONObject jsonObject = jsonParser.getJSONFromURL(url);
    		int i = 0;
    		try{
    			JSONArray array_bank = jsonObject.getJSONArray("list_bank");
    			for(i=0;i<array_bank.length();i++){
    				JSONObject node = array_bank.getJSONObject(i);
    				JSONObject c = node.getJSONObject("node_list_bank");
    				String namebank = c.getString("tennganhang");
    				arrayBankL.add(namebank);
    			}
    		}catch(JSONException e){
    			e.printStackTrace();
    		}
			return arrayBankL;
		}
    	
    	@Override
		protected void onPostExecute(ArrayList<String> result) {
    		MyDialog.dismiss();
	        ArrayAdapter<String> arrayAdapter_bank = new ArrayAdapter<String>(_timkiem.this,android.R.layout.simple_spinner_item,result);
			arrayAdapter_bank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spn_bank.setAdapter(arrayAdapter_bank);
			Task_GetTinh b = new Task_GetTinh();
		    b.execute();
		}
    }    
	
	public ArrayList<String> get_list_district(HttpEntity httpEntity){
		ArrayList<String> arrayList_district = new ArrayList<String>();
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = jsonParser.getJSONFromRESPONSE(httpEntity);
		int i = 0;
		try{
			JSONArray array_district = jsonObject.getJSONArray("list_district");
			for(i=0;i<array_district.length();i++){
				JSONObject node = array_district.getJSONObject(i);
				JSONObject c = node.getJSONObject("node_list_district");
				String namedistrict = c.getString("tenquan");
				arrayList_district.add(namedistrict);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		return arrayList_district;
	}
	
	public boolean checkInternet(){
		ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if(networkInfo!=null&&networkInfo.isConnectedOrConnecting())
			return true;
		else
			return false;
	}
	
	 public static Intent createIntent(Context context) {
	        Intent i = new Intent(context, _timkiem.class);
	        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        return i;
	    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timkiem);
		
		final ActionBar actionBar = (ActionBar)findViewById(R.id.actionBar);
		actionBar.setTitle("Tìm Kiếm");
		
		 final Action backAction = new IntentAction(this, _main.createIntent(this), R.drawable.back);
	     actionBar.addAction(backAction);
	     final Action homeAction = new IntentAction(this, _main.createIntent(this), R.drawable.home);
		actionBar.addAction(homeAction);
		
		txt_quan = (TextView)findViewById(R.id.txt_quan);
		edt_tenduong =(EditText)findViewById(R.id.edt_tenduong);
		edt_tenduong.setVisibility(View.INVISIBLE);
		spn_bank = (Spinner)findViewById(R.id.spn_bank);
		spn_tinh = (Spinner)findViewById(R.id.spn_tinh);
		spn_quan = (Spinner)findViewById(R.id.spn_quan);
		
		cbx_timkiem = (CheckBox)findViewById(R.id.cbx_timkiem);
		cbx_timkiem.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(cbx_timkiem.isChecked()){
					spn_quan.setVisibility(View.INVISIBLE);
					spn_bank.setEnabled(false);
					edt_tenduong.setVisibility(View.VISIBLE);
					txt_quan.setText("Tên Đường:");
					kieutimkiem = 1;
				}else{
					spn_quan.setVisibility(View.VISIBLE);
					spn_bank.setEnabled(true);
					edt_tenduong.setVisibility(View.INVISIBLE);
					txt_quan.setText("Quận/Huyện");
					kieutimkiem = 2;
				}	
			}		
		});
		
		if(checkInternet()==false){
			AlertDialog.Builder msg_check = new AlertDialog.Builder(this);
			msg_check.setTitle("Internet");
			msg_check.setMessage("Vui lòng kiểm tra kết nối Internet và bật GPS!");
			msg_check.setCancelable(false);
			msg_check.setNegativeButton("Đang dò...", null);
			msg_check.show();
		}else{
			try{
				Task_GetBank a = new Task_GetBank();
		        a.execute();

				spn_tinh.setOnItemSelectedListener(new OnItemSelectedListener(){
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						String tentinh = arg0.getItemAtPosition(arg2).toString();
						
						ArrayList<NameValuePair> _REQUEST = new ArrayList<NameValuePair>();
						_REQUEST.add(new BasicNameValuePair("tentinh",tentinh));
						try{
							HttpClient httpClient = new DefaultHttpClient();
							HttpPost httpPost = new HttpPost("http://qhoang.org/webservices/get_list_district.php");
							httpPost.setEntity(new UrlEncodedFormEntity(_REQUEST,"UTF-8"));
							HttpResponse httpResponse = httpClient.execute(httpPost);
							HttpEntity httpEntity = httpResponse.getEntity();
							ArrayList<String> arrayList_district = get_list_district(httpEntity);
							ArrayAdapter<String> arrayAdapter_district = new ArrayAdapter<String>(_timkiem.this,android.R.layout.simple_spinner_item,arrayList_district);
							arrayAdapter_district.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							spn_quan.setAdapter(arrayAdapter_district);
						}catch(Exception e){	}	
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}		
				});
				
				btt_timkiem = (Button)findViewById(R.id.btt_timkiem);
				btt_timkiem.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						String tennganhang = spn_bank.getSelectedItem().toString();
						String tentinh = spn_tinh.getSelectedItem().toString();
						String tenquan = spn_quan.getSelectedItem().toString();
						String tenduong = edt_tenduong.getText().toString();
						
						Intent intent = new Intent("lvhong.tim.atm.KETQUATIMKIEM");
						Bundle bundle = new Bundle();
						
						if(kieutimkiem == 1){
							bundle.putString("tentinh", tentinh);
							bundle.putString("tenduong", tenduong);
							bundle.putInt("kieutimkiem", kieutimkiem);
							intent.putExtras(bundle);
						}else{
							bundle.putString("tennganhang", tennganhang);
							bundle.putString("tenquan", tenquan);
							bundle.putInt("kieutimkiem", kieutimkiem);
							intent.putExtras(bundle);
						}
						startActivity(intent);
					}		
				});
			}catch(Exception e){
				
			}
		}
	}
}
