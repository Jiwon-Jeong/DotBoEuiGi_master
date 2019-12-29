package com.example.dowkk.myui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

class RecyclerImageViewAdapter extends RecyclerView.Adapter<RecyclerImageViewAdapter.ViewHolder> {
    private ArrayList<String> mDetailImgs = new ArrayList<>();
    private Context mContext;

    public RecyclerImageViewAdapter(Context context, ArrayList<String> detailImgs) {
        this.mDetailImgs = detailImgs;
        this.mContext = context;
    }
    @NonNull
    @Override
    public RecyclerImageViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detail_img, parent, false);
        RecyclerImageViewAdapter.ViewHolder holder = new RecyclerImageViewAdapter.ViewHolder(view);

        return holder;
    }


    @Override
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

    @Override
    public int getItemCount() {
        return mDetailImgs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView detail_img;
        LinearLayout parentLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.ad_cv1);
            detail_img = itemView.findViewById(R.id.detail_img);

        }
    }

}
