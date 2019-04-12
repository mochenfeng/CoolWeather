package com.example.admin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {

    private String[] data = {"北京","上海","天津","重庆","香港","澳门","台湾","黑龙江","吉林","辽宁","内蒙古","河北"};
    private TextView textView;
    private Button button;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.textView = (TextView) findViewById(R.id.text);
        this.button = (Button) findViewById(R.id.btn);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(Main2Activity.this, MainActivity.class));
            }
        });
        this.listview = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        listview.setAdapter(adapter);

//        String weatherId="CN101320102";
        String weatherUrl = "http://guolin.tech/api/china";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback(){
            @Override
            public void  onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
//                textView.setText(responseText);
                String[] result = parseJSONWithJSONObjec(responseText);
                Main2Activity.this.data = result;

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

    private String[] parseJSONWithJSONObjec(String responseText) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(responseText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] result = new String[jsonArray.length()];
        for(int i = 0; i<jsonArray.length(); i++){
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                result[i] = jsonObject.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
