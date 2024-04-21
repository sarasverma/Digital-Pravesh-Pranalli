package com.saras.pppandroid.model;

public class Ticket {
    private String date;
    private String person;
    private String place;

    public Ticket(String date, String person, String place) {
        this.date = date;
        this.person = person;
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
