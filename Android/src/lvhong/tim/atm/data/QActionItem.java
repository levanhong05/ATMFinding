package lvhong.tim.atm.data;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class QActionItem {
	private Drawable icon;
	private Bitmap thumb;
	private String title;
	private int actionID = -1;
	private boolean selected;
	private boolean sticky;
	
	public QActionItem(int actionID, String title, Drawable icon){
		this.actionID = actionID;
		this.title = title;
		this.icon = icon;
	}
	
	public QActionItem(){
		this(-1,null,null);
	}
	
	public QActionItem(int actionID, String title){
		this(actionID,title,null);
	}
	
	public QActionItem(int actionID, Drawable icon){
		this(actionID, null, icon);
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return this.title;
	}
	
	public void setIcon(Drawable icon){
		this.icon = icon;
	}
	public Drawable getIcon(){
		return this.icon;
	}
	
	public void setActionID(int actionID){
		this.actionID = actionID;
	}
	public int getActionID(){
		return this.actionID;
	}
	
	public void setSticky(boolean sticky){
		this.sticky = sticky;
	}
	public boolean getSticky(){
		return this.sticky;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	public boolean getSelected(){
		return this.selected;
	}
	
	public void setThumb(Bitmap thumb){
		this.thumb = thumb;
	}
	public Bitmap getThumb(){
		return this.thumb;
	}
}
