package com.fxhfgm.fxh.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 方徐浩
 * 实况天气
 */
public class Now {

    @SerializedName("cond_code")
    public String condCode;//实时天气代号

    @SerializedName("fl")
    public String fl;//体感温度

    @SerializedName("hum")
    public String hun;//相对湿度

    @SerializedName("pcpn")
    public String pcpn ;//降水量

    @SerializedName("pres")
    public String pres;//大气压强

    @SerializedName("vis")
    public String vis;//能见度

    @SerializedName("cloud")
    public String cloud;//云量
    @SerializedName("wind_deg")
    public String windDeg;//风向360角度

    @SerializedName("wind_dir")
    public String windDir;//风向

    @SerializedName("wind_sc")
    public String windSc;//风力

    @SerializedName("wind_spd")
    public String windSpd;//风速


    @SerializedName("cond_txt")
    public String info;//实时天气

    @SerializedName("tmp")
    public String temperature;//实时温度

}
