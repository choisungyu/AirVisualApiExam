package com.csg.airvisualapiexam.ui;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csg.airvisualapiexam.JsonAirVisualService;
import com.csg.airvisualapiexam.R;
import com.csg.airvisualapiexam.databinding.FragmentAirVisualBinding;
import com.csg.airvisualapiexam.models.Pollutions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AirVisualFragment extends Fragment {

    private FragmentAirVisualBinding mBinding;
//    private Favorite mFavorite;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    public AirVisualFragment() {
    }

    public static AirVisualFragment newInstance() {
        AirVisualFragment fragment = new AirVisualFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mFavorite = (Favorite) getArguments().getSerializable("favorite");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_air_visual, container, false);
        return mBinding.getRoot();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.airvisual.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final JsonAirVisualService service = retrofit.create(JsonAirVisualService.class);


        // 자기 현재 위치 표시하기
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                // 현재 위치로 할 때, ( Favorite 를 데려와야 함 .. 현재 null )
                service.getPosition(location.getLatitude(), location.getLongitude()).enqueue(new Callback<Pollutions>() {
                    @Override
                    public void onResponse(Call<Pollutions> call, Response<Pollutions> response) {
                        // 해야될곳...
                        mBinding.setPollution(response.body());
                    }

                    @Override
                    public void onFailure(Call<Pollutions> call, Throwable t) {

                    }
                });
            }
        });
    }
}