package com.example.earthquake2java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class EarthQuake {

   private String magnitude;
   private String place;
   private Long time;
   private String url;

    @Override
    public String toString() {
        return "EarthQuake{" +
                "magnitude='" + magnitude + '\'' +
                ", place='" + place + '\'' +
                ", time=" + time +
                '}';
    }

    public EarthQuake(String mag, String place, long parseLong, String url) {
        this.magnitude=mag;
        this.place=place;
        this.time=parseLong;
        this.url=url;
    }

    public String getMagnitude() {
        return magnitude;
    }


    public String getPlace() {
        return place;
    }

    /*public  EarthQuake(String magnitude, String place, String date,Long time){

         this.magnitude=magnitude;
         this.place=place;
         this.date=date;
         this.time=time;

     }*/


     public long getTime(){

        return time;
     }


    public String getUrl() {
        return url;
    }
}
