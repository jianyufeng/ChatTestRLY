package com.fule.myapplication.group.memberDetails;

import android.content.Context;
import android.os.Bundle;

import com.fule.myapplication.R;
import com.fule.myapplication.common.superActivity.ECSuperActivity;

/**
 * 作者:Created by 简玉锋 on 2016/11/29 17:15
 * 邮箱: jianyufeng@38.hn
 * 群组成员详情 界面
 */

public class GroupMemberDetailsActivity extends ECSuperActivity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.group_member_details;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {


    }

    @Override
    protected void asynData() {

    }

    @Override
    protected void logicControlView() {

    }
}
