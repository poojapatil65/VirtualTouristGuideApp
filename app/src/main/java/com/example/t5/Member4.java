package com.example.t5;

public class Member4  {
    private String fortName,country,location,timing,fort_info,imageAddress;

    public Member4() {
    }

    public Member4(String fortName, String country, String location, String timing, String fort_info, String imageAddress) {
        this.fortName = fortName;
        this.country = country;
        this.location = location;
        this.timing = timing;
        this.fort_info = fort_info;
        this.imageAddress = imageAddress;
    }

    public String getFortName() {
        return fortName;
    }

    public void setFortName(String fortName) {
        this.fortName = fortName;
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

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getFort_info() {
        return fort_info;
    }

    public void setFort_info(String fort_info) {
        this.fort_info = fort_info;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }
}
