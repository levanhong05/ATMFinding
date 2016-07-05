package lvhong.tim.atm.cl;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class QPopupWindows {
	protected Context context;
	protected PopupWindow popupWindow;
	protected View rootView;
	protected Drawable background = null;
	protected WindowManager windowManager;
	
	public QPopupWindows(Context context){
		this.context = context;
		popupWindow = new PopupWindow(context);
		popupWindow.setTouchInterceptor(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if(arg1.getAction() ==	MotionEvent.ACTION_OUTSIDE){
					popupWindow.dismiss();
					return true;
				}
				return false;
			}		
		});
		windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
	}
	
	protected void onDismiss(){
	}
	
	protected void onShow(){
	}
	
	protected void preShow(){
		if(rootView == null){
			throw new IllegalStateException("setContentView was not called with a view to display.");
		}
		onShow();
		if(background == null){
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
		}else{
			popupWindow.setBackgroundDrawable(background);
		}
		
		popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setContentView(rootView);
	}
	
	public void setBackgroundDrawable(Drawable background){
		this.background = background;
	}
	
	public void setContentView(View root){
		this.rootView = root;
		this.popupWindow.setContentView(root);
	}
	
	public void setContentView(int layoutResID){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setContentView(inflater.inflate(layoutResID, null));
	}
	
	public void setOnDismissListener(PopupWindow.OnDismissListener listener){
		popupWindow.setOnDismissListener(listener);
	}
	
	public void dismiss(){
		popupWindow.dismiss();
	}
}
