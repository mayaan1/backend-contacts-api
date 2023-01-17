package com.flock.spring_test.model;

public class Contacts {
    private String uid;
    private String contactUID;
    private String contactName;
    private Integer score;

    public Contacts() {

    }
    public Contacts(String uid, String contactUID, String contactName, Integer score) {
        this.uid = uid;
        this.contactUID = contactUID;
        this.contactName = contactName;
        this.score = score;
    }


    public String getUid() {
        return uid;
    }

    public String getContactName() {
        return contactName;
    }

    public Integer getScore() {
        return score;
    }

    public String getContactUID() {
        return contactUID;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setContactUID(String contactUID) {
        this.contactUID = contactUID;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
