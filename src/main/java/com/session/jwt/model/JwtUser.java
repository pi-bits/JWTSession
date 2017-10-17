package com.session.jwt.model;

public class JwtUser {
    private String userName;
    private String role;

    public JwtUser() {
    }

    public JwtUser(String userName, String role) {
        this.userName = userName;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
