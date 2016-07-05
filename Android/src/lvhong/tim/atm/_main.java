package lvhong.tim.atm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.markupartist.android.widget.ActionBar;

public class _main extends Activity {
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
			if (id == R.id.mn_about)
			{
				startActivity(new Intent("lvhong.tim.atm.ABOUT"));
				return true;
			}
			else if (id == R.id.mn_gopy)
			{
				startActivity(new Intent("lvhong.tim.atm.GOPY"));
				return true;
			}
			else if (id == R.id.mn_out)
			{
				AlertDialog.Builder msg_thoat = msg_thoat();
				msg_thoat.show();
				return true;
			}
		return false;
	}

	private Button btttimkiem, bttatmogan;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final ActionBar actionBar = (ActionBar)findViewById(R.id.actionBar);
		actionBar.setTitle("Home");
        
        btttimkiem = (Button)findViewById(R.id.btttimkiem);
        btttimkiem.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent("lvhong.tim.atm.TIMKIEM"));
			}
        });
        
        bttatmogan = (Button)findViewById(R.id.bttatmogan);
        bttatmogan.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				startActivity(new Intent("lvhong.tim.atm.ATMOGAN"));
			}       	
        });
    }
    
    public AlertDialog.Builder msg_thoat(){
    	AlertDialog.Builder msg_dong = new AlertDialog.Builder(_main.this);
		msg_dong.setTitle("Cảnh báo");
		msg_dong.setIcon(getResources().getDrawable(R.drawable.closeapp));
		msg_dong.setMessage("Bạn có muốn thoát khỏi ứng dụng không?");
		msg_dong.setCancelable(false);
		msg_dong.setNegativeButton("Không", null);
		msg_dong.setNeutralButton("Có", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				System.exit(0);
			}
		});
		return msg_dong;
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			AlertDialog.Builder msg_dong = msg_thoat();
			msg_dong.show();
			return true;
		}else{
			return false;
		}
	}

    
    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, _main.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }
}
