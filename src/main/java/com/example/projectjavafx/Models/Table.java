package com.example.projectjavafx.Models;

public class Table {
    private int id;
    private int stt;
    private boolean status;

    public Table(int id, int stt, boolean status) {
        this.id = id;
        this.stt = stt;
        this.status=status;
    }

    public int getId() {
        return id;
    }

    public int getStt() {
        return stt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
