package com.example.project;

import java.io.Serializable;

public class places implements Serializable {
    private int id ;
    private String name ;
    private String pic ;
    String description ;
    String coordinates ;
    String restrictions ;
    String open_time ;
    String ticket ;
    String governorate ;
    String categories ;
    String stars ;


    public places(int id  , String name,String description, String coordinates , String restrictions , String open_time ,String ticket  ,String governorate ,String categories ,String stars, String pic) {
        this.id=id;
        this.name = name;
        this.pic=pic;
        this.description= description ;
        this.coordinates= coordinates ;
        this.restrictions = restrictions ;
        this.open_time = open_time ;
        this.ticket = ticket ;
        this.governorate= governorate ;
        this.categories= categories ;
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
