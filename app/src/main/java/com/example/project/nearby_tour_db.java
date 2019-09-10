package com.example.project;

import java.io.Serializable;

public class nearby_tour_db implements Serializable {
    private int id ;
    private int guide_id ;

    public int getGuide_id() {
        return guide_id;
    }

    public void setGuide_id(int guide_id) {
        this.guide_id = guide_id;
    }

    private String name ;
    private String email ;
    private String password ;
    private String ssn ;
    private String phone ;
    private String dob ;
    private String sex ;
    private String bio ;
    private String img ;
    private String has_car ;
    private String reg_date ;
    private String governorates ;
    private String languages ;
    private int pic ;
    private String langs;


    public nearby_tour_db(String name, int pic, String langs) {
        this.name = name;
        this.pic = pic;
        this.langs = langs;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHas_car() {
        return has_car;
    }

    public void setHas_car(String has_car) {
        this.has_car = has_car;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getGovernorates() {
        return governorates;
    }

    public void setGovernorates(String governorates) {
        this.governorates = governorates;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
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

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getLangs() {
        return langs;
    }

    public void setLangs(String langs) {
        this.langs = langs;
    }
}
