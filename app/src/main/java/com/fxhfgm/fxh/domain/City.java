package com.fxhfgm.fxh.domain;

import org.litepal.crud.LitePalSupport;

/**
 * 方徐浩
 * 市数据
 */
public class City extends LitePalSupport {
    private  int id;
    private String name;
    private int code;//市代号
    private int provinceId;//省Id


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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public City() {
    }

    public City(String name, int code, int provinceId) {
        this.name = name;
        this.code = code;
        this.provinceId = provinceId;
    }
}
