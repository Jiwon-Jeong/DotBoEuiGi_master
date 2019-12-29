package com.example.dowkk.myui;

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
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<Product> mProducts = new ArrayList<>();
    private EditText keywordEdt;
    private ImageButton searchBtn;
    private RecyclerViewAdapter adapter;
    private InputMethodManager imm;
    private String keyword;
    private Object currentFocus = null;
    ProductSearchService service = new ProductSearchService(this);
    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        keywordEdt = (EditText)findViewById(R.id.keyword_edt);
        searchBtn = (ImageButton)findViewById(R.id.main_search_btn);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        Intent intent = new Intent(this.getIntent());
        keyword = intent.getStringExtra("keyword");
        keywordEdt.setText(keyword);
        new SearchAsyncTask().execute(keyword);
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                keyword = keywordEdt.getText().toString();
                //LogWrapper.v(TAG, "키워드검색:" + keyword);
                new SearchAsyncTask().execute(keyword);
                imm.hideSoftInputFromWindow(keywordEdt.getWindowToken(), 0);
            }
        });

        keywordEdt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        keywordEdt.setInputType(InputType.TYPE_CLASS_TEXT);
        AccessibilityManager am = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
        boolean isAccessibilityEnabled = am.isEnabled();
        boolean isExploreByTouchEnabled = am.isTouchExplorationEnabled();
        if(isAccessibilityEnabled && isExploreByTouchEnabled) {
            keywordEdt.setFocusable(false);
            keywordEdt.setFocusableInTouchMode(false);
        }
        keywordEdt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        keywordEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        keyword = keywordEdt.getText().toString();
                       // LogWrapper.v(TAG, "btn_click", "키워드검색:" + keyword);
                        new SearchAsyncTask().execute(keyword);
                        imm.hideSoftInputFromWindow(keywordEdt.getWindowToken(), 0);;
                        break;
                    default:
                        // 기본 엔터키 동작
                        return false;
                }
                return true;
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
                       // LogWrapper.v(TAG, "current_focus", "검색버튼");
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
                       // LogWrapper.v(TAG, "current_focus", "검색버튼");
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
        LogWrapper.v(TAG, "btn_click(비활성화)", this.getResources().getResourceEntryName(v.getId()));
        Toast.makeText(this, "활성화 되지 않은 버튼입니다.", Toast.LENGTH_SHORT).show();
    }
}
