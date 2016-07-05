package lvhong.tim.atm.cl;

import lvhong.tim.atm.R;
import android.content.Context;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.RelativeLayout;
import android.widget.PopupWindow.OnDismissListener;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;

import java.util.List;
import java.util.ArrayList;

import lvhong.tim.atm.data.QActionItem;

public class QuickActionDialog extends QPopupWindows implements OnDismissListener{
	

	private View rootView;
	private ImageView mArrowUp;
	private ImageView mArrowDown;
	private LayoutInflater inflater;
	private ViewGroup viewGroup;
	private ScrollView scrollView;
	private OnActionItemClickListener itemClickListener;
	private OnDismissListener dismissListener;
	
	private List<QActionItem> actionItems = new ArrayList<QActionItem>();
	
	private boolean didAction;
	private int childPos;
	private int insertPos;
	private int animStyle;
	private int orientation;
	private int rootWidth = 0;
	
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1; 
    public static final int ANIM_GROW_FROM_LEFT = 1;
	public static final int ANIM_GROW_FROM_RIGHT = 2;
	public static final int ANIM_GROW_FROM_CENTER = 3;
	public static final int ANIM_REFLECT = 4;
	public static final int ANIM_AUTO = 5;
	
	public QuickActionDialog(Context context){
		this(context,HORIZONTAL);
	}
	
	public QuickActionDialog(Context context, int orientation){
		super(context);
		this.orientation = orientation;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(this.orientation == HORIZONTAL){
			setRootViewId(R.layout.popup_horizontal);
		}else{
			setRootViewId(R.layout.popup_vertical);
		}
		
		this.animStyle = ANIM_AUTO;
		this.childPos = 0;
	}
	
	public QActionItem getActionItem(int index){
		return this.actionItems.get(index);
	}
	
	public void setRootViewId(int id){
		this.rootView = (ViewGroup)this.inflater.inflate(id, null);
		this.viewGroup = (ViewGroup)this.rootView.findViewById(R.id.tracks);
		
		this.mArrowUp =	(ImageView)this.rootView.findViewById(R.id.arrow_up);
		this.mArrowDown = (ImageView)this.rootView.findViewById(R.id.arrow_down);
		
		this.scrollView = (ScrollView)this.rootView.findViewById(R.id.scroller);
		
		this.rootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		setContentView(this.rootView);
	}
	
	public void setAnimStyle(int id){
		this.animStyle = id;
	}
	
	public void setOnActionItemClickListener(OnActionItemClickListener listener){
		this.itemClickListener = listener;
	}
	
