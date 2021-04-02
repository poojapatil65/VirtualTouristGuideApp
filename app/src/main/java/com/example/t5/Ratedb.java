package com.example.t5;

public class Ratedb {
    private String ratecount,review;

    public Ratedb() {
    }

    public Ratedb(String ratecount, String review) {
        this.ratecount = ratecount;
        this.review = review;
    }

    public String getRatecount() {
        return ratecount;
    }

    public void setRatecount(String ratecount) {
        this.ratecount = ratecount;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
