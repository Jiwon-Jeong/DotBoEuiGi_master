package com.example.dowkk.apply11streetapi;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity2 extends AppCompatActivity
{
    TextView productName, productPrice,productSeller,reviewCount,rating,delivery,benefit,discount,
    bonus,point;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //xml과 연결
        setContentView(R.layout.detail_product);
        setTitle("상세정보 보기");

        productName = (TextView)findViewById(R.id.product_productName);
        productPrice = (TextView)findViewById(R.id.product_price);
        productSeller = (TextView)findViewById(R.id.product_seller);
        reviewCount = (TextView)findViewById(R.id.product_reviewCount);
        rating = (TextView)findViewById(R.id.product_rating);
        delivery = (TextView)findViewById(R.id.product_delivery);
        benefit = (TextView)findViewById(R.id.product_benefit);
        discount = (TextView)findViewById(R.id.product_discount);
        bonus = (TextView)findViewById(R.id.product_bonus);
        point = (TextView)findViewById(R.id.product_point);

       imageView = (ImageView)findViewById(R.id.product_img);

        Intent intent = getIntent();
        Product product = (Product)intent.getSerializableExtra("productInfo");
        //Toast.makeText(getApplicationContext(),"Button is clicked", Toast.LENGTH_LONG).show();

        productName.setText(product.getProductName());
        productPrice.setText("가격 : "+product.getProductPrice());
        productSeller.setText("판매자 : "+product.getSeller());
        reviewCount.setText("리뷰 수 : "+product.getReviewCount());
        rating.setText("좋아요 : "+product.getRating());
        delivery.setText("배송비 : "+product.getDelivery());
        benefit.setText("고객혜택정보 : "+product.getBenefit());
        discount.setText("할인금액정보 : "+product.getDiscount());
        bonus.setText("보너스포인트정보 : "+product.getBonus());
        point.setText("포인트 적립 정보 : "+product.getPoint());
        //imageView.setImageResource(product.getProductImage());
    }


        //   public void onClick(View v)
  //  {
//       Intent intent = getIntent();
//        Restaurant res = intent.getParcelableExtra("restinfo");
//        switch (v.getId())
//        {
//            case R.id.btnback:
//                finish();
//                break;
//            case R.id.imageView2://전화
//                Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:/"+res.getTel()));
//                startActivity(intent2);
//                break;
//
//            case R.id.imageView3:
//                Intent intent3 = new Intent(Intent.ACTION_VIEW,Uri.parse(res.getHomepage()));
//                startActivity(intent3);
//                break;
//        }
//    }
}