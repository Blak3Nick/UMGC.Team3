package com.example.umgcteam3;
import java.io.Serializable;

public class ProfileInformation implements Serializable {
    private String fname;
    private String email;
    private String phone;
    private boolean workoutsBuilt;

    public ProfileInformation() {
    }

    public ProfileInformation(String fname, String email, String phone, boolean workoutsBuilt) {
        this.fname = fname;
        this.email = email;
        this.phone = phone;
        this.workoutsBuilt = workoutsBuilt;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isWorkoutsBuilt() {
        return workoutsBuilt;
    }

    public void setWorkoutsBuilt(boolean workoutsBuilt) {
        this.workoutsBuilt = workoutsBuilt;
    }
}
