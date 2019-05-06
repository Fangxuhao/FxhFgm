package com.fxhfgm.fxh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fxhfgm.fxh.activity.WeatherActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Fragment fragment=new Fragment();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        //判断SharedPreferences中是否有缓存数据，如果有则直接跳转至WeatherActivity，如果没有则让用户选择城市
        if (preferences.getString("weather", null) != null) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
