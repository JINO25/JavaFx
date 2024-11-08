package com.example.projectjavafx.Models;

public class Product {
    private String id;
    private String name;
    private String type;
    private int stock;
    private int price;
    private String photo;

    public Product(String id, String name, String type, int stock, int price, String photo) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.stock = stock;
        this.price = price;
        this.photo = photo;
    }

    public Product(String id, String name, int price, String photo) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.photo = photo;
    }

    public Product(String id, String name, int stock, int price) {
        this.id=id;
        this.name=name;
        //stock there is quantities of product
        this.stock=stock;
        this.price=price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getStock() {
        return stock;
    }

    public int getPrice() {
        return price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
