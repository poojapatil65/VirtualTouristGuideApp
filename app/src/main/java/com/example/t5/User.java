package com.example.t5;

public class User {
    private String email, fullname, username, phone_no, address;

    public User() {
    }

    public User(String email, String fullname, String username, String phone_no, String address) {
        this.email = email;
        this.fullname = fullname;
        this.username = username;
        this.phone_no = phone_no;
        this.address = address;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}