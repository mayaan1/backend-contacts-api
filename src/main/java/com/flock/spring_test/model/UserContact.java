package com.flock.spring_test.model;

public class UserContact {
        String uid;
        String mobileNo;
        String address;
        String email;
        String name;

        public UserContact() {

        }

        public UserContact(String uid, String mobileNo, String address, String email, String name ) {
            this.uid = uid;
            this.mobileNo = mobileNo;
            this.address = address;
            this.email = email;
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public String getEmail() {
            return email;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public String getName() {
            return name;
        }

        public String getUid() {
            return uid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

}
