package com.example.earthquake2java;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.EarthQuakeViewHolder> {

    ArrayList<EarthQuake>earthQuakes;
    String locationOffset;
    String primaryLocation;
    RecyclerView mRecyclerView;
    private Context context;

    public RecyclerViewAdapter(ArrayList<EarthQuake> earthQuakes) {
        this.earthQuakes =earthQuakes;
    }




    @NonNull
    @Override
    public EarthQuakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li=(LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=li.inflate(R.layout.list_item_earthquake_card,parent,false);
        context = parent.getContext();
        return new EarthQuakeViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull EarthQuakeViewHolder holder, int position) {

        EarthQuake earthQuake=earthQuakes.get(position);
        String placeText=earthQuake.getPlace();
        if (placeText.contains("of")) {
            String[] parts = placeText.split("of");
            locationOffset = parts[0] + "of";
            primaryLocation = parts[1];
        } else {
            locationOffset = context.getString(R.string.near_the);
            primaryLocation = placeText;
        }

        holder.primaryLocation.setText(primaryLocation);
        holder.location_offset.setText(locationOffset);
        holder.magnitude.setText(earthQuake.getMagnitude());
        long time=earthQuake.getTime();
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        String time2display=timeFormatter.format(time);
        holder.time.setText(String.valueOf(time2display));
        int magnitudeColour=getMagnitudeColor(holder,earthQuake.getMagnitude());
        holder.magCircle.setColor(magnitudeColour);



    }

    private int getMagnitudeColor(EarthQuakeViewHolder holder,String magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(Double.parseDouble(magnitude));
        if(magnitudeFloor<0){
            return 0;
        }
        Log.i("magnitude",magnitude+ ":" +""+magnitudeFloor+"");
        switch (magnitudeFloor) {
            case 0:
                magnitudeColorResourceId = R.color.black;
                break;
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(context, magnitudeColorResourceId);
    }



    @Override
    public int getItemCount() {
        return earthQuakes.size();
    }

    public void clear() {
        int size = earthQuakes.size();
        earthQuakes.clear();
        notifyItemRangeRemoved(0, size);
    }


    class EarthQuakeViewHolder extends RecyclerView.ViewHolder{


        TextView primaryLocation,magnitude,time,location_offset;
        GradientDrawable magCircle;

        public EarthQuakeViewHolder(@NonNull View itemView) {
            super(itemView);
            primaryLocation=itemView.findViewById(R.id.primary_location);
            magnitude=itemView.findViewById(R.id.magnitude);
            time=itemView.findViewById(R.id.time);
            location_offset=itemView.findViewById(R.id.location_offset);
            magCircle=(GradientDrawable)magnitude.getBackground();
        }
    }



}
