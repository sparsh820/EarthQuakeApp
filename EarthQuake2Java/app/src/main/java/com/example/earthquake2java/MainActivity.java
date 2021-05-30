package com.example.earthquake2java;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getName();
    RecyclerView rvCourses;

    RecyclerViewAdapter adapter;
    TextView mEmptyTextview;
   public static ArrayList<EarthQuake>earthQuakes=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            doWork();
        }

/*
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        if (orderBy.equals(getString(R.string.settings_order_by_default))){
            extractdata(minMagnitude, "magnitude-asc");
        }else{
            extractdata(minMagnitude,orderBy);
        }*/


        RecyclerView recyclerView = findViewById(R.id.rvCourses);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        EarthQuake earthQuake=earthQuakes.get(position);

                        // Convert the String URL into a URI object (to pass into the Intent constructor)
                        Uri earthquakeUri = Uri.parse(earthQuake.getUrl());

                        // Create a new intent to view the earthquake URI
                        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                        // Send the intent to launch a new activity
                        startActivity(websiteIntent);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );






/*
        Retrofit r= new Retrofit.Builder().
                baseUrl("https://earthquake.usgs.gov/fdsnws/event/1/").
                addConverterFactory(GsonConverterFactory.create()).build();

         USGS_API api=r.create(USGS_API.class);
         Call<EathQuakeResponse>call=api.getEarthQuakes("geojson",5,10);
         call.enqueue(new Callback<EathQuakeResponse>() {
              @Override
             public void onResponse(Call<EathQuakeResponse> call, Response<EathQuakeResponse> response) {
                 if(!response.isSuccessful()){
                     return;
                 }

                 for(int i=0;i<response.body().getFeatures().size();i++){
                 earthQuakes.add(new EarthQuake(response.body().getFeatures().get(i).getProperties().getMag(),
                             response.body().getFeatures().get(i).getProperties().getPlace(),
                             Long.parseLong(response.body().getFeatures().get(i).getProperties().getTime())
                            ));
                 }

                Log.d("hey",earthQuakes.toString());

                 rvCourses=findViewById(R.id.rvCourses);
                 rvCourses.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));

                RecyclerViewAdapter adapter=new RecyclerViewAdapter(earthQuakes);
                 rvCourses.setAdapter(adapter);



             }

              @Override
             public void onFailure(Call<EathQuakeResponse> call, Throwable t) {
                 Log.i("FAILED!!!","FAILED!!");
          }
       });
*/





    }

    private void extractdata(String minMagnitude,String Orderby) {
        Retrofit r= new Retrofit.Builder().
                baseUrl("https://earthquake.usgs.gov/fdsnws/event/1/").
                addConverterFactory(GsonConverterFactory.create()).build();

        USGS_API api=r.create(USGS_API.class);
        Call<EathQuakeResponse>call=api.getEarthQuakes("geojson", Integer.parseInt(minMagnitude),60,Orderby);
        call.enqueue(new Callback<EathQuakeResponse>() {
            @Override
            public void onResponse(Call<EathQuakeResponse> call, Response<EathQuakeResponse> response) {
                if(!response.isSuccessful()){
                    return;
                }

                for(int i=0;i<response.body().getFeatures().size();i++){
                    earthQuakes.add(new EarthQuake(response.body().getFeatures().get(i).getProperties().getMag(),
                            response.body().getFeatures().get(i).getProperties().getPlace(),
                            Long.parseLong(response.body().getFeatures().get(i).getProperties().getTime()),
                            response.body().getFeatures().get(i).getProperties().getUrl()
                    ));
                }

                Log.d("hey",earthQuakes.toString());

                View loadingIndicator = findViewById(R.id.progressBar);
                loadingIndicator.setVisibility(View.GONE);

                rvCourses=findViewById(R.id.rvCourses);
                rvCourses.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));

                adapter=new RecyclerViewAdapter(earthQuakes);
                rvCourses.setAdapter(adapter);



            }

            @Override
            public void onFailure(Call<EathQuakeResponse> call, Throwable t) {
                Log.i("FAILED!!!","FAILED!!");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            adapter.clear();
            startActivity(settingsIntent);


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void doWork() {

       ConnectivityManager cm=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);


        cm.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){

            public void onAvailable(Network network) {
                Log.e("hola", "The default network is now: " + network);
                 network=cm.getActiveNetwork();
                  if(network!=null){
                      SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                      String minMagnitude = sharedPrefs.getString(
                              getString(R.string.settings_min_magnitude_key),
                              getString(R.string.settings_min_magnitude_default));
                      String orderBy = sharedPrefs.getString(
                              getString(R.string.settings_order_by_key),
                              getString(R.string.settings_order_by_default)
                                                      );

                      if (orderBy.equals(getString(R.string.settings_order_by_default))){
                          extractdata(minMagnitude, "magnitude-asc");
                      }else{
                          extractdata(minMagnitude,orderBy);

                      }

                  }
            }

            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
                Log.i(TAG,network+" "+networkCapabilities.toString()+" "+networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        View loadingIndicator = findViewById(R.id.progressBar);
                        loadingIndicator.setVisibility(View.VISIBLE);
                        rvCourses=findViewById(R.id.rvCourses);
                        mEmptyTextview=findViewById(R.id.empty_view);
                        mEmptyTextview.setVisibility(View.GONE);
                        rvCourses.setVisibility(View.VISIBLE);
                    }
                });
                if(networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET)){

                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    String minMagnitude = sharedPrefs.getString(
                            getString(R.string.settings_min_magnitude_key),
                            getString(R.string.settings_min_magnitude_default));
                    String orderBy = sharedPrefs.getString(
                            getString(R.string.settings_order_by_key),
                            getString(R.string.settings_order_by_default)
                    );

                    if (orderBy.equals(getString(R.string.settings_order_by_default))){
                        extractdata(minMagnitude, "magnitude-asc");
                    }else{
                        extractdata(minMagnitude,orderBy);

                    }


                }
            }

               public void onLost(Network network) {
                /*View loadingIndicator = findViewById(R.id.progressBar);
                loadingIndicator.setVisibility(View.GONE);
                Log.e(TAG, "The application no longer has a default network. The last default network was " + network);
                rvCourses=findViewById(R.id.rvCourses);
                mEmptyTextview=findViewById(R.id.empty_view);
                mEmptyTextview.setText("NO INTERNET CONNECTION");
                rvCourses.setVisibility(View.GONE);
                mEmptyTextview.setVisibility(View.VISIBLE);*/

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        View loadingIndicator = findViewById(R.id.progressBar);
                        loadingIndicator.setVisibility(View.GONE);
                        Log.e(TAG, "The application no longer has a default network. The last default network was " + network);
                        rvCourses=findViewById(R.id.rvCourses);
                        mEmptyTextview=findViewById(R.id.empty_view);
                        mEmptyTextview.setText("NO INTERNET CONNECTION");
                        rvCourses.setVisibility(View.GONE);
                        mEmptyTextview.setVisibility(View.VISIBLE);

                    }
                });


            }





        }

        );


    }






}


