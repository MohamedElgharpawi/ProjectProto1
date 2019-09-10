package com.example.project;
//class for cities and governmental touring cities in egypt
public class Cities {
    int id;
    String img ;
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cities(int id , String img, String name) {
        this.id = id ;
        this.img = img;
        this.name = name;
    }
}
