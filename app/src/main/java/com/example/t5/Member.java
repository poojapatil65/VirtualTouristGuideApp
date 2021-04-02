package com.example.t5;

public class Member{
    private String hotelName, country, location, checkout_time, price_range, contact, imageAddress;

    public Member() {
    }

    public Member(String hotelName, String country, String location, String checkout_time, String price_range, String contact, String imageAddress) {
        this.hotelName = hotelName;
        this.country = country;
        this.location = location;
        this.checkout_time = checkout_time;
        this.price_range = price_range;
        this.contact = contact;
        this.imageAddress = imageAddress;
    }


    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
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

    public String getCheckout_time() {
        return checkout_time;
    }

    public void setCheckout_time(String checkout_time) {
        this.checkout_time = checkout_time;
    }

    public String getPrice_range() {
        return price_range;
    }

    public void setPrice_range(String price_range) {
        this.price_range = price_range;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }
}