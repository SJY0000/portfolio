package model;

public class MarketBean {
	private int mno;
	private String mname;
	private String mtype;
	private String madd;
	private String period;
	private String lat;
	private String longi;
	private String store;
	private String object;
	private int toilet;
	private int parking;
	
	public MarketBean() {
	}
	
	public MarketBean(String mname, String mtype, String madd, String period, String lat, String longi, String store,
			String object, int toilet, int parking) {
		this.mname = mname;
		this.mtype = mtype;
		this.madd = madd;
		this.period = period;
		this.lat = lat;
		this.longi = longi;
		this.store = store;
		this.object = object;
		this.toilet = toilet;
		this.parking = parking;
	}
	
	public int getToilet() {
		return toilet;
	}
	public void setToilet(int toilet) {
		this.toilet = toilet;
	}
	public int getParking() {
		return parking;
	}
	public void setParking(int parking) {
		this.parking = parking;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getMtype() {
		return mtype;
	}
	public void setMtype(String mtype) {
		this.mtype = mtype;
	}
	public String getMadd() {
		return madd;
	}
	public void setMadd(String madd) {
		this.madd = madd;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLongi() {
		return longi;
	}
	public void setLongi(String longi) {
		this.longi = longi;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	
}
