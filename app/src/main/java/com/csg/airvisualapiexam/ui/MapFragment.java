package com.csg.airvisualapiexam.ui;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.csg.airvisualapiexam.JsonAirVisualService;
import com.csg.airvisualapiexam.R;
import com.csg.airvisualapiexam.models.Favorite;
import com.csg.airvisualapiexam.models.Pollutions;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1000;
    private GoogleMap mMap;
    private static final String TAG = MapFragment.class.getSimpleName();
    private PlacesClient mPlacesClient;
    private Favorite mFavorite;


    //--------
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private LocationCallback mLocationCallback;
    private LocationRequest locationRequest;
    private LatLng mCurrentLocation;
    private JsonAirVisualService mService;
    private Favorite mFavoritePosition;
    //--------

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(Favorite favoritePosition) {

        MapFragment mapFragment = new MapFragment();
        Bundle args = new Bundle();
        args.putSerializable("mFavoritePosition", favoritePosition);
        mapFragment.setArguments(args);
        return mapFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mPlacesClient = Places.createClient(context);

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // 자기 현재 위치 표시하기
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        // 위치 결과을 콜백으로 받음
//        mLocationCallback = new LocationCallback() {
//
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult == null) {
//                    return;
//                }
//                for (Location location : locationResult.getLocations()) {
//                    mCurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());
//
//                    // 자기위치 마커 생성
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.position(mCurrentLocation);
//                    markerOptions.title("현재위치");
////                  현재위치 mCurrentLocation.longitude + mCurrentLocation.latitude
//                    mMap.addMarker(markerOptions);
//
//                    // 위치로 이동
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 15.0f));
//                    Toast.makeText(requireContext(), "위치갱신", Toast.LENGTH_SHORT).show();
//                    // 진짜 위치 정보
//                }
//            }
//
//
//        };

        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                mCurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                // 자기위치 마커 생성
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(mCurrentLocation);
                markerOptions.title("현재위치");

                mMap.addMarker(markerOptions);

                // 위치로 이동
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 15.0f));
                Toast.makeText(requireContext(), "위치갱신", Toast.LENGTH_SHORT).show();
                // 진짜 위치 정보
            }
        });

        // locationRequest 생성
        locationRequest = LocationRequest.create();

        // 추가적인 베터리 소모 업이 위치정보 획득
        locationRequest.setPriority(LocationRequest.PRIORITY_NO_POWER);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //----------autocompleteFragment
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.PHOTO_METADATAS,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.d(TAG, "Place: " + place.toString());

                // -- 옮겨 빼 놓는 이유 --
                // place 객체 범주가 넓다 보니깐 favorite 에 옮겨 심는 것
                mFavorite = new Favorite();
                mFavorite.setAddress(place.getAddress());
                mFavorite.setName(place.getName());
                mFavorite.setMemoId(place.getId());
                if (place.getLatLng() != null) {

                    mFavorite.setLatitude(place.getLatLng().latitude);
                    mFavorite.setLongitude(place.getLatLng().longitude);
                }

                // autoComplete Selected place
                LatLng selectedPlace = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                MarkerOptions markerOptions = new MarkerOptions().position(selectedPlace).title(place.getAddress())
                        .snippet(place.getAddress());
//                        .title(place.getName());
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                mMap.addMarker(markerOptions);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedPlace));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, 15.0f));


            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        //----------autocompleteFragment

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.airvisual.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(JsonAirVisualService.class);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // DialogFragment 띄우기
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mFavoritePosition = new Favorite();
                mFavoritePosition.setAddress(marker.getTitle());
                mFavoritePosition.setLatitude(marker.getPosition().latitude);
                mFavoritePosition.setLongitude(marker.getPosition().longitude);

                mService.getPosition(marker.getPosition().latitude, marker.getPosition().longitude).enqueue(new Callback<Pollutions>() {
                    @Override
                    public void onResponse(Call<Pollutions> call, Response<Pollutions> response) {
                        MapInfoFragment.newInstance(mFavoritePosition, response.body()).show(getChildFragmentManager(), "dialog");

                    }

                    @Override
                    public void onFailure(Call<Pollutions> call, Throwable t) {

                    }
                });

                return true;
            }
        });

        // 클릭
//        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);


        UiSettings mapUiSettings = mMap.getUiSettings();
        mapUiSettings.setZoomControlsEnabled(true);
        mapUiSettings.setMapToolbarEnabled(false);


        // 권한체크 승인 안된것
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 사용자가 권한 거부를 한 번 이상 요청이 있으면
                // sees the explanation,

                // 퍼미션 요청
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                                , Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
            } else {
                // 퍼미션 요청
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                                , Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // 권한승인이 된 경우
            mMap.setMyLocationEnabled(true);

            // 권한 체크를 하고 켜줘야 함
//        mMap.setMyLocationEnabled(true);

        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.

                // 허락 된 것
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 허락 됏으면 켜주기
                    mMap.setMyLocationEnabled(true);
                    startLocationUpdates();


                } else {
                    // permission denied,
                    // 거부했을때 , 재요청하는 다이얼로그 띄우기 try again to request the permission.
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle("권한")
                            .setMessage("설정에서 언제든지 설정할 수 있음")
                            .setPositiveButton("설정", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // 설정 화면
                                    Intent intent = new Intent();
                                    String packageName = requireContext().getPackageName();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", packageName, null);
                                    intent.setData(uri);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("닫기", null)
                            .show();

                }
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // 지속적으로 현재위치를 얻게 하는 코드
        startLocationUpdates();
    }

    private void startLocationUpdates() {

        // 거부
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        // 허락
        else {


//            mFusedLocationProviderClient.requestLocationUpdates(locationRequest,
//                    mLocationCallback,
//                    null /* Looper */);
            // mFused 를 여기서 하고 

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
//        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);

    }


    @Override
    public void onMapLongClick(LatLng latLng) {
        getAddress(latLng);
    }

    private void getAddress(LatLng latLng) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(requireContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            String address = addresses.get(0).getAddressLine(0);

            mMap.addMarker(new MarkerOptions().position(latLng).title(address));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
