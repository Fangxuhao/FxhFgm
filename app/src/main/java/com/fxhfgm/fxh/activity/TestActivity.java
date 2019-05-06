package com.fxhfgm.fxh.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fxhfgm.fxh.R;
import com.fxhfgm.fxh.domain.BingYingPic;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.Weather;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {



    public DrawerLayout drawerLayout;
    private Button navButton;
    private static final String TAG = TestActivity.class.getSimpleName();
    TextView responseText;
    HeWeather heWeather = new HeWeather();
    private ImageView imageView;
    String bingImgUrl;
    public SwipeRefreshLayout swipeRefreshLayout;

    int idx = -1;//图片日期代号

    private LineChartView lineChart;

    String[] date = {"10-22", "11-22", "12-22", "1-22", "6-22", "5-23", "5-22", "6-22"};//X轴的标注
    int[] score = {-1, 3, -5, 13, 16, 12, 22, 18};//图表的数据点
    int[] score1={-1, 2, 3, 4, 3, 5, 2, 1};//测试
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<PointValue> mPointValues1 = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        drawerLayout=findViewById(R.id.drawer_layout);

        drawerLayout.closeDrawers();

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        //设置下拉刷新进度条的颜色
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        Button testBtn = findViewById(R.id.test_btn);
        testBtn.setOnClickListener(this);

        responseText = findViewById(R.id.test_tv);

        lineChart = findViewById(R.id.line_chart);

        navButton=findViewById(R.id.nav_btn);
        navButton.setOnClickListener(this);

        getAxisXLables();//获取x轴的标注
        getAxisXPoints();//获取坐标点
        initLineChart();//初始化

        HeConfig.switchToFreeServerNode();//免费接口
        HeConfig.init("HE1901181938191543", "c59bb4f51baa47e2aca1b3de6181abf0");//ID以及密钥
        /**
         * 测试示例
         */
        heWeather.getWeather(this, "永康", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherDataListBeansListener() {
            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(TestActivity.this, "sb", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(List<Weather> list) {
                Toast.makeText(TestActivity.this, list.get(0).getLifestyle().get(1).getTxt(), Toast.LENGTH_SHORT).show();
            }
        });


        imageView = findViewById(R.id.test_img);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);//数据持久化

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (idx <= 6) {
                    idx += 1;
                } else {
                    idx = -1;
                }
                getDailyBingPic();
            }
        });

        String bingpic = preferences.getString("bing_img", null);
        if (bingpic != null) {
            Glide.with(this).load(bingpic).into(imageView);
        } else {
            getDailyBingPic();
        }

    }


    private void getDailyBingPic() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String url = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=" + idx + "&n=1";
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    String content = response.body().string();
                    //response.body().string() 不容许同时出现两次

                    final BingYingPic bingYingPic = new Gson().fromJson(content, BingYingPic.class);

                    bingImgUrl = "https://cn.bing.com" + bingYingPic.images.get(0).bingBasePicUrl;//图片地址
                    final String describe = bingYingPic.images.get(0).describe;//图片描述

                    runOnUiThread(new Runnable() {//加载并显示图片
                        @Override
                        public void run() {
                            Glide.with(TestActivity.this).load(bingImgUrl).into(imageView);
                            responseText.setText(describe);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });

                } catch (IOException e) {
                    Log.e(TAG, "失败");
                    swipeRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                }
            }
        }).start();

    }


    /**
     * 图标内每个点的数据获取
     */
    private void getAxisXPoints() {
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
        for (int i = 0; i < score1.length; i++) {
            mPointValues1.add(new PointValue(i, score1[i]));
        }
    }

    /**
     * 设置x轴的数据
     */
    private void getAxisXLables() {
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }

    }

    /**
     * 初始化图表相关数据，如：点的形状，曲线是否平滑，等
     */
    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));//折线的颜色
        Line line1=new Line(mPointValues1).setColor(Color.parseColor("#00CD41"));
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE 方形 ValueShape.CIRCLE 圆形 ValueShape.DIAMOND菱形）
        line.setFilled(false);//是否填充曲线的面积
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line1.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
//      line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        lines.add(line1);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
        axisX.setName("未来几天天气预报");  //表格名称
        axisX.setTextSize(15);//设置字体大小
        //axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(false); //x 轴分割线


//设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(false, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        v.bottom = -10;
        v.top = 50;
        lineChart.setCurrentViewport(v);


        Axis axisY = new Axis().setHasLines(true);
        axisY.setMaxLabelChars(100);//max label length, for example 60
        List<AxisValue> values = new ArrayList<>();
        for (int i = 0; i < 100; i += 10) {
            AxisValue value = new AxisValue(i);
            String label = "";
            value.setLabel(label);
            values.add(value);
        }
        axisY.setValues(values);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_btn:
                responseText.setText(bingImgUrl);
                break;
            case R.id.nav_btn:
                drawerLayout.openDrawer(GravityCompat.START);
                default:
                    break;
        }
//        if (v.getId() == R.id.test_btn) {
//            responseText.setText(bingImgUrl);
//        }
    }
}
