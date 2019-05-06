package com.fxhfgm.fxh.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 方徐浩
 */
public class Update {
    @SerializedName("loc")
    public  String localTime;//当地时间
    @SerializedName("utc")
    public String utcTime;//UTC时间

}
