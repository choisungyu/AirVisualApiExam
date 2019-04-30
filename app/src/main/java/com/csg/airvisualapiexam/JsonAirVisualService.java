package com.csg.airvisualapiexam;

import com.csg.airvisualapiexam.models.Pollutions;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonAirVisualService {
    @GET("v2/nearest_city?key=ttk422uxobr8xKYhk")
    Call<Pollutions> getPollutions();

    @GET("v2/nearest_city?lat=37.4883078&lon=126.8096653&key=ttk422uxobr8xKYhk")
    Call<Pollutions> getData();

}
