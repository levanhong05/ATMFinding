package lvhong.tim.atm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class _splash extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread logontime = new Thread(){
			public void run(){
				try{
					int logontime = 0;
					while(logontime < 1000){
						sleep(5000);
						logontime = 1001;
					}
					startActivity(new Intent("lvhong.tim.atm.MAIN_MENU"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				finally{
					finish();
				}
			}
		};
		logontime.start();
    }
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
