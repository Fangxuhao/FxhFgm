package com.fxhfgm.fxh.domain;

import org.litepal.crud.LitePalSupport;

/**
 * 方徐浩
 * 县数据
 */
public class County extends LitePalSupport {
    private int id;
    private String name;
    private String weather;//
    private int cityId;//市id

    public County() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