	public void addActionItem(QActionItem action) {
		actionItems.add(action);
		
		String title 	= action.getTitle();
		Drawable icon 	= action.getIcon();
		
		View container;
		
		if(this.orientation == HORIZONTAL) {
           container = inflater.inflate(R.layout.action_item_horizontal, null);
        } else {
            container = inflater.inflate(R.layout.action_item_vertical, null);
        }
		
		ImageView img 	= (ImageView) container.findViewById(R.id.iv_icon);
		TextView text 	= (TextView) container.findViewById(R.id.tv_title);
		
		if (icon != null) {
			img.setImageDrawable(icon);
		} else {
			img.setVisibility(View.GONE);
		}
		
		if (title != null) {
			text.setText(title);
		} else {
			text.setVisibility(View.GONE);
		}
		
		final int pos 		=  this.childPos;
		final int actionId 	= action.getActionID();
		
		container.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (itemClickListener != null) {
					itemClickListener.onItemClick(QuickActionDialog.this, pos, actionId);
                }
				
                if (!getActionItem(pos).getSticky()) {  
                	didAction = true;         	
                    dismiss();
                }
			}
		});
		
		container.setFocusable(true);
		container.setClickable(true);
			 
		if (this.orientation == HORIZONTAL && this.childPos != 0) {
            View separator = this.inflater.inflate(R.layout.horiz_separator, null);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
            separator.setLayoutParams(params);
            separator.setPadding(5, 0, 5, 0);
            viewGroup.addView(separator, insertPos);
            insertPos++;
        }
		viewGroup.addView(container,insertPos);
		childPos++;
		insertPos++;
	}
	
	public void show (View anchor) {
		preShow();
		int xPos, yPos, arrowPos;
		this.didAction	= false;
		int[] location 		= new int[2];
		anchor.getLocationOnScreen(location);
		
		Rect anchorRect 	= new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1] + anchor.getHeight());
		this.rootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		int rootHeight 		= this.rootView.getMeasuredHeight();
		
		if (rootWidth == 0) {
			rootWidth		= this.rootView.getMeasuredWidth();
		}
		
		int screenWidth 	= this.windowManager.getDefaultDisplay().getWidth();
		int screenHeight	= this.windowManager.getDefaultDisplay().getHeight();
		
		if ((anchorRect.left + rootWidth) > screenWidth) {
			xPos 		= anchorRect.left - (rootWidth-anchor.getWidth());			
			xPos 		= (xPos < 0) ? 0 : xPos;
			arrowPos 	= anchorRect.centerX()-xPos;
			
		} else {
			if (anchor.getWidth() > rootWidth) {
				xPos = anchorRect.centerX() - (rootWidth/2);
			} else {
				xPos = anchorRect.left;
			}
			arrowPos = anchorRect.centerX()-xPos;
		}
		
		int dyTop			= anchorRect.top;
		int dyBottom		= screenHeight - anchorRect.bottom;
		boolean onTop		= (dyTop > dyBottom) ? true : false;
		if (onTop) {
			if (rootHeight > dyTop) {
				yPos 			= 15;
				LayoutParams l 	= this.scrollView.getLayoutParams();
				l.height		= dyTop - anchor.getHeight();
			} else {
				yPos = anchorRect.top - rootHeight;
			}
		} else {
			yPos = anchorRect.bottom;
			if (rootHeight > dyBottom) { 
				LayoutParams l 	= this.scrollView.getLayoutParams();
				l.height		= dyBottom;
			}
		}
		
		showArrow(((onTop) ? R.id.arrow_down : R.id.arrow_up), arrowPos);
		setAnimationStyle(screenWidth, anchorRect.centerX(), onTop);
		popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
	}
	
	private void setAnimationStyle(int screenWidth, int requestedX, boolean onTop) {
		int arrowPos = requestedX - mArrowUp.getMeasuredWidth()/2;
		switch (this.animStyle) {
		case ANIM_GROW_FROM_LEFT:
			popupWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Left : R.style.Animations_PopDownMenu_Left);
			break;		
		case ANIM_GROW_FROM_RIGHT:
			popupWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Right : R.style.Animations_PopDownMenu_Right);
			break;
		case ANIM_GROW_FROM_CENTER:
			popupWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Center : R.style.Animations_PopDownMenu_Center);
			break;
		case ANIM_REFLECT:
			popupWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Reflect : R.style.Animations_PopDownMenu_Reflect);
			break;
		case ANIM_AUTO:
			if (arrowPos <= screenWidth/4) {
				popupWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Left : R.style.Animations_PopDownMenu_Left);
			} else if (arrowPos > screenWidth/4 && arrowPos < 3 * (screenWidth/4)) {
				popupWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Center : R.style.Animations_PopDownMenu_Center);
			} else {
				popupWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Right : R.style.Animations_PopDownMenu_Right);
			}		
			break;
		}
	}
	
	private void showArrow(int whichArrow, int requestedX) {
        final View showArrow = (whichArrow == R.id.arrow_up) ? mArrowUp : mArrowDown;
        final View hideArrow = (whichArrow == R.id.arrow_up) ? mArrowDown : mArrowUp;
        final int arrowWidth = mArrowUp.getMeasuredWidth();
        showArrow.setVisibility(View.VISIBLE);
        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams)showArrow.getLayoutParams();
        param.leftMargin = requestedX - arrowWidth / 2;
        hideArrow.setVisibility(View.INVISIBLE);
    }
	
	@Override
	public void onDismiss() {
		if (!this.didAction && this.dismissListener != null) {
			this.dismissListener.onDismiss();
		}
	}
	
	public void setOnDismissListener(QuickActionDialog.OnDismissListener listener) {
		setOnDismissListener(this);
		this.dismissListener = listener;
	}
	
	public interface OnActionItemClickListener {
		public abstract void onItemClick(QuickActionDialog source, int pos, int actionId);
	}
	
	public interface OnDismissListener {
		public abstract void onDismiss();
	}
	
}
