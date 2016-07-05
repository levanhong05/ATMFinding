package lvhong.tim.atm.marker;

import lvhong.tim.atm.R;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.widget.ImageView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MarkerME extends Overlay{
	
	private Location location;
	private Drawable icon;
	
	public void setLocation(Location location){
		this.location = location;
	}
	public Location getLocation(){
		return this.location;
	}
	
	public MarkerME(){
		
	}
	public MarkerME(Drawable icon){
		this.icon = icon;
	}
	public void addAnimationToMap(MapView mapView, GeoPoint geoPoint)	{
		final ImageView view = new ImageView(mapView.getContext());
		view.setImageResource(R.drawable.current_loc);
		view.post(new Runnable() {
			@Override
			public void run() {
				AnimationDrawable animationDrawable = (AnimationDrawable) view.getDrawable();
				animationDrawable.start();
			}
		});
		mapView.addView(view);
		MapView.LayoutParams layoutParams = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT, MapView.LayoutParams.WRAP_CONTENT, geoPoint, MapView.LayoutParams.BOTTOM_CENTER);
		view.setLayoutParams(layoutParams);
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		
		Projection projection = mapView.getProjection();
		
		Double lat = location.getLatitude()*1E6;
		Double lng = location.getLongitude()*1E6;
		GeoPoint geoPoint = new GeoPoint(lat.intValue(),lng.intValue());
		Point point = new Point();
		projection.toPixels(geoPoint, point);
		
		Paint paint = new Paint();
		
		Bitmap bitmap = ((BitmapDrawable)icon).getBitmap();
		canvas.drawBitmap(bitmap, point.x, point.y, paint);
		
		super.draw(canvas, mapView, shadow);
	}
	@Override
	public boolean onTap(GeoPoint geoPoint, MapView mapView) {
		return false;
	}
	
}
