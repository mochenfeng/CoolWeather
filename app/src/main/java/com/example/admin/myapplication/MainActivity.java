package com.example.admin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


import java.io.IOException;





public class MainActivity extends AppCompatActivity {

    private  TextView textView;
    private Button button;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid",0);
        Log.i("我们接收到了id",""+pid);

        this.textView = (TextView) findViewById(R.id.abc);
        this.button = (Button) findViewById(R.id.button);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(MainActivity.this, Main2Activity.class));
            }
        });
//        this.listview = (ListView) findViewById(R.id.listview);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
//        listview.setAdapter(adapter);

        String weatherId="CN101320102";
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=40fc75aa40b44ef2923f42b345fbc2d6";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback(){
            @Override
            public void  onResponse(Call call, Response response) throws IOException{
                final String responseText = response.body().string();
//                textView.setText(responseText);
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
