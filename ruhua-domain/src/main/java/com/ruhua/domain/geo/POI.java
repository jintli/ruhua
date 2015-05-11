package com.ruhua.domain.geo;

public class POI {
	private String name;
	private Coordinate lnglat;
	private String type;
	private String address;
    private String price;
    private String dis4u;
    private String dis4ta;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Coordinate getCoordinate() {
		return lnglat;
	}
	public void setCoordinate(Coordinate lnglat) {
		this.lnglat = lnglat;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDis4u() {
        return dis4u;
    }

    public void setDis4u(String dis4u) {
        this.dis4u = dis4u;
    }

    public String getDis4ta() {
        return dis4ta;
    }

    public void setDis4ta(String dis4ta) {
        this.dis4ta = dis4ta;
    }
}
