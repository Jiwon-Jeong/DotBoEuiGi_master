package com.example.dowkk.myui;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;    //툴바에 현재 상태 나타내기 위해 만든 변수
    private ImageButton btnSearch;
    private EditText keywordEdt;
    private ImageButton btnVoiceSearch;
    private static final String TAG = "MainActivity";
    private Object currentFocus = null;
    private String kwConst=null;

    ProductSearchService service = new ProductSearchService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogWrapper.v(TAG, "init_activity");
        currentFocus = getCurrentFocus();

        if (Build.VERSION.SDK_INT >= 23) {
            // 퍼미션 체크
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        Intent intent = getIntent();
        if(intent.getExtras()!=null) {
            String participant = intent.getExtras().getString("participant");
            kwConst = intent.getExtras().getString("keyword");
            String appName = intent.getExtras().getString("appNm");

            LogWrapper.v(TAG, "===============실험시작=================");
            LogWrapper.v(TAG, "실험자명: " + participant);
            LogWrapper.v(TAG, "앱이름: " + appName);
            LogWrapper.v(TAG, "실험순서: Part2");
            LogWrapper.v(TAG, "키워드: " + kwConst);
        }

        toolbar = findViewById(R.id.toolbar);   //툴바에 앱 이름 띄우기
        toolbar.setTitle("돋보의기");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setHomeActionContentDescription(R.string.icon_menu);

        btnVoiceSearch = (ImageButton) findViewById(R.id.btn_mic);
        btnVoiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogWrapper.v(TAG, "Click: 음성검색버튼");
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

                startActivityForResult(i, 1000);
            }
        });

        btnSearch = (ImageButton) findViewById(R.id.btn_main_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = keywordEdt.getText().toString();
                if(kwConst!=null) keyword = kwConst;
                LogWrapper.v(TAG, "Click: (" + keyword+")검색");
                Toast.makeText(getApplicationContext(), keyword + " 검색결과 화면입니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("keyword", keyword);
                startActivity(intent);
            }
        });

        keywordEdt = (EditText) findViewById(R.id.keyword_edt);
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
                        String keyword = keywordEdt.getText().toString();
                        if(kwConst!=null) keyword=kwConst;
                        LogWrapper.v(TAG,  "Click: (" + keyword+")검색");
                        LogWrapper.v(TAG, "#Task1_시작");
                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        intent.putExtra("keyword", keyword);
                        startActivity(intent);
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
                        LogWrapper.v(TAG, "Current_Focus: 키워드_검색창");
                        currentFocus = keywordEdt;
                    }
                }
            }
        });

        btnVoiceSearch.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    btnVoiceSearch.setFocusable(true);
                    btnVoiceSearch.setFocusableInTouchMode(true);
                    if(currentFocus != btnVoiceSearch) {
                        LogWrapper.v(TAG, "Current_Focus: 음성검색버튼");
                        currentFocus = btnVoiceSearch;
                    }
                }
            }
        });

        btnSearch.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    btnSearch.setFocusable(true);
                    btnSearch.setFocusableInTouchMode(true);
                    if(currentFocus != btnSearch) {
                        LogWrapper.v(TAG, "Current_Focus: 키워드검색버튼");
                        currentFocus = btnSearch;
                    }
                }
            }
        });

    }

    public void onResults(Bundle results) {
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        for(int i = 0; i < matches.size() ; i++){
            Log.e("GoogleActivity", "onResults text : " + matches.get(i));
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String keyword;
        switch (requestCode) {
            case 1000 : {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    for(int i = 0; i < text.size() ; i++){
                        Log.e("GoogleActivity", "onActivityResult text : " + text.get(i));

                    }

                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    keyword = text.get(0);
                    if(kwConst!=null) keyword=kwConst;
                    intent.putExtra("keyword", keyword);
                    startActivity(intent);
                }
                break;
            }
        }
    }

    public void blockBtnClick(View v) {
        LogWrapper.v(TAG,"Click: 활성화_되지_않은_버튼: "+ this.getResources().getResourceEntryName(v.getId()));
        Toast.makeText(this, "활성화 되지 않은 버튼입니다.", Toast.LENGTH_SHORT).show();
    }
}
