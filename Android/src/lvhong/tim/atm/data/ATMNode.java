package lvhong.tim.atm.data;

public class ATMNode {
	private String tendiadiem;
	private String diachi;
	private String _lat;
	private String _lng;
	
	public void set_lat(String lat){
		this._lat = lat;
	}
	public void set_lng(String lng){
		this._lng = lng;
	}
	public void set_tendd(String tendd){
		this.tendiadiem = tendd;
	}
	public void set_diachi(String diachi){
		this.diachi = diachi;
	}
	
	
	
	public String get_lat(){
		return this._lat;
	}
	public String get_lng(){
		return this._lng;
	}
	public String get_tendd(){
		return tendiadiem;
	}
	public String get_diachi(){
		return diachi;
	}
}
