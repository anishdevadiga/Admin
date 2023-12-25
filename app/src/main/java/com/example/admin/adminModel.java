package com.example.admin;

public class adminModel {
    String password;
    adminModel(){}

    public adminModel(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
