package com.example.admin;

public class Tourmodel {
    String  placename,placedescription,placelocationurl,placetime,placepicurl;
    Tourmodel(){}

    public Tourmodel(String placename, String placedescription, String placelocationurl, String placetime, String placepicurl) {
        this.placename = placename;
        this.placedescription = placedescription;
        this.placelocationurl = placelocationurl;
        this.placetime = placetime;
        this.placepicurl = placepicurl;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public String getPlacedescription() {
        return placedescription;
    }

    public void setPlacedescription(String placedescription) {
        this.placedescription = placedescription;
    }

    public String getPlacelocationurl() {
        return placelocationurl;
    }

    public void setPlacelocationurl(String placelocationurl) {
        this.placelocationurl = placelocationurl;
    }

    public String getPlacetime() {
        return placetime;
    }

    public void setPlacetime(String placetime) {
        this.placetime = placetime;
    }

    public String getPlacepicurl() {
        return placepicurl;
    }

    public void setPlacepicurl(String placepicurl) {
        this.placepicurl = placepicurl;
    }
}
