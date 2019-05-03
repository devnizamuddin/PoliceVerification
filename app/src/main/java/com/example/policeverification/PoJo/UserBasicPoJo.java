package com.example.policeverification.PoJo;

public class UserBasicPoJo {


    private String id;
    private String name;
    private String role;

    public UserBasicPoJo(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public UserBasicPoJo(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserBasicPoJo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
