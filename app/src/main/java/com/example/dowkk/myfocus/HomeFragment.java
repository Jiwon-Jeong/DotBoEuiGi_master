package com.example.dowkk.myfocus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        //constructor
    }

    private ViewFlipper flipper;
    private ExpandableListView expListView;
    private ImageButton like;
    private ConstraintLayout lastItem;
    private Object currentFocus = lastItem;
    private ConstraintLayout lankGroup;


    @Nullable

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        flipper = (ViewFlipper) rootView.findViewById(R.id.flipper);
        flipper.setFlipInterval(1000);

        ArrayList<LankGroup> DataList = new ArrayList<LankGroup>();
        expListView = (ExpandableListView) rootView.findViewById(R.id.list_lank);
        lastItem = (ConstraintLayout) rootView.findViewById(R.id.LastItem);
        like = (ImageButton) rootView.findViewById(R.id.cv_heart3);
        lankGroup = (ConstraintLayout) rootView.findViewById(R.id.lank_group_row);

        LankGroup temp = new LankGroup("실시간 쇼핑 검색어");
        temp.child.add("1. 32GK850F");
        temp.child.add("2. 스프리스바운스스니커즈");
        temp.child.add("3. 15ZD990-VX50K");
        temp.child.add("4. 뿌링클");
        temp.child.add("5. AS181DAW");
        DataList.add(temp);


        ExpandAdapter adapter = new ExpandAdapter(getContext(), R.layout.lank_group_row, R.layout.lank_child_row, DataList);
        expListView.setAdapter(adapter);


        currentFocus = lastItem;

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "상품을 좋아요했습니다.", Toast.LENGTH_SHORT).show();
                LogWrapper.v(TAG, "#Task2_종료");
            }
        });

        return rootView;
    }


}

