package com.csg.airvisualapiexam.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csg.airvisualapiexam.JsonAirVisualService;
import com.csg.airvisualapiexam.R;
import com.csg.airvisualapiexam.databinding.FragmentAirVisualBinding;
import com.csg.airvisualapiexam.models.Favorite;
import com.csg.airvisualapiexam.models.Pollutions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AirVisualFragment extends Fragment {

    private FragmentAirVisualBinding mBinding;
    private Favorite mFavorite;

    public AirVisualFragment() {
    }

    public static AirVisualFragment newInstance(Favorite favorite) {
        AirVisualFragment fragment = new AirVisualFragment();
        Bundle args = new Bundle();
        args.putSerializable("favorite", favorite);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFavorite = (Favorite) getArguments().getSerializable("favorite");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_air_visual, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.airvisual.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonAirVisualService service = retrofit.create(JsonAirVisualService.class);


        // 원래 하던 임의의 데이터
        service.getData().enqueue(new Callback<Pollutions>() {
            @Override
            public void onResponse(Call<Pollutions> call, Response<Pollutions> response) {
                mBinding.setPollution(response.body());
            }

            @Override
            public void onFailure(Call<Pollutions> call, Throwable t) {
                Log.d("AirVisualFragment", "onFailure: " + t.getLocalizedMessage());
            }
        });

        // 현재 위치로 할 때,
//        service.getPosition(mFavorite.getLatitude(), mFavorite.getLongitude()).enqueue(new Callback<Pollutions>() {
//            @Override
//            public void onResponse(Call<Pollutions> call, Response<Pollutions> response) {
//                // 해야될곳...
////                MapInfoFragment.newInstance(favoritePosition, response.body()).show(getChildFragmentManager(), "dialog");
//
//            }
//
//            @Override
//            public void onFailure(Call<Pollutions> call, Throwable t) {
//
//            }
//        });
//
//        service.getPosition(marker.getPosition().latitude, marker.getPosition().longitude).enqueue(new Callback<Pollutions>() {
//            @Override
//            public void onResponse(Call<Pollutions> call, Response<Pollutions> response) {
//                MapInfoFragment.newInstance(mFavoritePosition, response.body()).show(getChildFragmentManager(), "dialog");
//
//            }
//
//            @Override
//            public void onFailure(Call<Pollutions> call, Throwable t) {
//
//            }
//        });

    }
}