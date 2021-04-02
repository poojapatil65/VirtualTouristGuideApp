package com.example.t5;

public class Member5 {
    private String  templeName,country,location,visit_time,entry,temple_info,imageAddress;

    public Member5() {
    }

    public Member5(String templeName, String country, String location, String visit_time, String entry, String temple_info, String imageAddress) {
        this.templeName = templeName;
        this.country = country;
        this.location = location;
        this.visit_time = visit_time;
        this.entry = entry;
        this.temple_info = temple_info;
        this.imageAddress = imageAddress;
    }

    public String getTempleName() {
        return templeName;
    }

    public void setTempleName(String templeName) {
        this.templeName = templeName;
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

    public String getVisit_time() {
        return visit_time;
    }

    public void setVisit_time(String visit_time) {
        this.visit_time = visit_time;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getTemple_info() {
        return temple_info;
    }

    public void setTemple_info(String temple_info) {
        this.temple_info = temple_info;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }
}
