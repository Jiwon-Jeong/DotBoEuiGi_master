package com.example.dowkk.myfocus;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class InfoActivity extends AppCompatActivity {

    private Toolbar toolbar;    //툴바에 현재 상태 나타내기 위해 만든 변수
    private ImageView infoImg;
    private TextView txtProductType;
    private TextView txtProductName;
    private TextView txtProductPrice;
    private TextView txtProductCntReview;
    private TextView txtDeliveryFee;
    private TextView txtDeliveryDue;
    private TextView txtBrandName;
    private TextView txtBrandGrade;
    private TextView txtItemGrade;
    private ScrollView scrollView;
    private ArrayList<String> detailImgs = new ArrayList<>();
    private String itemId;
    private RecyclerImageViewAdapter adapter;
    private RecyclerView recyclerView;
    private Button btnPurchase;
    private TextView txtItemName;
    private ConstraintLayout basicInfoArea;
    private ConstraintLayout deliveryArea;
    private ConstraintLayout dibsShareArea;
    private ConstraintLayout brandArea;
    private ArrayList<String> options = new ArrayList<>();
    ProductSearchService service = new ProductSearchService(this);
    private static final String TAG = "InfoActivity";
    private Object currentFocus = null;
    private String kwConst;
    String testNum=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        LogWrapper.v(TAG, "#Task3_종료");
        LogWrapper.v(TAG, "===============실험종료=================");


        toolbar = findViewById(R.id.toolbar);   //툴바에 앱 이름 띄우기
        infoImg = findViewById(R.id.info_img);
        txtProductType = findViewById(R.id.txt_product_type);
        txtProductName = findViewById(R.id.txt_product_name);
        txtProductPrice = findViewById(R.id.txt_product_price);
        txtProductCntReview = findViewById(R.id.txt_product_cnt_review);
        txtItemGrade = findViewById(R.id.txt_item_grade);
        txtDeliveryFee = findViewById(R.id.txt_delivery_price);
        txtDeliveryDue = findViewById(R.id.txt_delivery_due);
        txtBrandName = findViewById(R.id.txt_brand_name);
        txtBrandGrade = findViewById(R.id.txt_brand_grade);       basicInfoArea = findViewById(R.id.basic_info_area);
        deliveryArea = findViewById(R.id.delivery_area);
        dibsShareArea = findViewById(R.id.dibs_share_area);
        brandArea = findViewById(R.id.brand_area);
        btnPurchase = findViewById(R.id.btn_purchase);
        scrollView = findViewById(R.id.scrollView);
        btnPurchase = findViewById(R.id.btn_purchase);

        toolbar.setTitle("돋보의기");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String json = null;
        final Product p = new Product();
        try {
            InputStream is = getAssets().open("product_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("products");
            for(int i=0; i<m_jArry.length(); i++) {
                JSONObject jsonObject = m_jArry.getJSONObject(i);
                itemId = jsonObject.getString("itemId");
                if(itemId.equals(getIntent().getStringExtra("productCode"))) {
                    p.setItemId(itemId);
                    p.setItemNm(jsonObject.getString("itemNm"));
                    p.setType(jsonObject.getString("type"));
                    p.setProductImage(jsonObject.getString("productImage"));
                    p.setTotalPrice(jsonObject.getString("totalPrice"));
                    p.setDeliveryFee(jsonObject.getString("deliveryFee"));
                    p.setDeliveryCompany(jsonObject.getString("deliveryCompany"));
                    p.setDeliveryDue(jsonObject.getString("deliveryDue"));
                    p.setReviewCnt(jsonObject.getString("reviewCnt"));
                    p.setLikeCnt(jsonObject.getString("likeCnt"));
                    p.setItemGrade(jsonObject.getString("itemGrade"));
                    p.setBrand(jsonObject.getString("brand"));
                    p.setBrandGrade(jsonObject.getString("brandGrade"));
                    JSONArray jsonArray = jsonObject.getJSONArray("option");
                    String[] optionStr = new String[jsonArray.length()];
                    for(int j=0; j<jsonArray.length(); j++) {
                        optionStr[j] = (String) jsonArray.get(j);
                    }
                    p.setOptions(optionStr);

                    int id_main_img = getResources()
                            .getIdentifier(jsonObject.getString("productImage"), "drawable", getPackageName());
                    Drawable drawable_main = getResources().getDrawable(id_main_img);
                    p.setMainImage(drawable_main);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        infoImg.setImageDrawable(p.getMainImage());
        infoImg.setContentDescription(p.getItemNm()+"상품이미지입니다.");
        txtProductType.setText(p.getType());
        txtProductName.setText(p.getItemNm());
        txtProductPrice.setText(p.getTotalPrice()+"원");
        txtProductCntReview.setText("리뷰 "+p.getReviewCnt()+" >");
        txtItemGrade.setText("별점 "+p.getItemGrade()+" >");
        txtDeliveryFee.setText("배송비 "+p.getDeliveryFee());
        txtDeliveryDue.setText("오늘 주문하면 "+p.getDeliveryDue()+"에 받으실 수 있습니다.");
        txtBrandName.setText(p.getBrand());
        txtBrandGrade.setText("* "+p.getBrandGrade()+"/5");

        TabHost tabHost1 = (TabHost) findViewById(R.id.tab_host);
        tabHost1.setup();

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("상품설명");
        tabHost1.addTab(ts1);

        new DetailImgAsyncTask().execute(p.getItemId());
        scrollView.smoothScrollTo(0,0);

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomSheetDialog = BottomSheetDialog.getInstance();
                bottomSheetDialog.setList(p.getOptions());
                bottomSheetDialog.setPriceStr(p.getTotalPrice());
                bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheet");
            }
        });

        logAcessibilityFocus();
    }

    private void logAcessibilityFocus() {
        infoImg.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    infoImg.setFocusable(true);
                    infoImg.setFocusableInTouchMode(true);
                    if(currentFocus != infoImg) {
                        LogWrapper.v(TAG, "Current Focus : " + "이미지");
                        currentFocus = infoImg;
                    }
                }
            }
        });

        basicInfoArea.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    basicInfoArea.setFocusable(true);
                    basicInfoArea.setFocusableInTouchMode(true);
                    if(currentFocus != basicInfoArea) {
                        LogWrapper.v(TAG, "Current Focus : " + "기본 정보 영역");
                        currentFocus = basicInfoArea;
                    }
                }
            }
        });

        deliveryArea.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    deliveryArea.setFocusable(true);
                    deliveryArea.setFocusableInTouchMode(true);
                    if(currentFocus != deliveryArea) {
                        LogWrapper.v(TAG, "Current Focus : " + "배송 관련 영역");
                        currentFocus = deliveryArea;
                    }
                }
            }
        });

        dibsShareArea.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    dibsShareArea.setFocusable(true);
                    dibsShareArea.setFocusableInTouchMode(true);
                    if(currentFocus != dibsShareArea) {
                        LogWrapper.v(TAG, "Current Focus : " + "찜/공유하기 영역");
                        currentFocus = dibsShareArea;
                    }
                }
            }
        });

        brandArea.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    brandArea.setFocusable(true);
                    brandArea.setFocusableInTouchMode(true);
                    if(currentFocus != brandArea) {
                        LogWrapper.v(TAG, "Current Focus : " + "브랜드 영역");
                        currentFocus = brandArea;
                    }
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecyclerImageViewAdapter(this, detailImgs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    class DetailImgAsyncTask extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... strings) {
            String itemId = strings[0];
            service.setItemId(itemId);
            List<String> imgData = service.detailImgs();
            return imgData;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            detailImgs.addAll(strings);
            initRecyclerView();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void blockBtnClick(View v) {
        LogWrapper.v(TAG, "btn_click(비활성화)", this.getResources().getResourceEntryName(v.getId()));
        Toast.makeText(this, "활성화 되지 않은 버튼입니다.", Toast.LENGTH_SHORT).show();
    }
}

