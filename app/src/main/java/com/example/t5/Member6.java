package com.example.t5;

public class Member6 {

    private String  marketName,country,location,buy,market_info,imageAddress;

    public Member6() {
    }

    public Member6(String marketName, String country, String location, String buy, String market_info, String imageAddress) {
        this.marketName = marketName;
        this.country = country;
        this.location = location;
        this.buy = buy;
        this.market_info = market_info;
        this.imageAddress = imageAddress;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
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

    public void setLocation(String loacation) {
        this.location = loacation;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getMarket_info() {
        return market_info;
    }

    public void setMarket_info(String market_info) {
        this.market_info = market_info;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

}
