package com.example.admin;

public class RentModel {
    RentModel() {

    }
        String name,location,price,rating,status,time,pic,number;

    public RentModel(String name, String location, String price, String rating, String status, String time, String pic, String number) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.rating = rating;
        this.status = status;
        this.time = time;
        this.pic = pic;
        this.number = number;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String ratings) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
