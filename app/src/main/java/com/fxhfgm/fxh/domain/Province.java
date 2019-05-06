package com.fxhfgm.fxh.domain;

import org.litepal.crud.LitePalSupport;

/**
 * 方徐浩
 * 省数据
 */
public class Province extends LitePalSupport {
    private int id;
    private String name;
    private int code;//代号

    public Province(String name) {
        this.name = name;
    }

    public Province(int code) {
        this.code = code;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Province() {
    }
}
