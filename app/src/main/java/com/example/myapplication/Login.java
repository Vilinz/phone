package com.example.myapplication;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private TextView p1, p2, p3, p4, title;
    private ViewPager vp;
    private page1 fragment1;
    private page2 fragment2;
    private page3 fragment3;
    private page4 fragment4;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    String[] titles = new String[]{"通讯录", "收藏夹", "发现", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除工具栏
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        init();

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        vp.setOffscreenPageLimit(4);
        vp.setAdapter(mFragmentAdapter);
        vp.setCurrentItem(0);
        p1.setTextColor(Color.parseColor("#66CDAA"));

        //ViewPager监听
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_p1:
                vp.setCurrentItem(0, true);
                break;
            case R.id.item_p2:
                vp.setCurrentItem(1, true);
                break;
            case R.id.item_p3:
                vp.setCurrentItem(2, true);
                break;
            case R.id.item_p4:
                vp.setCurrentItem(3, true);
                break;
        }
    }

    public void init() {
        p1 = findViewById(R.id.item_p1);
        p2 = findViewById(R.id.item_p2);
        p3 = findViewById(R.id.item_p3);
        p4 = findViewById(R.id.item_p4);
        title = findViewById(R.id.textView4);

        p1.setOnClickListener((View.OnClickListener) this);
        p2.setOnClickListener((View.OnClickListener) this);
        p3.setOnClickListener((View.OnClickListener) this);
        p4.setOnClickListener((View.OnClickListener) this);

        vp = findViewById(R.id.vp);

        fragment1 = new page1();
        fragment2 = new page2();
        fragment3 = new page3();
        fragment4 = new page4();

        mFragmentList.add(fragment1);
        mFragmentList.add(fragment2);
        mFragmentList.add(fragment3);
        mFragmentList.add(fragment4);
    }

    private void changeTextColor(int position) {
        if (position == 0) {
            p1.setTextColor(Color.parseColor("#66CDAA"));
            p2.setTextColor(Color.parseColor("#000000"));
            p3.setTextColor(Color.parseColor("#000000"));
            p4.setTextColor(Color.parseColor("#000000"));
        } else if (position == 1) {
            p2.setTextColor(Color.parseColor("#66CDAA"));
            p1.setTextColor(Color.parseColor("#000000"));
            p3.setTextColor(Color.parseColor("#000000"));
            p4.setTextColor(Color.parseColor("#000000"));
        } else if (position == 2) {
            p3.setTextColor(Color.parseColor("#66CDAA"));
            p1.setTextColor(Color.parseColor("#000000"));
            p2.setTextColor(Color.parseColor("#000000"));
            p4.setTextColor(Color.parseColor("#000000"));
        } else if (position == 3) {
            p4.setTextColor(Color.parseColor("#66CDAA"));
            p1.setTextColor(Color.parseColor("#000000"));
            p2.setTextColor(Color.parseColor("#000000"));
            p3.setTextColor(Color.parseColor("#000000"));
        }
    }
}
