package lvhong.tim.atm;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

public class _gopy extends Activity{
	
	private EditText edttieude, edtnoidung;
	private Button bttgui;
	private WifiManager wifiManager;
	private WifiInfo wifiInfo;
	private String tieude, noidung, name, mac;
	
	public boolean checkInternet(){
		ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if(networkInfo!=null&&networkInfo.isConnectedOrConnecting())
			return true;
		else
			return false;
	}
	
	private class task_GopY extends AsyncTask<Void,Void,Void>{
		ProgressDialog MyDialog = new ProgressDialog(_gopy.this);
		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<NameValuePair> _REQUEST = new ArrayList<NameValuePair>();
			_REQUEST.add(new BasicNameValuePair("tieude",tieude));
			_REQUEST.add(new BasicNameValuePair("noidung",noidung));
			_REQUEST.add(new BasicNameValuePair("name",name));
			_REQUEST.add(new BasicNameValuePair("mac",mac));
			try{
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost("http://qhoang.org/webservices/add_contact.php");
				httpPost.setEntity(new UrlEncodedFormEntity(_REQUEST,"UTF-8"));
				httpClient.execute(httpPost);
			}catch(Exception e){
				
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			MyDialog.dismiss();
			edttieude.setText("");
			edtnoidung.setText("");
			//Toast.makeText(getApplicationContext(), "Đã gửi góp ý thành công!", Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPreExecute() {
			MyDialog.setMessage("Đang gửi góp ý! ");
			MyDialog.show();
		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gopy);
		
		final ActionBar actionBar = (ActionBar)findViewById(R.id.actionBar);
		actionBar.setTitle("Góp ý");
		final Action backAction = new IntentAction(this, _main.createIntent(this), R.drawable.back);
	     actionBar.addAction(backAction);
		final Action homeAction = new IntentAction(this, _main.createIntent(this), R.drawable.home);
		actionBar.addAction(homeAction);
		
		edttieude = (EditText)findViewById(R.id.edt_tieude);
		edtnoidung = (EditText)findViewById(R.id.edt_noidung);
		bttgui = (Button)findViewById(R.id.btt_guigopy);
		wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
		wifiInfo = wifiManager.getConnectionInfo();		
			
		bttgui.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				tieude = edttieude.getText().toString();
				noidung = edtnoidung.getText().toString();
				name = Build.MODEL + " - " + VERSION.RELEASE;
				mac = wifiInfo.getMacAddress();
				
				if(tieude.length()==0||noidung.length()==0){
					AlertDialog.Builder msg_nd = new AlertDialog.Builder(_gopy.this);
					msg_nd.setTitle("Cảnh báo");
					msg_nd.setIcon(getResources().getDrawable(R.drawable.closeapp));
					msg_nd.setMessage("Tiêu đề và nội dung không được bỏ trống!");
					msg_nd.setCancelable(true);
					msg_nd.setNeutralButton("OK", null);
					msg_nd.show();
				}	else if (checkInternet()==false){
					AlertDialog.Builder msg_net = new AlertDialog.Builder(_gopy.this);
					msg_net.setTitle("Cảnh báo");
					msg_net.setIcon(getResources().getDrawable(R.drawable.closeapp));
					msg_net.setMessage("Kiểm tra kết nối Internet");
					msg_net.setCancelable(true);
					msg_net.setNeutralButton("OK", null);
					msg_net.show();
				}	else	{
					task_GopY a = new task_GopY();
					a.execute();
				}
			}
		});
	}

}
