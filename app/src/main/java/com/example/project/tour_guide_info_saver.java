package com.example.project;

import java.io.Serializable;

public class tour_guide_info_saver implements Serializable {
    String pic;
    String name ;
    String email ;
    String pass ;
    String phone ;
    String ssn ;
    String bd ;
    int gender ;
    int car ;
    String nationality ;
    String langs ;
    String locs ;

    public tour_guide_info_saver(String pic, String name, String email, String pass, String phone, String ssn, String bd, int gender, int car, String nationality) {
        this.pic = pic;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.phone = phone;
        this.ssn = ssn;
        this.bd = bd;
        this.gender = gender;
        this.car = car;
        this.nationality = nationality;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLangs() {
        return langs;
    }

    public void setLangs(String langs) {
        this.langs = langs;
    }

    public String getLocs() {
        return locs;
    }

    public void setLocs(String locs) {
        this.locs = locs;
    }
}
