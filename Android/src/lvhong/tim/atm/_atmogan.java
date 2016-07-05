package lvhong.tim.atm;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lvhong.tim.atm.marker.MarkerATM;
import lvhong.tim.atm.marker.MarkerME;
import lvhong.tim.atm.parserdata.JSONParser;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class _atmogan extends MapActivity{

	private MapView mapView;
	private MapController mapController;
	private LocationManager locationManager;
	private LocationListener locationListener;
	private Location location;
	private Spinner spn_bankinh, spn_kieubando;
 	private MarkerATM itemizedOverlay = null;
 	private List<Overlay> mapOverlays;
 	private Drawable marker;

 
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.atmogan);
		
		mapView = (MapView)findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapView.setTraffic(true);
		
		mapView.invalidate();
		mapController = mapView.getController();
		mapController.setZoom(16);
		mapOverlays = mapView.getOverlays();

		locationListener = new MyLocationListener();
		locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		
		Criteria criteria = new Criteria(); 
		criteria.setAccuracy(Criteria.ACCURACY_COARSE); 
		//String bestProvider= locationManager.getBestProvider(criteria, true); 
		//location = locationManager.getLastKnownLocation(bestProvider);
		
		
		//----ĐH Bach Khoa Da Nang--
		location = new Location("tag");
		Double lat1 = 16.074269;
		Double lng1 = 108.149775;
		location.setLatitude(lat1);
		location.setLongitude(lng1);
		updateLocation(location);
		//----ĐH Bah Khoa Da Nang--------------------------
		
		addItemToSpinner(this);

		//-----asynctask cho nay----////
		spn_bankinh.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,	int arg2, long arg3) {
				String[] bankinh_values = getResources().getStringArray(R.array.bankinh_values);
				if(arg2 == 6){
					AlertDialog.Builder msg_bankinh = new AlertDialog.Builder(_atmogan.this);
					msg_bankinh.setTitle("Nhập bán kính tùy chọn: ");
					final EditText input_bankinh = new EditText(_atmogan.this);
					input_bankinh.setInputType(InputType.TYPE_CLASS_NUMBER);
					msg_bankinh.setView(input_bankinh);
					msg_bankinh.setNegativeButton("Bỏ qua", null);
					msg_bankinh.setNeutralButton("Nhập", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
								marker_ATM(input_bankinh.getText().toString(),location);
						}
					});
					msg_bankinh.show();
				}	else	{
					marker_ATM(bankinh_values[arg2],location);
					
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}		
		});
		 //-----asynctask cho nay----////
	}
	
	private class taskMK extends AsyncTask<Void,Void,JSONObject>{
		String url;
		ProgressDialog MyDialog = new ProgressDialog(_atmogan.this);
		
		public taskMK(String url){
			this.url = url;
		}
		
		@Override
		protected JSONObject doInBackground(Void... params) {
			JSONParser jsonParser = new JSONParser();
			JSONObject json = jsonParser.getJSONFromURL(url);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			MyDialog.dismiss();
			marker=getResources().getDrawable(R.drawable.hoe);
			itemizedOverlay = new MarkerATM(marker, mapView);
			int i=0;
			try{
				JSONArray array_atm = result.getJSONArray("results");
				for(i=0;i<array_atm.length();i++){
					JSONObject c = array_atm.getJSONObject(i);
					String atm_name = c.getString("name");
					String atm_address = c.getString("vicinity");
					
					JSONObject geometry  = c.getJSONObject("geometry");
					JSONObject location = geometry.getJSONObject("location");
					String atm_lat = location.getString("lat");
					String atm_lng = location.getString("lng");
					
					Double _lat = Double.parseDouble(atm_lat)*1E6;
					Double _lng = Double.parseDouble(atm_lng)*1E6;
					
					GeoPoint myPoint = new GeoPoint(_lat.intValue(), _lng.intValue());
					OverlayItem overlayItem = new OverlayItem(myPoint, atm_name, atm_address);
					itemizedOverlay.addOverlay(overlayItem);			    			
				}
			}	catch(JSONException e){
				e.printStackTrace();
			}			
			if(itemizedOverlay != null&&i>0){
				mapOverlays.add(itemizedOverlay);
				String str = "Tìm thấy "+ i +" máy ATM ";
				Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();
			}	
		}

		@Override
		protected void onPreExecute() {
			MyDialog.setMessage("Đang tải dữ liệu.......!");
			MyDialog.show();
		}
	}
	
	public void marker_ATM(String bk, Location loc){
		Double lat = loc.getLatitude();
		Double lng = loc.getLongitude();
		String url = "https://maps.googleapis.com/maps/api/place/search/json?location="+lat+"%2C"+lng+"&name=atm&radius="+bk+"&sensor=false&key=0ldnGXI3TNEPi_dixF4e8utg9HCQtBUVFrsPtnA";
		
		marker_me(loc);

		taskMK mk = new taskMK(url);
		mk.execute();

		/*
		JSONParser jsonParser = new JSONParser();
		JSONObject json = jsonParser.getJSONFromURL(url);
		marker=getResources().getDrawable(R.drawable.hoe);
		itemizedOverlay = new MarkerATM(marker, mapView);
		int i=0;
		try{
			JSONArray array_atm = json.getJSONArray("results");
			for(i=0;i<array_atm.length();i++){
				JSONObject c = array_atm.getJSONObject(i);
				String atm_name = c.getString("name");
				String atm_address = c.getString("vicinity");
				
				JSONObject geometry  = c.getJSONObject("geometry");
				JSONObject location = geometry.getJSONObject("location");
				String atm_lat = location.getString("lat");
				String atm_lng = location.getString("lng");
				
				Double _lat = Double.parseDouble(atm_lat)*1E6;
				Double _lng = Double.parseDouble(atm_lng)*1E6;
				
				GeoPoint myPoint = new GeoPoint(_lat.intValue(), _lng.intValue());
				OverlayItem overlayItem = new OverlayItem(myPoint, atm_name, atm_address);
				itemizedOverlay.addOverlay(overlayItem);			    			
			}
		}	catch(JSONException e){
			e.printStackTrace();
		}
		
		
		if(itemizedOverlay != null&&i>0){
			mapOverlays.add(itemizedOverlay);
			String str = "Tìm thấy "+ i +" máy ATM ";
			Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();
		}	
		*/
	}
	
	public static boolean isNumberic(String str){
		for (char c : str.toCharArray()){
			if (!Character.isDigit(c)) return false;
		}
		return true;
	}
	
	public void addItemToSpinner(Context ct){
		String[] bankinh_item = ct.getResources().getStringArray(R.array.bankinh_item);
		String[] kieuhienthi_item = ct.getResources().getStringArray(R.array.kieuhienthi_item);
		ArrayAdapter<String> adapter_bankinh = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,bankinh_item);
		ArrayAdapter<String> adapter_kieuhienthi = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,kieuhienthi_item);
		adapter_bankinh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter_kieuhienthi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn_bankinh = (Spinner)findViewById(R.id.spn_bankinh);
		spn_kieubando = (Spinner)findViewById(R.id.spn_kieuhienthi);
		spn_bankinh.setAdapter(adapter_bankinh);
		spn_kieubando.setAdapter(adapter_kieuhienthi);
	}
	
	@Override
	protected void onPause() {
		/*mov.disableMyLocation();
		mov1.disableMyLocation();
		mov1.disableCompass();*/
		super.onPause();
	}

	@Override
	protected void onResume() {
		/*mov.enableMyLocation();
		mov1.enableMyLocation();
		mov1.disableCompass();*/
		super.onResume();
	}
	
	public void marker_me(Location location){
		mapView.getOverlays().clear();
		/*
		Drawable icon = this.getResources().getDrawable(R.drawable.current_loc1); 
		MarkerME itemmarker = new MarkerME(icon);
		itemmarker.setLocation(location);
		mapOverlays.add(itemmarker);*/
		
		MarkerME item1 = new MarkerME();
		item1.addAnimationToMap(mapView, convertLoc(location));
	}
	
	public GeoPoint convertLoc(Location loc){
		Double lat = loc.getLatitude()*1E6;
		Double lng = loc.getLongitude()*1E6;
		GeoPoint point = new GeoPoint(lat.intValue(),lng.intValue());
		return point;
	}
	
	public void updateLocation(Location location){
		this.location = location;
		mapController.animateTo(convertLoc(location));
		marker_me(location);
	}
	
	public class MyLocationListener implements LocationListener{
		@Override
		public void onLocationChanged(Location arg0) {
			updateLocation(arg0);
		}

		@Override
		public void onProviderDisabled(String arg0) {
			Toast.makeText(getApplicationContext(),"Hãy bật GPS để xác định địa điểm",Toast.LENGTH_SHORT ).show();
		}

		@Override
		public void onProviderEnabled(String arg0) {
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		}	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater m = getMenuInflater();
		m.inflate(R.menu.menu_map, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 int id = item.getItemId();
	        if (id == R.id.myLocation)
	        {
	        	mapController.animateTo(convertLoc(location));
	            return true;
	        }
	        else if (id == R.id.home)
	        {
	        	Intent homeIntent = new Intent(getApplicationContext(),_main.class);
	        	homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	startActivity(homeIntent);
	        	finish();
	            return true;
	        }
	    return false;
	}
	
}
