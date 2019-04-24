package com.example.admin.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CountyActivity extends AppCompatActivity {

    private String[] data = {"","","","","","","","","","","","","","","","","","","","",};
    private int[] cods = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county);
    }
    private String[] parseJSONJSONObjec(String responseText) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(responseText);
            String[] result = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);
                this.data[i] = jsonObject.getString("name");
                this.cods[i] = jsonObject.getInt("id");
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
