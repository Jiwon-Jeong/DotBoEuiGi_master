package com.example.dowkk.myfocus;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class RecylcerOptionViewAdapter extends RecyclerView.Adapter<RecylcerOptionViewAdapter.ViewHolder> {
    private ArrayList<MyOption> mmOptions = new ArrayList<>();
    static private String priceStr;
    Intent intent = new Intent();

    public Intent getIntent() {
        return intent;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtOption;
        public ImageButton btnCancel;
        public TextView txtProductCount;
        public Button btnIncrease;
        public TextView txtProductPrice;

        public ViewHolder(View view) {
            super(view);
            txtOption = (TextView) view.findViewById(R.id.txt_option);
            btnCancel = (ImageButton) view.findViewById(R.id.btn_cancel);
            txtProductCount = (TextView) view.findViewById(R.id.txt_product_count);
            btnIncrease = (Button) view.findViewById(R.id.btn_increase);
            txtProductPrice = (TextView) view.findViewById(R.id.txt_product_price);
            txtProductPrice.setText(priceStr+"원");
        }
    }

    public RecylcerOptionViewAdapter(ArrayList<MyOption> options, String priceStr1) {
        mmOptions = options;
        priceStr = priceStr1;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_option, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txtOption.setText(mmOptions.get(position).option);
    }

    @Override
    public int getItemCount() {
        return mmOptions.size();
    }

}
