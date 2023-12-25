package com.example.admin;

public class Hotelmodel {
    String hotelname, hotelratings, hoteldescription, hoteltime, hotelroomstatus, hotellocation, hotelpic;
    Hotelmodel(){}

    public Hotelmodel(String hotelname, String hotelratings, String hoteldescription, String hoteltime, String hotelroomstatus, String hotellocation, String hotelpic) {
        this.hotelname = hotelname;
        this.hotelratings = hotelratings;
        this.hoteldescription = hoteldescription;
        this.hoteltime = hoteltime;
        this.hotelroomstatus = hotelroomstatus;
        this.hotellocation = hotellocation;
        this.hotelpic = hotelpic;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public String getHotelratings() {
        return hotelratings;
    }

    public void setHotelratings(String hotelratings) {
        this.hotelratings = hotelratings;
    }

    public String getHoteldescription() {
        return hoteldescription;
    }

    public void setHoteldescription(String hoteldescription) {
        this.hoteldescription = hoteldescription;
    }

    public String getHoteltime() {
        return hoteltime;
    }

    public void setHoteltime(String hoteltime) {
        this.hoteltime = hoteltime;
    }

    public String getHotelroomstatus() {
        return hotelroomstatus;
    }

    public void setHotelroomstatus(String hotelroomstatus) {
        this.hotelroomstatus = hotelroomstatus;
    }

    public String getHotellocation() {
        return hotellocation;
    }

    public void setHotellocation(String hotellocation) {
        this.hotellocation = hotellocation;
    }

    public String getHotelpic() {
        return hotelpic;
    }

    public void setHotelpic(String hotelpic) {
        this.hotelpic = hotelpic;
    }
}