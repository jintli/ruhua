package com.ruhua.domain.date;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-12-5
 * Time: 下午7:27
 * To change this template use File | Settings | File Templates.
 */
public class DateInfo implements Serializable {

    private int type;
    private String email;
    private String dateEmail;
    private String datetime;
    private String loc;
    private double lng;
    private double lat;
    private int status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateEmail() {
        return dateEmail;
    }

    public void setDateEmail(String dateEmail) {
        this.dateEmail = dateEmail;
    }


    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
