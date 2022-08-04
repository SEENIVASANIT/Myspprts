package com.example.myproject;

public class Admain_maths_module {
    private String id;
    private String title;
    private String maths;
    private int win;
    private int win1;




    public int getStatus() {
        return status;
    }

    public Admain_maths_module(){}


    public Admain_maths_module(String title, String maths, String id)
    {   this.id=id;
        this.title=title;


        this.maths=maths;



    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMaths() {
        return maths;
    }
    public void setMaths(String maths) {
        this.maths = maths;
    }

    public int getWin1() {
        return win1;
    }

    //private String win1;

    private int status;
    public int getWin() {
        return win;
    }

}

