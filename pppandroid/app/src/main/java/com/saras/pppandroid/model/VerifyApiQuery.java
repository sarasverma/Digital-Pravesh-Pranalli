package com.saras.pppandroid.model;

public class VerifyApiQuery {
    private String person;
    private String place;

    public VerifyApiQuery(String person, String place) {
        this.person = person;
        this.place = place;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
