package com.csg.airvisualapiexam;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class BindingAdapters {
    @BindingAdapter("aqius")
    public static void aqius(TextView textView, int aqius) {
        if (aqius <= 15) {
            textView.setText("좋음");
        } else if (aqius <= 50) {
            textView.setText("보통");
        } else if (aqius <= 100) {
            textView.setText("나쁨");
        } else {
            textView.setText("매우나쁨");
        }
    }

    @BindingAdapter("city")
    public static void city(TextView textView, String city) {
        textView.setText("도시 : " + city);
    }

    @BindingAdapter("windSpeed")
    public static void windSpeed(TextView textView, String windSpeed) {
        textView.setText("풍속 : " + windSpeed);

    }

    @BindingAdapter("tempUrl")
    public static void tempUrl(ImageView imageView, String tempUrl) {
        Glide.with(imageView.getContext()).load("https://airvisual.com/images/" + tempUrl + ".png").into(imageView);

    }

}