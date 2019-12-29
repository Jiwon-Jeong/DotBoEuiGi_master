package com.example.dowkk.myfocus;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    public BottomSheetDialog(){ }
    public static BottomSheetDialog getInstance(){return new BottomSheetDialog();}

    private ArrayList<Product> mProducts = new ArrayList<>();
    private Spinner spnOption;
    private TextView txtTotalCount;
    private TextView txtTotalPrice;
    private TextView txtTotalPriceValue;
    private Button btnCart;
    private Button btnPurchase;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyOption> myOptions;
    private String list[];
    private String priceStr;
    private static final String TAG = "BottomSheetDialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sheet_purchase, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_option);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        spnOption = (Spinner) view.findViewById(R.id.spn_option);

        final ArrayList<String> optionData = new ArrayList<>();

        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        spnOption.setAdapter(spinnerAdapter);

        spnOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String)parent.getItemAtPosition(position);
                myOptions = new ArrayList<>();
                mAdapter = new RecylcerOptionViewAdapter(myOptions, priceStr);
                recyclerView.setAdapter(mAdapter);
                myOptions.add(new MyOption(str));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtTotalCount=(TextView)view.findViewById(R.id.txt_total_count);
        txtTotalPrice=(TextView)view.findViewById(R.id.txt_total_price);
        txtTotalPriceValue=(TextView)view.findViewById(R.id.txt_total_price_value);
        txtTotalPriceValue.setText(priceStr+"원");
        btnCart = (Button)view.findViewById(R.id.btn_cart);
        btnPurchase = (Button)view.findViewById(R.id.btn_purchase);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "장바구니에 담겼습니다", Toast.LENGTH_SHORT).show();
            }
        });

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "구매하기를 선택하셨습니다. 테스트를 종료합니다.", Toast.LENGTH_SHORT).show();
                LogWrapper.v(TAG, "버튼 Click : " + "구매하기 버튼 클릭");
                LogWrapper.v(TAG, "===============테크스 종료====================");
            }
        });

        return view;

    }

    public void setList(String[] list) {
        Log.e("hihi", list[0]);
        this.list = list;
    }

    public void setPriceStr(String priceStr1) {
        this.priceStr = priceStr1;
    }
}


