package com.example.projectjavafx.Models;

public class Staticstics {
    private String id;
    private String name;
    private String type;
    private int totalBill;
    private int totalRevenue;
    private String date;

    public Staticstics(String id, String name, String type, int totalBill, int totalRevenue, String date) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.totalBill = totalBill;
        this.totalRevenue = totalRevenue;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
