package com.example.admin;

public class Othermodel {
    String name,rating,time,location,description,pic;
    Othermodel(){}

    public Othermodel(String name, String rating, String time, String location, String description, String pic) {
        this.name = name;
        this.rating = rating;
        this.time = time;
        this.location = location;
        this.description = description;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
