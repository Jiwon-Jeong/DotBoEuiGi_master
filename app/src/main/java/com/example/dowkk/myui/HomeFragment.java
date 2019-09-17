package com.example.dowkk.myui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        //constructor
    }

    private ViewFlipper flipper;
    private ExpandableListView expListView;
    private ImageView img_ad1, img_ad2, img_ad3;

    @Nullable

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        img_ad1 = (ImageView)rootView.findViewById(R.id.img_ad1);
        img_ad2 = (ImageView)rootView.findViewById(R.id.img_ad2);
        img_ad3 = (ImageView)rootView.findViewById(R.id.img_ad3);

        flipper = (ViewFlipper) rootView.findViewById(R.id.flipper);
        flipper.setFlipInterval(3000);
        flipper.startFlipping();
        flipper.requestFocusFromTouch();

        ArrayList<LankGroup> DataList = new ArrayList<LankGroup>();
        expListView = (ExpandableListView) rootView.findViewById(R.id.list_lank);
        LankGroup temp = new LankGroup("실시간 쇼핑 검색어");
        temp.child.add("1. 32GK850F");
        temp.child.add("2. 스프리스바운스스니커즈");
        temp.child.add("3. 15ZD990-VX50K");
        temp.child.add("4. 뿌링클");
        temp.child.add("5. AS181DAW");
        DataList.add(temp);

        ExpandAdapter adapter = new ExpandAdapter(getContext(), R.layout.lank_group_row, R.layout.lank_child_row, DataList);
        expListView.setAdapter(adapter);

        //GridLayout 부분 수정중

        return rootView;
    }


}

