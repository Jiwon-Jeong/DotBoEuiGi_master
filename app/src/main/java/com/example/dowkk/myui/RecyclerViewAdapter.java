package com.example.dowkk.myui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<Product> mProducts = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<Product> products) {
        this.mProducts = products;
        this.mContext = context;
        //LogWrapper.v(mContext.getClass().getSimpleName(), "init List View");
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final String title = mProducts.get(position).getItemNm();
        final String content = mProducts.get(position).getTotalPrice();
        holder.product_img.setImageDrawable(mProducts.get(position).getMainImage());
        holder.product_productName.setText(mProducts.get(position).getItemNm());
        holder.product_price.setText(mProducts.get(position).getTotalPrice()+"원");
        holder.product_brand.setText(mProducts.get(position).getBrand());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InfoActivity.class);
                intent.putExtra("productCode", mProducts.get(position).getItemId());
                LogWrapper.v(mContext.getClass().getSimpleName(), "Click: " + mProducts.get(position).getItemNm());
                mContext.startActivity(intent);
            }
        });

        holder.container.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    LogWrapper.v(mContext.getClass().getSimpleName(),"Current_Focus: 목록_"+position + "_번째_상품");
                    holder.container.setFocusable(true);
                    holder.container.setFocusableInTouchMode(true);
                }
            }
        });

        holder.btn_ads.setVisibility(View.GONE);
        holder.btn_heart.setVisibility(View.GONE);

        int id = Integer.parseInt(mProducts.get(position).getItemId());
        if(id%10 > 2) {
            holder.txt_ads.setVisibility(View.GONE);
        }

        //parent layout에 accessibility focus indicator 받아와보기
        AccessibilityNodeInfo accessNodeInfo =  holder.parentLayout.createAccessibilityNodeInfo();
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView product_img;
        TextView product_productName,product_price, txt_ads, product_brand;
        CardView parentLayout;
        Button btn_ads;
        ConstraintLayout container;
        ImageButton btn_heart;


        public ViewHolder(View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.ad_cv1);
            product_img = itemView.findViewById(R.id.product_img);
            product_productName = itemView.findViewById(R.id.txt_product_name);
            product_price = itemView.findViewById(R.id.txt_product_price);
            product_brand = itemView.findViewById(R.id.txt_product_brand);
            btn_ads = itemView.findViewById(R.id.btn_ads);
            btn_heart = itemView.findViewById(R.id.btn_heart);
            container = itemView.findViewById(R.id.container);
            txt_ads = itemView.findViewById(R.id.txt_ads);
        }
    }

}
