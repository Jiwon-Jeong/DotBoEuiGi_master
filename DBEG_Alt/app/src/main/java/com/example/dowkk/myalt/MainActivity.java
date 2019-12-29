package com.example.dowkk.myalt;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;    //툴바에 현재 상태 나타내기 위해 만든 변수
    private ImageButton btnSearch;
    private Button btnSearchBar;

    private TabLayout mTabLayout;
    private ViewPager mViewPager; //큰 뷰페이저 만들기 위한 변수
    private PagerAdapter mPagerAdapter;
    private Object currentFocus = null;
    private String kwConst;
    private static final String TAG = "MainActivity";
    String testNum=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogWrapper.v(TAG, "init_activity");

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

            if(kwConst.equals("공기청정기")) testNum="3";
            else testNum="2";

            LogWrapper.v(TAG, "===============실험시작=================");
            LogWrapper.v(TAG, "실험자명: " + participant);
            LogWrapper.v(TAG, "앱이름: " + appName);
            LogWrapper.v(TAG, "실험순서: Part"+testNum);
            LogWrapper.v(TAG, "키워드: " + kwConst);

        }

        toolbar = findViewById(R.id.toolbar);   //툴바에 앱 이름 띄우기
        toolbar.setTitle("돋보의기");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setHomeActionContentDescription(R.string.icon_menu);


        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText("홈"));
        mTabLayout.addTab(mTabLayout.newTab().setText("베스트"));
        mTabLayout.addTab(mTabLayout.newTab().setText("T멤버십"));
        mTabLayout.addTab(mTabLayout.newTab().setText("쇼킹딜"));
        mTabLayout.addTab(mTabLayout.newTab().setText("미세먼지"));
        mTabLayout.addTab(mTabLayout.newTab().setText("MY추천"));
        mTabLayout.addTab(mTabLayout.newTab().setText("백화점"));
        mTabLayout.addTab(mTabLayout.newTab().setText("푸드"));
        mTabLayout.addTab(mTabLayout.newTab().setText("홈쇼핑"));
        mTabLayout.addTab(mTabLayout.newTab().setText("NOW배송"));
        mTabLayout.addTab(mTabLayout.newTab().setText("해외직구"));
        mTabLayout.addTab(mTabLayout.newTab().setText("여행"));
        mTabLayout.addTab(mTabLayout.newTab().setText("브랜드"));
        mTabLayout.addTab(mTabLayout.newTab().setText("로드#"));
        mTabLayout.addTab(mTabLayout.newTab().setText("아울렛"));
        mTabLayout.addTab(mTabLayout.newTab().setText("배달"));
        mTabLayout.addTab(mTabLayout.newTab().setText("기획전"));
        mTabLayout.addTab(mTabLayout.newTab().setText("이벤트"));

        mViewPager = (ViewPager) findViewById(R.id.pager);

        mPagerAdapter = new PagerAdapter
                (getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnSearch = (ImageButton) findViewById(R.id.main_search_btn);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogWrapper.v(TAG, "키워드검색활성화");
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        btnSearchBar = (Button) findViewById(R.id.btn_search_bar);
        btnSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogWrapper.v(TAG, "Click: 키워드_검색창");
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("kw_const", kwConst);
                intent.putExtra("test_num", testNum);
                startActivity(intent);
            }
        });
        logAcessibilityFocus();
    }

    private void logAcessibilityFocus() {
        btnSearch.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    btnSearch.setFocusable(true);
                    btnSearch.setFocusableInTouchMode(true);
                    if(currentFocus != btnSearch) {
                        LogWrapper.v(TAG, "Current_Focus: " + "상단바버튼");
                        currentFocus = btnSearch;
                    }
                }
            }
        });

        btnSearchBar.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    btnSearchBar.setFocusable(true);
                    btnSearchBar.setFocusableInTouchMode(true);
                    if(currentFocus != btnSearchBar) {
                        LogWrapper.v(TAG, "Current_Focus: " + "상단바버튼");
                        currentFocus = btnSearchBar;
                    }
                }
            }
        });

    }

    public void blockBtnClick(View v) {
        LogWrapper.v(TAG,"Click: 활성화_되지_않은_버튼: "+ this.getResources().getResourceEntryName(v.getId()));
        Toast.makeText(this, "활성화 되지 않은 버튼입니다.", Toast.LENGTH_SHORT).show();
    }

}
