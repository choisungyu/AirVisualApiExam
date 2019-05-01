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
import android.widget.Toast;

import com.csg.airvisualapiexam.JsonAirVisualService;
import com.csg.airvisualapiexam.R;
import com.csg.airvisualapiexam.databinding.FragmentAirVisualBinding;
import com.csg.airvisualapiexam.models.Pollutions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AirVisualFragment extends Fragment {

    public static final double LATITUDE = 37.4883078;
    public static final double LONGITUDE = 126.8096653;
    private FragmentAirVisualBinding mBinding;
//    private FusedLocationProviderClient mFusedLocationProviderClient;
//    private double mLatitude;
//    private double mLongitude;


    public AirVisualFragment() {
    }

    public static AirVisualFragment newInstance() {
        AirVisualFragment fragment = new AirVisualFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        checkTedPermission();
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

    }


//    private void checkTedPermission() {
//
//        PermissionListener permissionlistener = new PermissionListener() {
//            @SuppressLint("MissingPermission")
//            @Override
//            public void onPermissionGranted() {
//                mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        mLatitude = LATITUDE;
//                        mLongitude = LONGITUDE;
//                    }
//                });
//            }
//
//            @Override
//            public void onPermissionDenied(List<String> deniedPermissions) {
//                Toast.makeText(requireActivity(), "권한이 없습니다.\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
//            }
//
//
//        };
//
//        TedPermission.with(requireActivity())
//                .setPermissionListener(permissionlistener)
//                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .check();
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_air_visual, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toast.makeText(requireContext(), "" + LATITUDE + "," + LONGITUDE, Toast.LENGTH_SHORT).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.airvisual.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonAirVisualService service = retrofit.create(JsonAirVisualService.class);

//        service.getPollutions().enqueue(new Callback<Pollutions>() {
//            @Override
//            public void onResponse(Call<Pollutions> call, Response<Pollutions> r  esponse) {
//                mBinding.setPollution(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<Pollutions> call, Throwable t) {
//                Log.d("AirVisualFragment", "onFailure: " + t.getLocalizedMessage());
//            }
//        });

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


    }
}