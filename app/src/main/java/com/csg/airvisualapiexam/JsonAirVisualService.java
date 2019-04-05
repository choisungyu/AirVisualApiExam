package com.csg.airvisualapiexam;

import com.csg.airvisualapiexam.models.Pollutions;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonAirVisualService {
    @GET("v2/nearest_city?key=ttk422uxobr8xKYhk")
    Call<Pollutions> getPollutions();

}
