package com.fxhfgm.fxh.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.fxhfgm.fxh.R;
import com.fxhfgm.fxh.gson.Forecast;
import com.fxhfgm.fxh.gson.Weather;
import com.fxhfgm.fxh.util.HttpUtil;
import com.fxhfgm.fxh.util.Utility;

import java.io.IOException;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    String[] date;//折线图X轴显示数据
    int[] score;//折线图数据
    public Context context;

    public DrawerLayout drawerLayout;
    private Button navButton;
    List<interfaces.heweather.com.interfacesmodule.bean.weather.Weather> lotalList ;
    private static final String TAG = WeatherActivity.class.getSimpleName();
    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;//当前气温
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;//天气预报布局
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortableText;
    private TextView carWashText;
    private TextView sportText;
    private String weathertext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        HeConfig.switchToFreeServerNode();//非商业
        HeConfig.init("HE1901181938191543", "c59bb4f51baa47e2aca1b3de6181abf0");

        //初始化各控件
        weatherLayout = findViewById(R.id.weather_layout);
        titleCity = findViewById(R.id.title_city);//标题显示城市名称
        titleUpdateTime = findViewById(R.id.title_update_time);//标题显示更新时间
        degreeText = findViewById(R.id.now_temperature_text);//当前气温
        weatherInfoText = findViewById(R.id.now_weather_info_text);//天气
        forecastLayout = findViewById(R.id.forecast_layout);//天气预报布局
        aqiText = findViewById(R.id.aqi_text); //空气质量
        pm25Text = findViewById(R.id.pm25_text);//pm2.5
        comfortableText = findViewById(R.id.suggestion_comfortable_info_text);
        carWashText = findViewById(R.id.suggestion_car_wash_text);
        sportText = findViewById(R.id.suggestion_sport_text);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);//数据持久化
        String weatherString = preferences.getString("weather", null);

        if (weatherString != null) {
            //有缓存是直接解析天气数据
            Toast.makeText(this, "读取缓存", Toast.LENGTH_SHORT).show();
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        } else {
            //无缓存时去服务器获取查询天气
            String countyName = getIntent().getStringExtra("county_name");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(countyName);
        }

    }

    /**
     * 根据城市名请求城市天气信息
     *
     * @param countyName 城市名
     */
    private void requestWeather(String countyName) {
        final HeWeather heWeather = new HeWeather();
        heWeather.getWeather(this, countyName, Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherDataListBeansListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, "onError: 查询失败");
            }

            @Override
            public void onSuccess(List<interfaces.heweather.com.interfacesmodule.bean.weather.Weather> list) {
                lotalList=list;
                SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();

                SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);

//                String weatherString=preferences.getString(0);
                String comText = list.get(0).getLifestyle().get(0).getTxt();//0为舒适度指数，1为穿衣指数，2为感冒指数，3为运动指数，4为出行指数，5为紫外线指数，6为洗车指数，7为空气指数

                String sportText = list.get(0).getLifestyle().get(3).getTxt();

                String aqiText = list.get(0).getLifestyle().get(7).getTxt();

                String degree = list.get(0).getNow().getTmp();//当前气温

                String upDate = list.get(0).getUpdate().getLoc();//更新时间（当地时间）

                String fordegreemax1 = list.get(0).getDaily_forecast().get(0).getTmp_max();//第1天最高气温
                String fordegreemax2 = list.get(0).getDaily_forecast().get(1).getTmp_max();//第2天最高气温
                String fordegreemax3 = list.get(0).getDaily_forecast().get(2).getTmp_max();//第3天最高气温
                String fordegreemax4 = list.get(0).getDaily_forecast().get(0).getTmp_max();//第4天最高气温
                String fordegreemax5 = list.get(0).getDaily_forecast().get(1).getTmp_max();//第5天最高气温
                String fordegreemax6 = list.get(0).getDaily_forecast().get(2).getTmp_max();//第6天最高气温
                String fordegreemax7 = list.get(0).getDaily_forecast().get(2).getTmp_max();//第7天最高气温


                String fordegreemin1 = list.get(0).getDaily_forecast().get(0).getTmp_max();//第1天最低气温
                String fordegreemin2 = list.get(0).getDaily_forecast().get(1).getTmp_max();//第2天最低气温
                String fordegreemin3 = list.get(0).getDaily_forecast().get(2).getTmp_max();//第3天最低气温
                String fordegreemin4 = list.get(0).getDaily_forecast().get(0).getTmp_max();//第4天最低气温
                String fordegreemin5 = list.get(0).getDaily_forecast().get(1).getTmp_max();//第5天最低气温
                String fordegreemin6 = list.get(0).getDaily_forecast().get(2).getTmp_max();//第6天最低气温
                String fordegreemin7 = list.get(0).getDaily_forecast().get(2).getTmp_max();//第7天最低气温


                String carWash = list.get(0).getLifestyle().get(6).getTxt();//洗车指数
                String comf = list.get(0).getLifestyle().get(0).getTxt();//舒适度指数
                String sport = list.get(0).getLifestyle().get(3).getTxt();//运动指数
                //  0：舒适度 1：穿衣指数 2：感冒指数 3：运功指数 4：旅游指数 5：紫外线指数 6：洗车指数 7：空气污染扩散条件指数



            }
        });
