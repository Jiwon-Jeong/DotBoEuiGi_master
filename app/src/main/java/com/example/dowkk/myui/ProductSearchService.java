package com.example.dowkk.myui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ProductSearchService {
    // 검색할 키워드
    String keyword;
    String itemId;
    Context mContext;
    public ProductSearchService(Context Context) {
        mContext = Context;
    }

    public List<Product> search() {
        String json = null;
        List<Product> list = new ArrayList<Product>();
        try {
            InputStream is = mContext.getAssets().open("product_data.json");
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
            JSONArray m_jArry = obj.getJSONArray("products");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jsonObject = m_jArry.getJSONObject(i);
                if(jsonObject.getString("type").equals(keyword)) {
                    Product p = new Product();
                    p.setItemId(jsonObject.getString("itemId"));
                    p.setItemNm(jsonObject.getString("itemNm"));
                    p.setTotalPrice(jsonObject.getString("totalPrice"));
                    p.setDeliveryFee(jsonObject.getString("deliveryFee"));;
                    p.setReviewCnt(jsonObject.getString("reviewCnt"));
                    p.setItemGrade(jsonObject.getString("itemGrade"));
                    p.setBrand(jsonObject.getString("brand"));

                    int id_main_img = mContext.getResources()
                            .getIdentifier(jsonObject.getString("productImage"), "drawable", mContext.getPackageName());
                    Drawable drawable_main = mContext.getResources().getDrawable(id_main_img);
                    p.setMainImage(drawable_main);

                    list.add(p);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        long seed = System.nanoTime();
        Collections.shuffle(list, new Random(seed));
        return list;
    }

    public List<String> detailImgs() {
        String json = null;
        List<String> list = new ArrayList<String>();
        try {
            InputStream is = mContext.getAssets().open("product_data.json");
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
            JSONArray m_jArry = obj.getJSONArray("products");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jsonObject = m_jArry.getJSONObject(i);
                if(jsonObject.getString("itemId").equals(itemId)) {
                    JSONArray detailImgArray = new JSONArray(jsonObject.get("detailImg").toString());

                    for (int j = 0; j < detailImgArray.length(); j++) {
                        JSONObject detailImgObject = new JSONObject(detailImgArray.get(j).toString());
                        String detailImg = detailImgObject.getString("name");
                        list.add(detailImg);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setItemId(String itemId) {  this.itemId = itemId; }
}
