package lvhong.tim.atm;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

import android.app.Activity;
import android.os.Bundle;

public class _about extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		final ActionBar actionBar = (ActionBar)findViewById(R.id.actionBar);
		actionBar.setTitle("Về tác giả: ");
		final Action homeAction = new IntentAction(this, _main.createIntent(this), R.drawable.home);
		actionBar.addAction(homeAction);
	}

}
