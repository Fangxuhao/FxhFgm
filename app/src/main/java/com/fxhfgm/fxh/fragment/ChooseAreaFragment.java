package com.fxhfgm.fxh.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxhfgm.fxh.MainActivity;
import com.fxhfgm.fxh.R;
import com.fxhfgm.fxh.activity.TestActivity;
import com.fxhfgm.fxh.activity.WeatherActivity;
import com.fxhfgm.fxh.adapter.areaAdapter;
import com.fxhfgm.fxh.domain.City;
import com.fxhfgm.fxh.domain.County;
import com.fxhfgm.fxh.domain.Province;
import com.fxhfgm.fxh.util.HttpUtil;
import com.fxhfgm.fxh.util.Utility;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 方徐浩
 */
public class ChooseAreaFragment extends Fragment {
    private ListView listView;
    private TextView titleText;
    private Button button;//标题栏返回键
    //    private ArrayAdapter<String> arrayAdapter;
    private areaAdapter areaAdapter;
    private static final int LEVEL_PROVINCE = 0;//省级别
    private static final int LEVEL_CITY = 1;//市级别
    private static final int LEVEL_COUNTY = 2;//县级别
    private RecyclerView recyclerView;
    private List<String> dataList = new ArrayList<>();//储存各省市县的名字数据
    private List<Province> provinceList;//省列表
    private List<City> cityList;//市列表
    private List<County> countyList;//县列表
    private Province selectedProvince;//被选中的省
    private City selectedCity;//被选中的市
    private int currentLevel;//被选中的级别
    private ProgressDialog progressDialog;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        titleText = view.findViewById(R.id.title_text);
        button = view.findViewById(R.id.choose_area_back_btn);
        recyclerView = view.findViewById(R.id.choose_area_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        areaAdapter = new areaAdapter(dataList);
        recyclerView.setAdapter(areaAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        areaAdapter.setOnItemClickListent(new areaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(currentLevel==LEVEL_PROVINCE){
                    selectedProvince=provinceList.get(position);
                    queryCities();
                }else if (currentLevel==LEVEL_CITY){
                    selectedCity=cityList.get(position);
                    queryCounties();
                }else if (currentLevel==LEVEL_COUNTY){

                    String countyName=countyList.get(position).getName();
                    if (getActivity() instanceof MainActivity){
                    Intent intent=new Intent(getActivity(),WeatherActivity.class);

                    intent.putExtra("county_name",countyName);
                    startActivity(intent);
                    getActivity().finish();}
                    else if (getActivity() instanceof TestActivity){
                                TestActivity activity= (TestActivity) getActivity();
                                activity.drawerLayout.closeDrawers();
                    }
                }


            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel==LEVEL_COUNTY){
                    queryCities();
                }else if (currentLevel==LEVEL_CITY){
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    /**
     * 加载省级数据
     */
    private void queryProvinces() {
        titleText.setText("中国");
        button.setVisibility(View.GONE);
        provinceList=LitePal.findAll(Province.class);
        if (provinceList.size()>0){
            dataList.clear();
            for (Province province:provinceList){    //遍历provinceList，取出每一个元素

                dataList.add(province.getName());
               // Toast.makeText(getContext(), dataList.get(dataList.size()-1), Toast.LENGTH_SHORT).show();
            }
            areaAdapter.notifyDataSetChanged();//刷新
           recyclerView.scrollToPosition(0);//滚动到顶部
            currentLevel=LEVEL_PROVINCE;
        }else {
            String address="http://guolin.tech/api/china";
            queryFromSever(address,"province");//从服务器获取省级信息
        }
    }




    /**
     * 根据传入的地址和类型从服务器上查询省市县的数据
     * @param address
     * @param type
     */
    private void queryFromSever(String address,final  String type) {
        showProgressDialog();//进度对话框
        HttpUtil.sendOKHttpRequest(address, new Callback() {
            @Override//加载失败
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialod();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    String responseText=response.body().string();
                    boolean result=false;
                    if("province".equals(type)){
                        result=Utility.handleProvinceResponse(responseText);//调用handleProvinceResponse();方法解析数据
                    }else if ("city".equals(type)){
                        result=Utility.handleCityResponse(responseText,selectedProvince.getId());
                    }else if ("country".equals(type)){
                        result=Utility.handleCountyResponse(responseText,selectedCity.getId());
                    }
                    if (result){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeProgressDialod();
                                if ("province".equals(type)){
                                    queryProvinces();
                                }else if ("city".equals(type)){
                                    queryCities();
                                }else if ("country".equals(type)){
                                    queryCounties();
                                }
                            }
                        });
                    }

            }
        });
    }

    /**
     * 关闭进度条对话框
     */
    private void closeProgressDialod() {
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog==null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载……");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 加载县级数据
     */
    private void queryCounties() {
        titleText.setText(selectedCity.getName());
        button.setVisibility(View.VISIBLE);
        countyList=LitePal.where("cityid=?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size()>0){
            dataList.clear();
            for (County county:countyList){
                dataList.add(county.getName());
            }
            areaAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(0);
            currentLevel=LEVEL_COUNTY;
        }else {
            int provinceCode=selectedProvince.getCode();
            int cityCode=selectedCity.getCode();

            String address="http://guolin.tech/api/china/"+provinceCode+"/"+cityCode;
            Toast.makeText(getContext(),address, Toast.LENGTH_SHORT).show();
            queryFromSever(address,"country");
        }
    }

    /**
     * 加载选中省的所有市数据
     */
    private void queryCities() {
        titleText.setText(selectedProvince.getName());
        button.setVisibility(View.VISIBLE);
        cityList=LitePal.where("provinceid=?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size()>0){
            dataList.clear();
            for (City city:cityList){
                dataList.add(city.getName());
            }
            areaAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(0);
            currentLevel=LEVEL_CITY;
        }else{
            int provinceCode=selectedProvince.getCode();
            String address="http://guolin.tech/api/china/"+provinceCode;
            queryFromSever(address,"city");
        }
    }

}
