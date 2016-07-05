package lvhong.tim.atm.parserdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {
	static InputStream inputStream = null;
	static JSONObject jsonObject = null;
	static String json = "";
	
	public JSONParser(){
	}
	
	public JSONObject getJSONFromRESPONSE(HttpEntity httpEntity){
		try{
			inputStream = httpEntity.getContent();
		}	catch(UnsupportedEncodingException	e)	{
			e.printStackTrace();
		}	catch(ClientProtocolException e){
			e.printStackTrace();
		}	catch(IOException e){
			e.printStackTrace();
		}
		try{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"),8);
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while((line = bufferedReader.readLine()) != null){
				stringBuilder.append(line + "\n");
			}
			inputStream.close();
			json = stringBuilder.toString();
		}	catch(Exception e){
			Log.e("Buffered error ",e.toString());
		}
		
		try{
			jsonObject = new JSONObject(json);
		}	catch(JSONException e){
			Log.e("JSON Parser","Loi parse data" + e.toString());
		}
		return jsonObject;	
	}
	
	public JSONObject getJSONFromURL(String url){
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			HttpResponse  httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			inputStream = httpEntity.getContent();
		}	catch(UnsupportedEncodingException	e)	{
			e.printStackTrace();
		}	catch(ClientProtocolException e){
			e.printStackTrace();
		}	catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"),8);
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while((line = bufferedReader.readLine()) != null){
				stringBuilder.append(line + "\n");
			}
			inputStream.close();
			json = stringBuilder.toString();
		}	catch(Exception e){
			Log.e("Buffered error ",e.toString());
		}
		
		try{
			jsonObject = new JSONObject(json);
		}	catch(JSONException e){
			Log.e("JSON Parser","Loi parse data" + e.toString());
		}
		return jsonObject;	
	}
}
