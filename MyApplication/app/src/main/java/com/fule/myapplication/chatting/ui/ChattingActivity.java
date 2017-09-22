package com.fule.myapplication.chatting.ui;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.fule.myapplication.R;

/**
 * Created by Administrator on 2016/4/18.
 */
public class ChattingActivity extends FragmentActivity {
    public ChattingFragment mChattingFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chattingui_activity_container);
        mChattingFragment = new ChattingFragment();
        Bundle bundle = getIntent().getExtras();
        bundle.putBoolean(ChattingFragment.FROM_CHATTING_ACTIVITY, true);
        mChattingFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.ccp_root_view, mChattingFragment).commit();
    }
}