package com.example.t5;

public class Member1 {
    private String hospital_name, country, location, degree, contact_no, time_duration,imageAddress;

    public Member1() {
    }

    public Member1(String hospital_name, String country, String location, String degree, String contact_no, String time_duration, String imageAddress) {
        this.hospital_name = hospital_name;
        this.country = country;
        this.location = location;
        this.degree = degree;
        this.contact_no = contact_no;
        this.time_duration = time_duration;
        this.imageAddress = imageAddress;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
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

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getTime_duration() {
        return time_duration;
    }

    public void setTime_duration(String time_duration) {
        this.time_duration = time_duration;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }
}