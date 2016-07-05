package lvhong.tim.atm.marker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MarkerPath extends Overlay{

	private GeoPoint startP, endP;
	
	public MarkerPath(GeoPoint startP, GeoPoint endP){
		this.startP = startP;
		this.endP = endP;
	}
	@Override
    public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
            long when) {
        // TODO Auto-generated method stub
        Projection projection = mapView.getProjection();
        if (shadow == false) {

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            Point point = new Point();
            projection.toPixels(startP, point);
            paint.setColor(Color.BLUE);
            Point point2 = new Point();
            projection.toPixels(endP, point2);
            paint.setStrokeWidth(4);
            canvas.drawLine((float) point.x, (float) point.y, (float) point2.x,(float) point2.y, paint);
        }
        return super.draw(canvas, mapView, shadow, when);
    }

    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
    	super.draw(canvas, mapView, shadow);
    }

	@Override
	public boolean onTap(GeoPoint p, MapView mapView) {
		return false;
	}

}
