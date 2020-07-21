package com.example.loginactivity;

public class Msg {
    public String name;
    public String comment;
    public String time;

    public Msg(String name,String comment,String time){
        this.name = name;
        this.comment = comment;
        this.time = time;
    }
    public String getName(){
        return name;
    }
    public String getComment(){
        return comment;
    }
    public String getTime(){
        return time;
    }
}

