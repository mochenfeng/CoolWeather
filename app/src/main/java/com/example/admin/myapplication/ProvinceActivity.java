package com.example.admin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProvinceActivity extends AppCompatActivity {
    public static final String CITY = "city";
    public static final String PROVINCE = "province";
    public static final String COUNTY = "county";

    private TextView textView;
    private ListView listview;

    private String currentlevel = PROVINCE;
    private int provinceId = 0;
    private int cityId = 0;
    private List<String> areaNameList = new ArrayList<>();
    private List<Integer> areaIdList = new ArrayList<>();
    private List<String> weatherIdList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.textView = (TextView) findViewById(R.id.text);
        this.listview = (ListView) findViewById(R.id.listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, areaNameList);
        listview.setAdapter(adapter);
        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("点击了哪一个", "" + position + ":" + ProvinceActivity.this.areaIdList.get(position) + ":" + ProvinceActivity.this.areaNameList.get(position));
                if(currentlevel == PROVINCE) {
                    currentlevel = CITY;
                    provinceId = ProvinceActivity.this.areaIdList.get(position);
                } else if(currentlevel == CITY) {
                    currentlevel = COUNTY;
                    cityId = ProvinceActivity.this.areaIdList.get(position);
                } else if (currentlevel == COUNTY) {
                    Intent intent = new Intent(ProvinceActivity.this,WeatherActivity.class);
                    intent.putExtra("weatherIdList", weatherIdList.get(position));
                    startActivity(intent);

                }
                getData(adapter);
            }
        });
        getData(adapter);
    }

    private void getData(final ArrayAdapter<String> adapter) {
//        String weatherUrl = currentlevel == CITY ? "http://guolin.tech/api/china/" + provinceId : "http://guolin.tech/api/china";
        String weatherUrl = currentlevel == PROVINCE ? "http://guolin.tech/api/china/":(currentlevel == CITY?"http://guolin.tech/api/china/" + provinceId : "http://guolin.tech/api/china/"+provinceId+"/"+cityId);
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                parseJSONJSONObjec(responseText);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        textView.setText(responseText);
                    }
                });
            }


            @Override
            public void onFailure(Call call, IOException e) {

            }

        });
    }

    private void parseJSONJSONObjec(String responseText) {
        JSONArray jsonArray = null;
        this.areaNameList.clear();
        this.areaIdList.clear();
        this.weatherIdList.clear();
        try {
            jsonArray = new JSONArray(responseText);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);
                this.areaNameList.add(jsonObject.getString("name"));
                this.areaIdList.add(jsonObject.getInt("id"));
                if (currentlevel == COUNTY){
                    this.weatherIdList.add(jsonObject.getString("weather_id"));
                }
//                if (jsonObject.has("weather_id")) {
//                      this.weatherIdList.add(jsonObject.getString("weather_id"));
//                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
