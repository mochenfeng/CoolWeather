package com.example.admin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private TextView textView;
    private Button button;
    private ListView listview;


    private String currentlevel = "province";
    private int pid = 0;

    private List<String> data2 = new ArrayList();
    private List<String> data = new ArrayList<>();
    private List<Integer> pids = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.textView = (TextView) findViewById(R.id.text);

        this.listview = (ListView) findViewById(R.id.listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listview.setAdapter(adapter);
        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("点击了哪一个", "" + position + ":" + ProvinceActivity.this.pids.get(position) + ":" + ProvinceActivity.this.data.get(position));
                pid = ProvinceActivity.this.pids.get(position);
                currentlevel = "city";
                getData(adapter);
/*
                Intent intent = new Intent(ProvinceActivity.this,CityActivity.class);
                intent.putExtra("pid",ProvinceActivity.this.pids[position]);

                if (currentlevel == "city"){
                    intent.putExtra("cid", cids[position]);
                }

                startActivity(intent);
  */
            }
        });


        getData(adapter);
    }

    private void getData(final ArrayAdapter<String> adapter) {
        String weatherUrl = currentlevel == "city" ? "http://guolin.tech/api/china/" + pid : "http://guolin.tech/api/china";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
//                textView.setText(responseText);
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
        this.data.clear();
        this.pids.clear();
        try {
            jsonArray = new JSONArray(responseText);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);
                this.data.add(jsonObject.getString("name"));
                this.pids.add(jsonObject.getInt("id"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
