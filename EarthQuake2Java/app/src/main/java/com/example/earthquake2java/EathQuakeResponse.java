package com.example.earthquake2java;

import android.util.Property;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Keep
public class EathQuakeResponse {


     @SerializedName("type")
     private String type;


     @SerializedName("features")
    private ArrayList<Features> features;

    public String getType() {
        return type;
    }

    public ArrayList<Features> getFeatures() {
        return features;
    }



    public EathQuakeResponse(String type,  ArrayList<Features> features) {
        this.type = type;
        this.features = features;
    }


    public class Features{

        public Features(Properties properties) {
            this.properties = properties;
        }

        @SerializedName("properties")
        private Properties properties;


        public Properties getProperties() {
            return this.properties;
        }

        @Override
        public String toString() {
            return properties.toString();
        }
    }



    public  class Properties{

        @SerializedName("mag")
        private String mag;

        @SerializedName("place")
        private  String place;

        @SerializedName("time")
        private String time;

        @SerializedName("url")
        private String url;

        public Properties(String mag, String place, String time,String url) {
            this.mag = mag;
            this.place = place;
            this.time = time;
            this.url=url;

        }

        public String getMag() {
            return mag;
        }

        public String getPlace() {
            return place;
        }

        public String getTime() {
            return time;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return "Properties{" +
                    "mag='" + mag + '\'' +
                    ", place='" + place + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }




}

