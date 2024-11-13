package com.example.projectjavafx.Models;

public class Staff {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String pwd;
    private String role;
    private String status;

    public Staff(int id, String name, String email, String phone, String pwd, String role, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pwd = pwd;
        this.role = role;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
