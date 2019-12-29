package com.example.dowkk.apply11streetapi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText keywordEdt;
    private Button searchBtn;
    private List<Product> productList;

    private ListView listView;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keywordEdt = (EditText) findViewById(R.id.main_keyword_edt);
        searchBtn = (Button) findViewById(R.id.main_search_btn);
        productList = new ArrayList<Product>();
        adapter = new ProductAdapter(this, R.layout.list_product_item, productList);

        listView = (ListView) findViewById(R.id.main_listView);
        listView.setAdapter(adapter);

        /* 검색 버튼 누르면 검색 실행  */
        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //키워드 얻어와서 키워드로 파싱
                String keyword = keywordEdt.getText().toString();
                ProductSearchService service = new ProductSearchService(keyword);
                ProductSearchThread thread = new ProductSearchThread(service, handler);
                thread.start();
            }
        });

        /* 아이템 클릭시 상세정보 나타남 */
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               //명시적 인텐트 생성 및 보낼 객체 담기
               Intent intent = new Intent(MainActivity.this, MainActivity2.class);
               //첫 값은 식별태그, 뒤에는 화면에 넘길 값
               intent.putExtra("productInfo", productList.get(position));
               //액티비티 시작
             startActivity(intent);
            }
       });
    }

        @SuppressLint("HandlerLeak")
        private Handler handler = new Handler() {
            @SuppressLint("WrongConstant")
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d("test", String.valueOf(msg));

                if (msg.what == 1) {
                    //arg1이 10이면 처음 검색에 대한 결과를 갖다 준걸로
                    if (msg.arg1 == 10) {
                        String result = "";
                        List<Product> data = (List<Product>) msg.obj;
                        for (Product p : data)
                            result += p.getProductName() + "\n";
                        //Toast.makeText(MainActivity.this, result, 0).show();
                        //Toast.makeText(MainActivity.this, "dd", 0).show();
                        productList.clear();
                        productList.addAll((List<Product>) msg.obj);
                        adapter.notifyDataSetChanged();
                    }
//                arg2이 20이면 검색했던 결과에 대해 추가 아이템을 요청한걸로
                    if (msg.arg2 == 20) {

                    }
                }
            }
        };
    }