//        String weatherUrl = "https://free-api.heweather.net/s6/weather?location=CN101210907&key=ae4ba2d5df244d3ea4cc29bcef2b7afc" ;//+ "CN101210907" + "&key=ae4ba2d5df244d3ea4cc29bcef2b7afc";
//        HttpUtil.sendOKHttpRequest(weatherUrl, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {//获取失败
//                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(WeatherActivity.this, "获取天气信息失败1", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//
//            @Override//获取成功
//            public void onResponse(Call call, Response response) throws IOException {
//                final String responseText = response.body().string();
//                final Weather weather = Utility.handleWeatherResponse(responseText);//调用Utility.handleWeatherResponse()方法将返回的json数据转换为Weather对象
//                runOnUiThread(new Runnable() {//切换到主线程
//                    @Override
//                    public void run() {
//                        if (weather != null && "ok".equals(weather.status)) {
//                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
//                            editor.putString("weather", responseText);
//                            editor.apply();
//                            showWeatherInfo(weather);
//                        } else {
//                           // Toast.makeText(WeatherActivity.this, weather.status, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(WeatherActivity.this, "获取天气信息失败2", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//            }
//        });
    }

    /**
     * 处理并展示Weather实体类中的数据
     *
     * @param weather
     */
    private void showWeatherInfo(Weather weather) {
//        String cityName = weather.basic.cityName;
//
//        /**
//         * split(" ")[1]  ： 将字符串以" " 分割形成一个字符串数组，然后通过索引[1] 取出所得数组中的第二个元素的值
//         */
//        String updateTime ="11:55" ;//weather.basic.update.updateTime.split(" ")[1];//split:使用正则表达式的匹配拆分字符串   不懂
//
//        String degree = weather.now.temperature + "℃";
//        String weatherInfo = weather.now.info;
//        titleCity.setText(cityName);
//        titleUpdateTime.setText(updateTime);
//        degreeText.setText(degree);
//        weatherInfoText.setText(weatherInfo);
//        forecastLayout.removeAllViews();//刷新所有视图
//
//        for (Forecast forecast : weather.forecastList) {//遍历forecastList集合，取出每一个元素
//
//            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
//            TextView dateText = findViewById(R.id.forecast_item_data_text);
//            TextView infoText = findViewById(R.id.forecast_item_info_text);
//            TextView maxText = findViewById(R.id.forecast_item_max_text);
//            TextView minText = findViewById(R.id.forecast_item_min_text);
//
////            infoText.setText(forecast.condTxt);
////            maxText.setText(forecast.tmpMax);
////            minText.setText(forecast.tmpMin);
////            dateText.setText(forecast.date);
//            infoText.setText("我");
//            maxText.setText("干");
//            minText.setText("ni");
//            dateText.setText("ma");
//            forecastLayout.addView(view);
//        }
////        if (weather.aqi!=null){
////            aqiText.setText(weather.aqi.aqi);
////            pm25Text.setText(weather.aqi.pm25);
////        }
////        String comfort="舒适度："+weather.suggestion.comfortable.comfortableInfo;
////        String carWash="洗车指数："+weather.suggestion.carWash.carWashInfo;
////        String sport="远动建议“："+weather.suggestion.sport.sportInfo;
//        String comfort="舒适度：";//+weather.suggestion.comfortable.comfortableInfo;
//        String carWash="洗车指数：";//+weather.suggestion.carWash.carWashInfo;
//        String sport="远动建议“：";//+weather.suggestion.sport.sportInfo;
//        comfortableText.setText(comfort);
//        carWashText.setText(carWash);
//        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }
}
