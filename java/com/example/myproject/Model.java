package com.example.myproject;

public class Model {
    String show_id;
    String show_title;
    String show_maths;

    public Model() {

    }

    public Model(String show_id, String show_title, String show_maths) {
        this.show_id = show_id;
        this.show_title = show_title;
        this.show_maths = show_maths;
    }

    public String getShow_id() {
        return show_id;
    }

    public void setShow_id(String show_id) {
        this.show_id = show_id;
    }

    public String getShow_title() {
        return show_title;
    }

    public void setShow_title(String show_title) {
        this.show_title = show_title;
    }

    public String getShow_maths() {
        return show_maths;
    }

    public void setShow_maths(String show_maths) {
        this.show_maths = show_maths;
    }
}

