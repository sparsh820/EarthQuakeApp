package com.example.earthquake2java;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface USGS_API {

    @GET("query")
    Call<EathQuakeResponse>getEarthQuakes(@Query("format")String format,@Query("minmagnitude")int minmagnitude,
                                          @Query("limit")int limit,@Query("orderby")String orderby);



}
