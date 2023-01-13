package com.flock.spring_test.model;

public class UserContact {
        String uid;
        String mobile_no;
        String address;
        String email;
        String name;

        UserContact() {

        }

        public UserContact(String uid, String mobile_no, String address, String email, String name ) {
            this.uid = uid;
            this.mobile_no = mobile_no;
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

        public String getMobile_no() {
            return mobile_no;
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

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

}
