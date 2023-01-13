package com.flock.spring_test.model;

public class Contacts {
    String uid;
    String contact_id;
    String contact_name;
    Integer score;

    public Contacts() {

    }

    public Contacts(String uid, String contact_id, String contact_name, Integer score) {
        this.uid = uid;
        this.contact_id = contact_id;
        this.contact_name = contact_name;
        this.score = score;
    }


    public String getUid() {
        return uid;
    }

    public String getContact_name() {
        return contact_name;
    }

    public Integer getScore() {
        return score;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
