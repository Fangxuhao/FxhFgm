package com.fxhfgm.fxh.util;

import android.text.TextUtils;

import com.fxhfgm.fxh.domain.City;
import com.fxhfgm.fxh.domain.County;
import com.fxhfgm.fxh.domain.Province;
import com.fxhfgm.fxh.gson.AQI;
import com.fxhfgm.fxh.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 方徐浩
 */
public class Utility {
    /**
     * 解析返回的省级数据
     *
     * @param response
     * @return
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvince = new JSONArray(response);
                for (int i = 0; i < allProvince.length(); i++) {
                    JSONObject provinceObject = allProvince.getJSONObject(i);

                    Province province = new Province();//实例化省
                    province.setName(provinceObject.getString("name"));
                    province.setCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 解析返回的市级数据
     */
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCode(cityObject.getInt("id"));
                    city.setName(cityObject.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * j解析返回的县级数据
     */
    public static boolean handleCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setName(countyObject.getString("name"));
                    county.setCityId(cityId);
                    county.setWeather(countyObject.getString("weather_id"));
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    /**
     * 将返回的JSON数据解析成Weather实体类
     *
     * @param response
     * @return
     */
    public static Weather handleWeatherResponse(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static AQI handleAQIResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
            String AQIContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(AQIContent, AQI.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
