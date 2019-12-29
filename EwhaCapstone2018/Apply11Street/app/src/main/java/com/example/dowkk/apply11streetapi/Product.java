package com.example.dowkk.apply11streetapi;

import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

public class Product implements Serializable{
    private String productCode;
    private String productName;
    private String productImage; //?
    private String productDetailUrl; //상품상세정보 URL
    private String productPrice;
    private String seller;
    private String rating; //만족도 5점 척도로 계산
    private String salePrice; //할인 모음가 정보
    private String delivery; //배송비 정보
    private String reviewCount; //상품 평가 개수 정보
    private String buySatisfy; //구매만족도 정보
    private String benefit; //고객혜택 정보
    private String discount; //할인금액 정보
    private String bonus; //보너스 포인트 정보
    private String point; //포인트 (적립)정보
    private String inFree; //무이자 개월수 정보

    @Override
    public String toString() {
        return "Product [productCode=" + productCode + ", productName=" + productName + ", productImage=" + productImage
                + ", productDetailUrl=" + productDetailUrl + ", productPrice=" + productPrice + ", seller=" + seller
                + ", rating=" + rating + ", salePrice=" + salePrice + ", delivery=" + delivery + ", reviewCount="
                + reviewCount + ", buySatisfy=" + buySatisfy + ", benefit=" + benefit + ", discount="+discount
                + ", bonus="+ bonus+", point="+point+", inFree="+inFree+"]";
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }



    public String getProductDetailUrl() {
        return productDetailUrl;
    }

    public void setProductDetailUrl(String productDetailUrl) { this.productDetailUrl = productDetailUrl; }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getBuySatisfy() {
        return buySatisfy;
    }

    public void setBuySatisfy(String buySatisfy) {
        this.buySatisfy = buySatisfy;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getDiscount(){return discount;}

    public void setDiscount(String discount){this.discount=discount;}

    public String getBonus(){return bonus;}

    public void setBonus(String bonus){this.bonus = bonus;}

    public String getPoint(){return point;}

    public void setPoint(String point){this.point=point;}
}
