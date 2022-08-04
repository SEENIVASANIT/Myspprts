package com.example.myproject;

public class Upload {
    private String nname;
    private String nImageuri;
    public Upload(){

    }


    public Upload(String name, String imageuri){
        if(name.trim().equals("")){
            name="No Title";

        }
nname=name;
nImageuri=imageuri;

    }

    public String getName(){
        return nname;
    }
    public void setName(String name){
        nname=name;

    }
    public String getImageuri(){
        return nImageuri;
    }
    public void setImageuri(String imageuri){
        nImageuri=imageuri;
    }
}
