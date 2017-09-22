package com.fule.myapplication.mian_ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.fule.myapplication.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/4.
 */
public class LauncherActivity extends FragmentActivity {
    /*主界面的滑动控件*/
    private ViewPager vp;
    private ArrayList<Fragment> datas;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
         vp = (ViewPager) findViewById(R.id.activity_launcher_vierPager);
        datas = new ArrayList<>();
//        datas.add(new ContactFragment(getApplicationContext()));
//        datas.add(new ContactFragment(getApplicationContext()));
//        new ViewPagerAdapter(getSupportFragmentManager())
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
