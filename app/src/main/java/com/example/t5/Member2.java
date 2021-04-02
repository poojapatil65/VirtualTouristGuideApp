package com.example.t5;

public class Member2  {

    private String mimageAddress2,
            mbeachName,
            mcountry,
            mstate,
            mideal_trip_duration,
            mtime_to_visit,
            minfo;

    public Member2() {

    }

    public Member2(String mimageAddress2, String mbeachName, String mcountry, String mstate, String mideal_trip_duration, String mtime_to_visit, String minfo) {
        this.mimageAddress2 = mimageAddress2;
        this.mbeachName = mbeachName;
        this.mcountry = mcountry;
        this.mstate = mstate;
        this.mideal_trip_duration = mideal_trip_duration;
        this.mtime_to_visit = mtime_to_visit;
        this.minfo = minfo;
    }

    public String getMimageAddress2() {
        return mimageAddress2;
    }

    public void setMimageAddress2(String mimageAddress2) {
        this.mimageAddress2 = mimageAddress2;
    }

    public String getMbeachName() {
        return mbeachName;
    }

    public void setMbeachName(String mbeachName) {
        this.mbeachName = mbeachName;
    }

    public String getMcountry() {
        return mcountry;
    }

    public void setMcountry(String mcountry) {
        this.mcountry = mcountry;
    }

    public String getMstate() {
        return mstate;
    }

    public void setMstate(String mstate) {
        this.mstate = mstate;
    }

    public String getMideal_trip_duration() {
        return mideal_trip_duration;
    }

    public void setMideal_trip_duration(String mideal_trip_duration) {
        this.mideal_trip_duration = mideal_trip_duration;
    }

    public String getMtime_to_visit() {
        return mtime_to_visit;
    }

    public void setMtime_to_visit(String mtime_to_visit) {
        this.mtime_to_visit = mtime_to_visit;
    }

    public String getMinfo() {
        return minfo;
    }

    public void setMinfo(String minfo) {
        this.minfo = minfo;
    }
}
