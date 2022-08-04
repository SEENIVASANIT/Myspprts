package com.example.myproject;
public class Module_image {
    String imageuri,title,id;
    public Module_image(){
    }
    public Module_image(String imageuri,String title,String id){
        this.imageuri=imageuri;
        this.title=title;
        this.id=id;
    }
    public String getTitle(){return  title;}
    public void setTitle(String title){this.title=title;}
    public String getImageuri(){
        return imageuri;
    }
    public void setImageuri(String imageuri){
        this.imageuri=imageuri;
    }
public String getId(){
        return id;
}
public void  setId(String id){
        this.id=id;
}
}
