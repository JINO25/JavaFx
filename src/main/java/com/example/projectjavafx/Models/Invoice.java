package com.example.projectjavafx.Models;

public class Invoice {
    private String name;
    private int quantity;
    private int price;
    private int total;
    private String date;

    public Invoice(String name, int quantity, int price, int total, String date) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
