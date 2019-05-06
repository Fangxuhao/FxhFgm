package com.fxhfgm.fxh.gson;

import android.widget.ListView;

import com.google.gson.annotations.SerializedName;

import java.security.PublicKey;
import java.util.List;

/**
 * 方徐浩
 */
public class Weather {
    public String status;//接口状态

    public Basic basic;
    public Now now;
    public Suggestion suggestion;
//    public AQI aqi;
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;//P513

    @SerializedName("lifestyle")
    public List<Suggestion> suggestionList;

}
