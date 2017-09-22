package com.fule.myapplication.mian_ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> datas;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> datas) {
        super(fm);
        this.datas =datas;
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
