package com.example.t5;

public class Member3 {
    private String churchName, country, location, year_establish, church_info, imageAddress;


    public Member3() {
    }

    public Member3(String churchName, String country, String location, String year_establish, String church_info, String imageAddress) {
        this.churchName = churchName;
        this.country = country;
        this.location = location;
        this.year_establish = year_establish;
        this.church_info = church_info;
        this.imageAddress = imageAddress;
    }

    public String getChurchName() {
        return churchName;
    }

    public void setChurchName(String churchName) {
        this.churchName = churchName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getYear_establish() {
        return year_establish;
    }

    public void setYear_establish(String year_establish) {
        this.year_establish = year_establish;
    }

    public String getChurch_info() {
        return church_info;
    }

    public void setChurch_info(String church_info) {
        this.church_info = church_info;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }
}