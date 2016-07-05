package lvhong.tim.atm.data;

public class PlaceNode {
	private String placeName;
	private String placeLatPoint;
	private String placeLngPoint;
	
	public String getPlaceName(){
		return this.placeName;
	}
	public String getPlaceLatPoint(){
		return this.placeLatPoint;
	}
	public String getPlaceLngPoint(){
		return this.placeLngPoint;
	}
	
	public void setPlaceName(String placeName){
		this.placeName = placeName;
	}
	public void setPlaceLatPoint(String placePoint){
		this.placeLatPoint = placePoint;
	}
	public void setPlaceLngPoint(String placePoint){
		this.placeLngPoint = placePoint;
	}
}
