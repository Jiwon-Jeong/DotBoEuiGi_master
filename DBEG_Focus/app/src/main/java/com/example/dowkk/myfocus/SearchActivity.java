package com.example.dowkk.myfocus;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private Object currentFocus = null;
    private ArrayList<Product> mProducts = new ArrayList<>();
    private EditText keywordEdt;
    private ImageButton searchBtn;
    private RecyclerViewAdapter adapter;
    private InputMethodManager imm;
    private String kwConst = null;

    ProductSearchService service = new ProductSearchService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        kwConst = intent.getStringExtra("kw_const");
        LogWrapper.v(TAG, "init Act : "+TAG);

        keywordEdt = (EditText)findViewById(R.id.main_keyword_edt);
        keywordEdt.setInputType(InputType.TYPE_CLASS_TEXT);
        keywordEdt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        keywordEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        String keyword = keywordEdt.getText().toString();
                        if(kwConst!=null)    keyword = kwConst;
                        LogWrapper.v(TAG, "Click: (" + keyword+")검색");
                        Toast.makeText(getApplicationContext(), keyword + " 검색결과 화면입니다", Toast.LENGTH_SHORT).show();
                        new SearchAsyncTask().execute(keyword);
                        imm.hideSoftInputFromWindow(keywordEdt.getWindowToken(), 0);
                        break;
                    default:
                        // 기본 엔터키 동작
                        return false;
                }
                return true;
            }
        });

        searchBtn = (ImageButton)findViewById(R.id.main_search_btn);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String keyword = keywordEdt.getText().toString();
                if(kwConst!=null)    keyword = kwConst;
                LogWrapper.v(TAG, "Click: (" + keyword+")검색");
                LogWrapper.v(TAG, "#Task3_시작");
                Toast.makeText(getApplicationContext(), keyword + " 검색결과 화면입니다", Toast.LENGTH_SHORT).show();                new SearchAsyncTask().execute(keyword);
                imm.hideSoftInputFromWindow(keywordEdt.getWindowToken(), 0);
            }
        });

        logAcessibilityFocus();
    }

    private void logAcessibilityFocus() {
        keywordEdt.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    keywordEdt.setFocusable(true);
                    keywordEdt.setFocusableInTouchMode(true);
                    if(currentFocus != keywordEdt) {
                        LogWrapper.v(TAG, "Current Focus : " + "키워드 검색창");
                        currentFocus = keywordEdt;
                    }
                }
            }
        });

        searchBtn.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    searchBtn.setFocusable(true);
                    searchBtn.setFocusableInTouchMode(true);
                    if(currentFocus != searchBtn) {
                        LogWrapper.v(TAG, "Current Focus : " + "키워드 검색 버튼");
                        currentFocus = searchBtn;
                    }
                }
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecyclerViewAdapter(this, mProducts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    class SearchAsyncTask extends AsyncTask<String, Void, List<Product>> {

        @Override
        protected List<Product> doInBackground(String... strings) {
            String keyword = strings[0];
            service.setKeyword(keyword);
            mProducts.clear();
            List<Product> data = service.search();
            return data;
        }

        @Override
        protected void onPostExecute(List<Product> obj) {
            super.onPostExecute(obj);
            mProducts.addAll(obj);
            initRecyclerView();
        }
    }

    public void blockBtnClick(View v) {
        LogWrapper.v(TAG,"활성화되지 않은 버튼 Click : " + this.getResources().getResourceEntryName(v.getId()));
        Toast.makeText(this, "활성화 되지 않은 버튼입니다.", Toast.LENGTH_SHORT).show();
    }

}
