package lvhong.tim.atm;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import lvhong.tim.atm.adapter.PlaceHandler;
import lvhong.tim.atm.data.PlaceArray;
import lvhong.tim.atm.data.PlaceNode;
import lvhong.tim.atm.marker.MarkerATM;
import lvhong.tim.atm.marker.MarkerME;
import lvhong.tim.atm.marker.MarkerPath;
import lvhong.tim.atm.marker.MarkerPlace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class KQMap extends MapActivity{

	Button btt_tien,btt_lui;
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private MapView mapView;
	private MapController mapController;
	private MarkerATM markerATM = null;
	private Drawable marker;
	private GeoPoint geoPoint = null;
	private Bundle bundle;
	private ArrayList<PlaceNode> array_PlaceNode = null;
	public int chiso = -1;
	private MarkerPlace listMarkerPlace=null;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.kqmap);
		
		mapView = (MapView)findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapController = mapView.getController();
		mapController.setZoom(16);
		
		btt_tien = (Button)findViewById(R.id.btttien);
		btt_lui = (Button)findViewById(R.id.bttlui);
		btt_tien.setVisibility(View.INVISIBLE);
		btt_lui.setVisibility(View.INVISIBLE);
		btt_tien.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				animateMark("tien");
			}
		});
		btt_lui.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				animateMark("lui");
			}		
		});
		
		bundle = getIntent().getExtras();
		int kieu = bundle.getInt("kieu");
		if(kieu==1){
			DanhDauATM(bundle);
		}else{
			DrawPATH(bundle);
			
		}
	}
	
	public void animateMark(String huong){
		if(huong=="tien"){
			if(chiso == listMarkerPlace.size()){
				animateATM(bundle);
			}else if(chiso == -1){
				listMarkerPlace.onTap(0);
				chiso = 0;
			}else if(chiso ==listMarkerPlace.size()-1){
				listMarkerPlace.onTap(chiso++);
			}else{
				listMarkerPlace.onTap(++chiso);
			}
		}else{
			if(chiso==-1){
				animateME(bundle);
			}else if(chiso==0){
				listMarkerPlace.onTap(0);
			}else{
				listMarkerPlace.onTap(--chiso);
			}
		}
	}

	public PlaceArray getListPlaceMark(String myLoc, String atmLoc){
		try{	   
	    		SAXParserFactory spf = SAXParserFactory.newInstance();
	   			SAXParser sp = spf.newSAXParser();
	   			XMLReader xr = sp.getXMLReader();
	   			URL sourceUrl = new URL("http://maps.google.com/maps?f=d&hl=vi&saddr="+myLoc+"&daddr="+atmLoc+"&ie=UTF8&om=0&output=kml");
	   			PlaceHandler placeHandler = new PlaceHandler();
	   			xr.setContentHandler(placeHandler);
	   			xr.parse(new InputSource(sourceUrl.openStream()));
	    }catch(Exception e){
	     	  
	    }
		return PlaceHandler.itemList;
	}
	
	public ArrayList<PlaceNode> getListPlaceNode(PlaceArray itemList){
		 ArrayList<PlaceNode> arrayList = new ArrayList<PlaceNode>();
	     for(int i =0;i<itemList.getListLatPoint().size();i++){
	    	   PlaceNode a = new PlaceNode();
	    	   a.setPlaceName(itemList.getListPlaceName().get(i+1));
	    	   a.setPlaceLatPoint(itemList.getListLatPoint().get(i));
	    	   a.setPlaceLngPoint(itemList.getListLngPoint().get(i));
	    	   arrayList.add(a);
	       }
	     return arrayList;
	}
	
	public void MarkerPlaceNode(ArrayList<PlaceNode> arrayList){
		Drawable marker=getResources().getDrawable(R.drawable.star);
		listMarkerPlace = new MarkerPlace(marker, mapView);
		for(int i=0;i<arrayList.size();i++){
				String place_title = "Chỉ dẫn";
				String place_name = arrayList.get(i).getPlaceName();
				String atm_lng = arrayList.get(i).getPlaceLngPoint();
				String atm_lat = arrayList.get(i).getPlaceLatPoint();
				Double _lat = Double.parseDouble(atm_lat)*1E6;
				Double _lng = Double.parseDouble(atm_lng)*1E6;
				
				GeoPoint myPoint = new GeoPoint(_lat.intValue(), _lng.intValue());
				OverlayItem overlayItem = new OverlayItem(myPoint, place_title, place_name);
				listMarkerPlace.addOverlay(overlayItem);
			}
		mapView.getOverlays().add(listMarkerPlace);
	}
	
	public void animateME(Bundle bundle){
		Double _lat = Double.parseDouble(bundle.getString("latLoc"))*1E6;
		Double _lng = Double.parseDouble(bundle.getString("lngLoc"))*1E6;
		GeoPoint myPoint = new GeoPoint(_lat.intValue(),_lng.intValue());
		mapView.getController().animateTo(myPoint);
		markerME(bundle);
	}
	
	public void animateATM(Bundle bundle){
		Double _lat = Double.parseDouble(bundle.getString("latATM"))*1E6;
		Double _lng = Double.parseDouble(bundle.getString("lngATM"))*1E6;
		GeoPoint myPoint = new GeoPoint(_lat.intValue(),_lng.intValue());
		mapView.getController().animateTo(myPoint);
	}
	
	public void markerME(Bundle bundle){
        Double _lat = Double.parseDouble(bundle.getString("latLoc"))*1E6;
		Double _lng = Double.parseDouble(bundle.getString("lngLoc"))*1E6;
		MarkerME item1 = new MarkerME();
		item1.addAnimationToMap(mapView, new GeoPoint(_lat.intValue(),_lng.intValue()));
		
	}
	
	private class task_DrawPath extends AsyncTask<Void,Void,String[]>{
		String startGPS, endGPS;
		ProgressDialog MyDialog = new ProgressDialog(KQMap.this);
		public task_DrawPath(String startGP,String endGP){
			this.startGPS = startGP;
			this.endGPS = endGP;
		}
		@Override
		protected String[] doInBackground(Void... arg0) {
			String urlString = "http://maps.google.com/maps?f=d&hl=en&saddr="+startGPS+"&daddr="+endGPS+"&ie=UTF8&0&om=0&output=kml";
			Log.d("URL", urlString);
			Document doc = null;
			HttpURLConnection urlConnection = null;
			URL url = null;
			String pathConent = "";
			try {
					url = new URL(urlString.toString());
					urlConnection = (HttpURLConnection) url.openConnection();
					urlConnection.setRequestMethod("GET");
					urlConnection.setDoOutput(true);
					urlConnection.setDoInput(true);
					urlConnection.connect();
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					doc = db.parse(urlConnection.getInputStream());
				  } catch (Exception e) {
			}
			
			NodeList nl = doc.getElementsByTagName("LineString");
			for (int s = 0; s < nl.getLength(); s++) {
				Node rootNode = nl.item(s);
				NodeList configItems = rootNode.getChildNodes();
				for (int x = 0; x < configItems.getLength(); x++) {
					Node lineStringNode = configItems.item(x);
					NodeList path = lineStringNode.getChildNodes();
					pathConent = path.item(0).getNodeValue();
				}
			}
			String[] tempContent = pathConent.split(" ");
			return tempContent;
		}

		@Override
		protected void onPostExecute(String[] result) {
			String[] lngLat = result[0].split(",");

	        GeoPoint startGP = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6));
	        mapController = mapView.getController();
	        geoPoint = startGP;
	        mapController.setCenter(geoPoint);
	        mapController.setZoom(18);
	        mapView.getOverlays().clear();
	        DanhDauATM(bundle);
	        mapView.getOverlays().add(new MarkerPath(startGP, startGP));
	        
	        GeoPoint gp1;
	        GeoPoint gp2 = startGP;

	        for (int i = 1; i < result.length; i++) {
	            lngLat = result[i].split(",");
	            gp1 = gp2;
	            gp2 = new GeoPoint((int)(Double.parseDouble(lngLat[1])*1E6),(int)(Double.parseDouble(lngLat[0])*1E6));
	            mapView.getOverlays().add(new MarkerPath(gp1, gp2));
	        }

	        mapView.getOverlays().add(new MarkerPath(gp2, gp2));
	        mapView.getController().animateTo(startGP);
	        markerME(bundle);
	        array_PlaceNode =	getListPlaceNode(getListPlaceMark(startGPS,endGPS)); 
	        MarkerPlaceNode(array_PlaceNode);
			btt_tien.setVisibility(View.VISIBLE);
			btt_lui.setVisibility(View.VISIBLE);
			MyDialog.dismiss();
		}

		@Override
		protected void onPreExecute() {
			MyDialog.setMessage("Đang vẽ đường......");
			MyDialog.show();
		}
	}
	
	public void DrawPATH(Bundle bundle){
		String startPoint = bundle.getString("latLoc")+","+bundle.getString("lngLoc");
		String endPoint = bundle.getString("latATM")+","+bundle.getString("lngATM");
		task_DrawPath task = new task_DrawPath(startPoint,endPoint);
		task.execute();
	}
	
	public void DanhDauATM(Bundle bundle){
		Double _lat = Double.parseDouble(bundle.getString("latATM"))*1E6;
		Double _lng = Double.parseDouble(bundle.getString("lngATM"))*1E6;
		GeoPoint point = new GeoPoint(_lat.intValue(),_lng.intValue());
		String tenatm = bundle.getString("tenatm");
		String diachi = bundle.getString("diachi");
		
		OverlayItem overlayItem = new OverlayItem(point, tenatm, diachi);
		
		marker = this.getResources().getDrawable(R.drawable.hoe);
		markerATM = new MarkerATM(marker,mapView);
		markerATM.addOverlay(overlayItem);
		
		mapView.getOverlays().clear();
		mapView.getOverlays().add(markerATM);
		mapController.animateTo(point);
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater m = getMenuInflater();
		m.inflate(R.menu.menu_map1, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 int id = item.getItemId();
	     if (id == R.id.myLocation)
	     {
	    	 animateME(this.getIntent().getExtras());
	         return true;
	     }
	     else if (id == R.id.veduong)
	     {
	    	 DrawPATH(this.getIntent().getExtras());
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