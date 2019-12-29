package com.example.ziwon.databasetest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class sqlHelper extends AppCompatActivity {

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_CODE = "productCode";
    private static final String TAG_URL = "productDetailUrl";
    private static final String TAG_INFO = "productInfo";

    JSONArray products = null;
    ArrayList<HashMap<String, String>> productList;
    ListView list;
    String productCode="1000"; //임시로

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_sql_helper);
         list = (ListView) findViewById(R.id.listView);
         productList = new ArrayList<HashMap<String, String>>();
         getData(productCode); //클릭해서 얻어지는 상품코드 파라미터 들어가도록만 하면됨!!!
      }

      protected void showList() {
          try {
              JSONObject jsonObj = new JSONObject(myJSON);
              products = jsonObj.getJSONArray(TAG_RESULTS);

             for (int i = 0; i < products.length(); i++) {
                 JSONObject c = products.getJSONObject(i);
                 String productCode = c.getString(TAG_CODE);
                 String productDetailUrl = c.getString(TAG_URL);
                 String productInfo = c.getString(TAG_INFO);

                 HashMap<String, String> products = new HashMap<String, String>();

                 products.put(TAG_CODE, productCode);
                 products.put(TAG_URL, productDetailUrl);
                 products.put(TAG_INFO, productInfo);

                 productList.add(products);
             }

             ListAdapter adapter = new SimpleAdapter(
                     sqlHelper.this, productList, R.layout.detail,
                     new String[]{TAG_CODE, TAG_URL, TAG_INFO},
                     new int[]{R.id.code, R.id.url, R.id.info}
              );

             list.setAdapter(adapter);
          } catch (JSONException e) {
              e.printStackTrace();
          }
      }

      public void getData(String productCode) {
          class GetDataJSON extends AsyncTask<String, Void, String> {
              @Override
              protected String doInBackground(String... params) {

                  String product_code = params[0];
                  String uri = "http://172.30.15.42/product.php?productCode=" + product_code;

                  BufferedReader bufferedReader = null;
                  try {
                      URL url = new URL(uri);
                      HttpURLConnection con = (HttpURLConnection) url.openConnection();
                      StringBuilder sb = new StringBuilder();

                      bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                      String json;
                      while ((json = bufferedReader.readLine()) != null) {
                          sb.append(json + "\n");
                      }

                      return sb.toString().trim();

                  } catch (Exception e) {
                      return null;
                  }


              }

              @Override
              protected void onPostExecute(String result) {
                  myJSON = result;
                  showList();
              }
          }

      GetDataJSON g = new GetDataJSON();
          g.execute(productCode);
      }

}
