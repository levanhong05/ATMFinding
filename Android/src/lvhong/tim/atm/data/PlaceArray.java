package lvhong.tim.atm.data;

import java.util.ArrayList;

public class PlaceArray {
	ArrayList<String>	listPlaceName = new ArrayList<String>();
	ArrayList<String>  listLatPoint = new ArrayList<String>();
	ArrayList<String>  listLngPoint = new ArrayList<String>();
	
	public ArrayList<String> getListPlaceName() {
		return this.listPlaceName;
	}
	public void setListPlaceName(String nameD) {
		this.listPlaceName.add(nameD);
	}
	public ArrayList<String> getListLatPoint() {
		return this.listLatPoint;
	}
	public void setListLatPoint(String point) {
		this.listLatPoint.add(point);
	}
	public ArrayList<String> getListLngPoint() {
		return this.listLngPoint;
	}
	public void setListLngPoint(String point) {
		this.listLngPoint.add(point);
	}
}
