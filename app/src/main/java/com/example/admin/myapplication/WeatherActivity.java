package com.example.admin.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {


    TextView weathertextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        this.weathertextview = findViewById(R.id.weathertextview);
        String weatherIds = getIntent().getStringExtra("weatherIds");
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherIds;

        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback(){
            @Override
            public void  onResponse(Call call, Response response) throws IOException{
                final String responseText = response.body().string();
//                textView.setText(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        weathertextview.setText(responseText);
                    }
                });

            }
            @Override
            public void onFailure(Call call, IOException e){

            }

        });
    }
}
