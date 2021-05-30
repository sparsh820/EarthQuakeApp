package com.example.earthquake2java;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    String locationOffset;
    String primaryLocation;

    public EarthQuakeAdapter(Context context, ArrayList<EarthQuake>Earthquake) {
        super(context,0,Earthquake);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView=convertView;

        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        EarthQuake currentQuake=getItem(position);



        TextView magnitude= listItemView.findViewById(R.id.magnitude);
        magnitude.setText(currentQuake.getMagnitude());
        TextView timetextview=listItemView.findViewById(R.id.time);
        long time=currentQuake.getTime();
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        String time2display=timeFormatter.format(time);
        timetextview.setText(time2display);
        TextView place=listItemView.findViewById(R.id.primary_location);
        String placeText=currentQuake.getPlace();
        if (placeText.contains("of")) {
            String[] parts = placeText.split("of");
            locationOffset = parts[0] + "of";
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = placeText;
        }


        place.setText(primaryLocation);


        return listItemView;
    }



    }

