package com.csg.airvisualapiexam;

import com.csg.airvisualapiexam.models.Favorite;
import com.csg.airvisualapiexam.models.Pollutions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonAirVisualService {
    @GET("v2/nearest_city?key=ttk422uxobr8xKYhk")
    Call<Pollutions> getData();

    @GET("v2/nearest_city?key=ttk422uxobr8xKYhk")
    Call<Pollutions> getPosition(
        @Query("lat") double lat,
        @Query("lon") double lon
    );




}
