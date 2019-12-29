package com.example.dowkk.myui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class ProductImageAdapter extends PagerAdapter {
    private ArrayList<String> mDetailImgs;
    private LayoutInflater mInflater;
    private Context mContext;

    int resId=0;

    public ProductImageAdapter (Context context, LayoutInflater inflater, ArrayList<String> detailImgs) {
        mContext = context;
        mInflater = inflater;
        mDetailImgs = detailImgs;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View localView = null;
        View view = mInflater.inflate(R.layout.fragment_popup_page, null);
        ImageView popupImg = (ImageView)view.findViewById(R.id.popup_img);
        int idDetailImg = mContext.getResources()
                .getIdentifier(mDetailImgs.get(position), "drawable", mContext.getPackageName());
        Drawable drawable = mContext.getResources().getDrawable(idDetailImg);

        int idAltDetailImgs = mContext.getResources().getIdentifier(mDetailImgs.get(position), "string", mContext.getPackageName());

        String alt;
        if(idAltDetailImgs!=0) {
            alt = mContext.getResources().getString(idAltDetailImgs);
        } else {
            alt = "상품설명이미지입니다";
        }
        popupImg.setContentDescription(alt);
        popupImg.setImageDrawable(drawable);
        container.addView(view);
        return view;
    }

    /*
      public void onBindViewHolder(@NonNull final RecyclerImageViewAdapter.ViewHolder holder, final int position) {

        int idDrawableDetailImgs = mContext.getResources().getIdentifier(mDetailImgs.get(position), "drawable", mContext.getPackageName());

        // if you are default app
        // int idAltDetailImgs = 0;

        // if you are alt app
        int idAltDetailImgs = mContext.getResources().getIdentifier(mDetailImgs.get(position), "string", mContext.getPackageName());

        Drawable drawable = mContext.getResources().getDrawable(idDrawableDetailImgs);
        String alt;
        if(idAltDetailImgs!=0) {
            alt = mContext.getResources().getString(idAltDetailImgs);
        } else {
            alt = "상품설명이미지입니다";
        }
        holder.detail_img.setImageDrawable(drawable);
        holder.detail_img.setContentDescription(alt);
    }
    */

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "frag"+position;
    }

    @Override
    public int getCount() {
        return mDetailImgs.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }
}
