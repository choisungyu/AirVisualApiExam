package com.csg.airvisualapiexam;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class BindingAdapters {
    @BindingAdapter("aqius")
    public static void aqius(TextView textView, int aqius) {
        if (aqius <= 15) {
            textView.setText("미세먼지 : 좋음");
        } else if (aqius <= 50) {
            textView.setText("미세먼지 : 보통");
        } else if (aqius <= 100) {
            textView.setText("미세먼지 : 나쁨");
        } else {
            textView.setText("미세먼지 : 매우나쁨");
        }
    }

    @BindingAdapter("fineDust")
    public static void fineDust(TextView textView, int aqius) {
        textView.setText("미세먼지농도 : " + aqius);
    }

    @BindingAdapter("humid")
    public static void humid(TextView textView, String humid) {
        textView.setText("습도 : " + humid);
    }

    @BindingAdapter("city")
    public static void city(TextView textView, String city) {
        textView.setText("도시 : " + city);
    }

    @BindingAdapter("windSpeed")
    public static void windSpeed(TextView textView, String windSpeed) {
        textView.setText("풍속 : " + windSpeed);

    }

    @BindingAdapter("temp")
    public static void temp(TextView textView, String tp) {
        textView.setText("온도 : " + tp + "도");

    }


    @BindingAdapter("tempUrl")
    public static void tempUrl(ImageView imageView, String tempUrl) {
        Glide.with(imageView.getContext()).load("https://airvisual.com/images/" + tempUrl + ".png").into(imageView);

    }

}
