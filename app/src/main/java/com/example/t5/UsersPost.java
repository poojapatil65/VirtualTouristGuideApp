package com.example.t5;

public class UsersPost {
    String picture,caption,username;

    public UsersPost() {
    }

    public UsersPost(String picture, String caption, String username) {
        this.picture = picture;
        this.caption = caption;
        this.username = username;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
