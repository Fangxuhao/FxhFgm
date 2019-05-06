package com.fxhfgm.fxh.gson;

import com.google.gson.annotations.SerializedName;

/**
 * 方徐浩
 */
public class Forecast {
    @SerializedName("cond_code_d")
    public String dayCondCode;//白天天气代号

    @SerializedName("cond_code_n")
    public String nightCondCode;//晚上天气代号

    @SerializedName("cond_txt_n")
    public String nightCondTxt;//晚上天气

    @SerializedName("hum")
    public String hum;//相对湿度

    @SerializedName("pcpn")
    public String pcpn;//降水量

    @SerializedName("pop")
    public String pop;//降水概率

    @SerializedName("pres")
    public String pres;//大气压强

    @SerializedName("uv_index")
    public String UltravioletIntensityIndex;//紫外线强度

    @SerializedName("vis")
    public String vis;//能见度。单位：km

    @SerializedName("wind_deg")
    public String windDeg;//风向360角度

    @SerializedName("wind_dir")
    public String windDir;//风向

    @SerializedName("wind_sc")
    public String windSc;//风力

    @SerializedName("wind_spd")
    public String windSpd;//风速

    @SerializedName("sr")
    public String sr;//日出时间

    @SerializedName("ss")
    public String ss;//日落时间

    @SerializedName("mr")
    public String mr;//月升时间

    @SerializedName("ms")
    public String ms;//月落时间

    @SerializedName("date")
    public String date;//预报时间

    @SerializedName("tmp_max")
    public String tmpMax;//最高温度

    @SerializedName("tmp_min")
    public String tmpMin;//最低温度

    @SerializedName("cond_txt_d")
    public String condTxt;//白天天气

}
