package com.example.dowkk.myui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;

public class SizeActivity extends AppCompatActivity {

    private String table[][];
    private JSONArray contents[];
    private int colCnt, rowCnt;
    private JSONArray columnsArry;
    private Toolbar toolbar;    //툴바에 현재 상태 나타내기 위해 만든 변수
    private static final String TAG = "SizeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);
//        LogWrapper.v(TAG, "init Act : " + TAG);
        LogWrapper.v(TAG, "Current_Focus: 사이즈영역");
        LogWrapper.v(TAG, "Click: 사이즈_자세히_보기_버튼");
        LogWrapper.v(TAG, "#Task2_종료");
        LogWrapper.v(TAG, "#Task3_시작");

        toolbar = findViewById(R.id.toolbar);   //툴바에 앱 이름 띄우기
        toolbar.setTitle(R.string.title_activity_size);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView lview = (ListView) findViewById(R.id.listview);
        getData();
        setTable();


        SizelistAdapter adapter = new SizelistAdapter(this, table);
        lview.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    private void getData() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("product_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("sizes");

            Intent intent = new Intent(this.getIntent());
            String itemId = intent.getStringExtra("itemId");

            //Toast.makeText(this,itemId, Toast.LENGTH_LONG).show();
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jsonObject = m_jArry.getJSONObject(i);
                if(jsonObject.getString("itemId").equals(itemId)) {
                    columnsArry = jsonObject.getJSONArray("attribute");
                    contents = new JSONArray[columnsArry.length()];
                    for(int j=0; j < columnsArry.length(); j++) {
                        String attribute = (String) columnsArry.get(j);
                        if(columnsArry.getString(j).equals(attribute))
                            contents[j] = jsonObject.getJSONArray(columnsArry.getString(j));
                    }
                    colCnt = columnsArry.length();
                    rowCnt = contents[0].length();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setTable() {
        table = new String[rowCnt+1][colCnt];

        for(int i=0; i<=colCnt; i++) {
            try {
                table[0][i] = (String) columnsArry.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i=1; i<=rowCnt; i++) {
            for(int j=0; j<colCnt; j++) {
                try {
                    table[i][j] = "blank";
                    table[i][j] = (String) contents[j].get(i-1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
