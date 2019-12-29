package com.example.dowkk.myui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, PopupMenu.OnMenuItemClickListener {

    private Toolbar toolbar;    //툴바에 현재 상태 나타내기 위해 만든 변수
    private ImageView infoImg;
    private TextView txtProductType;
    private TextView txtProductName;
    private TextView txtProductPrice;
    private TextView txtProductCntReview;
    private TextView txtDeliveryFee;
    private TextView txtDeliveryDue;
    private TextView txtBrandName;
    private TextView txtItemGrade;
    private TextView txtSizeDetail;
    private TextView txtSizeOptionDetail;
    private Button btnMoreImg;
    private ImageButton btnClose;
    private ImageButton btnBack;
    private ImageButton btnNext;
    private Button btnDibs;
    private Button btnSizeOption;
    private ConstraintLayout basicInfoArea;
    private ConstraintLayout sizeArea;
    private ConstraintLayout deliveryArea;
    private ConstraintLayout dibsShareArea;
    private ConstraintLayout brandArea;
    private ViewPager viewPager;
    private ArrayList<String> detailImgs = new ArrayList<>();
    private String itemId;
    private Dialog myDialog;
    private int selectedViewPager = 0;
    private String table[][];
    private JSONArray contents[];
    private int colCnt, rowCnt;
    private JSONArray columnsArry;
    private Product p;
    private String[] sizes;
    private static final String TAG = "InfoActivity";
    private Object currentFocus = null;

    ProductSearchService service = new ProductSearchService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        LogWrapper.v(TAG, "#Task1_종료");
        LogWrapper.v(TAG, "#Task2_시작");

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
        txtSizeDetail = findViewById(R.id.txt_size_detail);
        btnMoreImg = findViewById(R.id.more_img_btn);
        basicInfoArea = findViewById(R.id.basic_info_area);
        deliveryArea = findViewById(R.id.delivery_area);
        dibsShareArea = findViewById(R.id.dibs_share_area);
        brandArea = findViewById(R.id.brand_area);
        sizeArea = findViewById(R.id.size_area);
        btnDibs = findViewById(R.id.btn_dibs);

        myDialog = new Dialog(this);

        toolbar.setTitle("돋보의기");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String json = null;
        p = new Product();
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
        infoImg.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);

        txtProductType.setText(p.getType());
        txtProductName.setText(p.getItemNm());
        txtProductPrice.setText(p.getTotalPrice()+"원");
        txtProductCntReview.setText("리뷰 "+p.getReviewCnt()+" >");
        txtItemGrade.setText("별점 "+p.getItemGrade()+" >");
        txtDeliveryFee.setText("배송비 "+p.getDeliveryFee());
        txtDeliveryDue.setText("오늘 주문하면 "+p.getDeliveryDue()+"에 받으실 수 있습니다.");
        txtBrandName.setText(p.getBrand());
        new DetailImgAsyncTask().execute(p.getItemId());

        btnMoreImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogWrapper.v(TAG, "Click: 이미지_더보기_버튼" );
                showPrdImagePopup();
            }
        });
        final String id = p.getItemId();

        getData();
        setTable();

        txtSizeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, SizeActivity.class);
                intent.putExtra("itemId", id);
                intent.putExtra("table", table);
                startActivity(intent);
            }
        });

        if(rowCnt!=0) {
            btnSizeOption = findViewById(R.id.btn_size_option);
            sizeArea.setVisibility(View.VISIBLE);
            btnSizeOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogWrapper.v(TAG, "Click: 사이즈옵션_선택");
                    PopupMenu popup = new PopupMenu(InfoActivity.this, v);
                    popup.setOnMenuItemClickListener(InfoActivity.this);
                    setSizesOpts();
                    for(int i=0; i<rowCnt; i++) {
                        popup.getMenu().add(sizes[i]);
                    }
                    popup.show();
                }
            });
        }

        Button btnPurchase;
        btnPurchase = findViewById(R.id.btn_purchase);
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InfoActivity.this, "구매하기를 선택하셨습니다. 테스트를 종료합니다.", Toast.LENGTH_LONG).show();
                LogWrapper.v(TAG, "Click: " + "구매하기_버튼");
                LogWrapper.v(TAG, "#Task3_종료");
                LogWrapper.v(TAG, "===============실험종료====================");
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

        btnMoreImg.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    btnMoreImg.setFocusable(true);
                    btnMoreImg.setFocusableInTouchMode(true);
                    if(currentFocus != btnMoreImg) {
                        LogWrapper.v(TAG, "Current_Focus: " + "이미지_더보기_버튼");
                        currentFocus = btnMoreImg;
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
                        LogWrapper.v(TAG, "Current_Focus: " + "기본정보영역");
                        currentFocus = basicInfoArea;
                    }
                }
            }
        });

        txtDeliveryFee.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    txtDeliveryFee.setFocusable(true);
                    txtDeliveryFee.setFocusableInTouchMode(true);
                    if(currentFocus != txtDeliveryFee) {
                        LogWrapper.v(TAG, "Current_Focus: " + "배송관련영역");
                        currentFocus = txtDeliveryFee;
                    }
                }
            }
        });

        btnDibs.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    btnDibs.setFocusable(true);
                    btnDibs.setFocusableInTouchMode(true);
                    if(currentFocus != btnDibs) {
                        LogWrapper.v(TAG, "Current_Focus: " + "찜/공유하기영역");
                        currentFocus = btnDibs;
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
                        LogWrapper.v(TAG, "Current_Focus: " + "브랜드영역");
                        currentFocus = brandArea;
                    }
                }
            }
        });

