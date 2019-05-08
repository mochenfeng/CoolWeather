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

public class CountyActivity extends AppCompatActivity {

    private List<String> data = new ArrayList<>();
    private int[] cod = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private TextView textView;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county);
        Intent intent = getIntent();
        final int cid = intent.getIntExtra("cid",0);
        Log.i("我们接收到了id",""+cid);


        this.textView = findViewById(R.id.textview);
        this.listview = (ListView) findViewById(R.id.listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        listview.setAdapter(adapter);
        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("点击了哪一个",""+position+":"+cod[position]+":"+data.get(position));
                Intent intent = new Intent(CountyActivity.this,CountyActivity.class);
                intent.putExtra("cid",CountyActivity.this.cod[position]);
                intent.putExtra("pid",cid);
                startActivity(intent);
            }
        });


        int cids = intent.getIntExtra("cid",0);
        int pid = intent.getIntExtra("pid",0);

        String url="http://guolin.tech/api/china/"+pid+"/"+cids;
        HttpUtil.sendOkHttpRequest(url, new Callback(){
            @Override
            public void  onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
//                textView.setText(responseText);
                String[] result = parseJSONJSONObjec(responseText);
//                CountyActivity.this.data = result;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        textView.setText(responseText);
                        adapter.notifyDataSetChanged();
                    }
                });
            }



            @Override
            public void onFailure(Call call, IOException e){

            }

        });

    }
    private String[] parseJSONJSONObjec(String responseText) {
        JSONArray jsonArray = null;
        this.data.clear();
        try {
            jsonArray = new JSONArray(responseText);
            String[] result = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);
                this.data.add(jsonObject.getString("name"));
                this.cod[i] = jsonObject.getInt("id");
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
