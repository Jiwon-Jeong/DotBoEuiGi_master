package com.example.dowkk.myalt;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<Product> mProducts = new ArrayList<>();
    private Context mContext;
    private String testNum;

    public RecyclerViewAdapter(Context context, ArrayList<Product> products, String testNum) {
        this.mProducts = products;
        this.mContext = context;
        this.testNum = testNum;
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
        holder.product_delivery.setText("배송비 "+ mProducts.get(position).getDeliveryFee());
        holder.product_satisfy.setText("별점 "+ mProducts.get(position).getItemGrade()+"/5");
        holder.product_cnt_review.setText(" | 리뷰"+mProducts.get(position).getReviewCnt());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InfoActivity.class);
                intent.putExtra("productCode", mProducts.get(position).getItemId());
                intent.putExtra("test_num", testNum);
                LogWrapper.v(mContext.getClass().getSimpleName(), "Click: " + mProducts.get(position).getItemNm());
                mContext.startActivity(intent);
            }
        });

        holder.container.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED) {
                    LogWrapper.v(mContext.getClass().getSimpleName(), "Current_Focus: 목록_" + position + "번째_상품");
                    holder.container.setFocusable(true);
                    holder.container.setFocusableInTouchMode(true);
                }
            }
        });

        int id = Integer.parseInt(mProducts.get(position).getItemId());
        if(id%10 > 2) {
            holder.btn_ads.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView product_img;
        TextView product_productName,product_price,
                product_delivery, product_satisfy, product_cnt_review;
        CardView parentLayout;
        ConstraintLayout container;
        Button btn_ads;


        public ViewHolder(View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            product_img = itemView.findViewById(R.id.product_img);
            product_productName = itemView.findViewById(R.id.txt_product_name);
            product_price = itemView.findViewById(R.id.txt_product_price);
            product_delivery = itemView.findViewById(R.id.delivery_area);
            product_satisfy = itemView.findViewById(R.id.txt_product_satisfy);
            product_cnt_review = itemView.findViewById(R.id.txt_product_cnt_review);
            container = itemView.findViewById(R.id.container);
            btn_ads = itemView.findViewById(R.id.btn_ads);
        }
    }

}
