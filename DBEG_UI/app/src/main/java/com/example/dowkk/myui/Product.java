package com.example.dowkk.myui;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Product {

    private String type;
    private String itemId;
    private String itemNm;
    private String productImage;
    private Drawable mainImage;
    private String totalPrice;
    private String deliveryFee;
    private String deliveryCompany;
    private String deliveryDue;
    private String reviewCnt;
    private String likeCnt;
    private String itemGrade;
    private String brand;
    private String brandGrade;
    private List<String> detailImgs;
    private List<Drawable> intDetailImgs;
    private String[] detailImgAlt;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemNm() {
        return itemNm;
    }

    public void setItemNm(String itemNm) {
        this.itemNm = itemNm;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getItemGrade() {
        return itemGrade;
    }

    public void setItemGrade(String itemGrade) {
        this.itemGrade = itemGrade;
    }

    public String getReviewCnt() {
        return reviewCnt;
    }

    public void setReviewCnt(String reviewCnt) {
        this.reviewCnt = reviewCnt;
    }

    public Drawable getMainImage() { return mainImage; }

    public void setMainImage(Drawable mainImage) { this.mainImage = mainImage; }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) { this.deliveryCompany = deliveryCompany;  }

    public String getDeliveryDue() {
        return deliveryDue;
    }

    public void setDeliveryDue(String deliveryDue) {
        this.deliveryDue = deliveryDue;
    }

    public String getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(String likeCnt) {
        this.likeCnt = likeCnt;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandGrade() {
        return brandGrade;
    }

    public void setBrandGrade(String brandGrade) { this.brandGrade = brandGrade; }

    public List<String> getDetailImgs() {
        return detailImgs;
    }

    public void setDetailImgs(List<String> detailImg) {
        this.detailImgs = detailImg;
    }

    public String[] getDetailImgAlt() {
        return detailImgAlt;
    }

    public void setDetailImgAlt(String[] detailImgAlt) {
        this.detailImgAlt = detailImgAlt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Drawable> getIntDetailImgs() {
        return intDetailImgs;
    }

    public void setIntDetailImgs(List<Drawable> intDetailImgs) {
        this.intDetailImgs = intDetailImgs;
    }
}
