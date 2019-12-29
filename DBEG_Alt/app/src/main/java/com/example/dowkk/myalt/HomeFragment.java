package com.example.dowkk.myalt;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        //constructor
    }

    private ViewFlipper flipper;
    private ViewFlipper flipper_kwd;
    private ExpandableListView expListView;

    @Nullable

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        flipper = (ViewFlipper) rootView.findViewById(R.id.flipper);
        flipper.setFlipInterval(3000);
        flipper.startFlipping();

        flipper_kwd = (ViewFlipper) rootView.findViewById(R.id.flipper_keywords);
        flipper_kwd.setFlipInterval(1000);
        flipper_kwd.startFlipping();

        flipper_kwd.bringToFront();

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

        return rootView;
    }

    public void blockBtnClick(View v) {
        Toast.makeText(getContext(), "활성화 되지 않은 버튼입니다.", Toast.LENGTH_SHORT).show();
    }
}

