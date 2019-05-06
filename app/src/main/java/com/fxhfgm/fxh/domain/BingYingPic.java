package com.fxhfgm.fxh.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 方徐浩
 */
public class BingYingPic {

    @SerializedName("images")
    public List<BaseBingPic> images;

    public class BaseBingPic {
        @SerializedName("url")
        public String bingBasePicUrl;//这里的链接还需要加上微软的http://cn.bing.com在前面

        @SerializedName("enddate")
        public String endDate;//最后更新的时间

        @SerializedName("startdate")
        public String startDate;

        @SerializedName("copyright")//图片备注，如：位于地中海的切法卢，意大利西西里岛 (© Tuul & Bruno Morandi/eStock Photo）
        public String describe;
//        @SerializedName("urlbase")
//        public  String urlbase;

//        @SerializedName("copyright")
//        public  String copyright;
//        @SerializedName("copyrightlink")
//        public String copyrightlink;
//
//        @SerializedName("title")
//        public String title;
//        @SerializedName("quiz")
//        public  String quiz;
//        @SerializedName("wp")
//        public String wp;
//        @SerializedName("hsh")
//        public String hsh;
//        @SerializedName("drk")
//        public  String drk;
//        @SerializedName("top")
//        public  String top;
//        @SerializedName("bot")
//        public  String bot;
//        @SerializedName("hs")
//        public  String hs;
    }

}

