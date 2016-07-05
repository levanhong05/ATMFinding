package lvhong.tim.atm.marker;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;

public class MarkerPlace extends BalloonItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();
	private Context c;
	private MapView mapView;
	
	public Context getContext(){
		return this.c;
	}
	
	public MarkerPlace(Drawable defaultMarker, MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
		this.mapView = mapView;
		c = mapView.getContext();
	}

	
	public void addOverlay(OverlayItem overlay) {
	    m_overlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	@Override
	protected boolean onBalloonTap(int index, OverlayItem item) {
		mapView.getController().animateTo(item.getPoint());
		//Toast.makeText(c, "onBalloonTap for overlay index " + index,Toast.LENGTH_LONG).show();
		return false;
	}
	
}

