package com.edwise.completespring.entities;

public enum UserAccountType {
    REST_USER,
    ADMIN_USER;

    public String role() {
        return "ROLE_" + this.toString();
    }
}
