package com.example.t5;

public class Member7 {
    private String agency_name, vehicle_name, vehicle_type, contact_no, rs_per_hour, other_info,imageAddress;

    public Member7() {
    }

    public Member7(String agency_name, String vehicle_name, String vehicle_type, String contact_no, String rs_per_hour, String other_info, String imageAddress) {
        this.agency_name = agency_name;
        this.vehicle_name = vehicle_name;
        this.vehicle_type = vehicle_type;
        this.contact_no = contact_no;
        this.rs_per_hour = rs_per_hour;
        this.other_info = other_info;
        this.imageAddress = imageAddress;
    }

    public String getAgency_name() {
        return agency_name;
    }

    public void setAgency_name(String agency_name) {
        this.agency_name = agency_name;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getRs_per_hour() {
        return rs_per_hour;
    }

    public void setRs_per_hour(String rs_per_hour) {
        this.rs_per_hour = rs_per_hour;
    }

    public String getOther_info() {
        return other_info;
    }

    public void setOther_info(String other_info) {
        this.other_info = other_info;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }
}