//        sizeArea.setAccessibilityDelegate(new View.AccessibilityDelegate() {
//            @Override
//            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
//                super.onPopulateAccessibilityEvent(host, event);
//                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
//                    sizeArea.setFocusable(true);
//                    sizeArea.setFocusableInTouchMode(true);
//                    if(currentFocus != sizeArea) {
//                        LogWrapper.v(TAG, "Current Focus : " + "사이즈 영역");
//                        currentFocus = sizeArea;
//                    }
//                }
//            }
//        });
    }


    private void setSizesOpts() {
        sizes = new String[rowCnt];
        for(int i=1; i<=rowCnt; i++) {
            sizes[i-1] = table[i][0];
        }
    }

    private void showPrdImagePopup() {
        LogWrapper.v(TAG, "Current_Focus: 이미지_더보기_팝업창");
        myDialog.setContentView(R.layout.detail_img_popup);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ProductImageAdapter adapter = new ProductImageAdapter(this.getApplicationContext(), this.getLayoutInflater(), detailImgs);
        viewPager = (ViewPager) myDialog.findViewById(R.id.pupup_view_pager);
        viewPager.setAdapter(adapter);
        myDialog.show();
        btnClose = myDialog.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogWrapper.v(TAG, "Click: " + "팝업창_닫기_버튼_in_Dialog_Popup");
                myDialog.dismiss();
            }
        });

        viewPager.addOnPageChangeListener(this);
        btnBack = myDialog.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogWrapper.v(TAG, "Click: " + "이전_이미지보기_버튼_in_Dialog_Popup");

                if(selectedViewPager > 0) {
                    viewPager.setCurrentItem(selectedViewPager-1, true);
                }
            }
        });

        btnNext = myDialog.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogWrapper.v(TAG, "Click: " + "다음_이미지보기_버튼_in_Dialog_Popup");

                if(selectedViewPager < viewPager.getAdapter().getCount()) {
                    viewPager.setCurrentItem(selectedViewPager + 1, true);
                }
            }
        });
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        selectedViewPager = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if(menuItem.getTitle()!=null) {
            btnSizeOption.setText("선택 :" + menuItem.getTitle());
            Toast.makeText(this, menuItem.getTitle() + "을(를) 선택하셨습니다.", Toast.LENGTH_LONG).show();
            txtSizeDetail = findViewById(R.id.txt_size_option_detail);
            String sizeDatail = "옵션 상세 정보\n";
            int position;
            for(position=0; position<rowCnt; position++) {
                if(menuItem.getTitle().equals(table[position+1][0]))
                    break;
            }
            for(int i=0; i<colCnt; i++) {
                sizeDatail += table[0][i];
                sizeDatail += " : ";
                sizeDatail += table[position+1][i];
                sizeDatail += "   ";
            }
            txtSizeDetail.setVisibility(View.VISIBLE);
            txtSizeDetail.setText(sizeDatail);
            return true;
        }
        return false;
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
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        myDialog.dismiss();
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

//            Log.e("hihi", String.valueOf(m_jArry.length()));

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jsonObject = m_jArry.getJSONObject(i);
                if(jsonObject.getString("itemId").equals(p.getItemId())) {
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

        for(int i=0; i<colCnt; i++) {
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

    public void blockBtnClick(View v) {
        LogWrapper.v(TAG,"활성화되지_않은_버튼_Click: "+ this.getResources().getResourceEntryName(v.getId()));
        Toast.makeText(this, "활성화 되지 않은 버튼입니다.", Toast.LENGTH_SHORT).show();
    }

}
