package com.example.admin.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


import java.io.IOException;





public class MainActivity extends AppCompatActivity {

    private  TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textView = (TextView) findViewById(R.id.abc);

        String weatherId="CN101320102";
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=40fc75aa40b44ef2923f42b345fbc2d6";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback(){
            @Override
            public void  onResponse(Call call, Response response) throws IOException{
                final String responseText = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(responseText);
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e){

            }

        });
    }
}
