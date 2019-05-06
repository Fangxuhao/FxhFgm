package com.fxhfgm.fxh.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 方徐浩
 * 基础信息
 */
public class Basic {
    //使用@SerializedName方式让JSON字段和Java字段建立映射关系。

    @SerializedName("parent_city")
    public String parentCity;

    @SerializedName("admin_area")
    public String adminArea;//所属行政区域

    @SerializedName("cnty")
    public String countryName;//所属国家名称

    @SerializedName("lat")
    public String latitude;//纬度

    @SerializedName("lon")
    public String longitude;//经度

    @SerializedName("tz")
    public String timeZone;//时区

    @SerializedName("location")//城市名
    public String cityName;

    @SerializedName("cid")//城市id
    public String weatherId;

//    public Update update;
//
//    public class Update {//更新时间类
//        @SerializedName("loc")//更新时间(当地时间)
//        public String updateTime;
//    }
}
