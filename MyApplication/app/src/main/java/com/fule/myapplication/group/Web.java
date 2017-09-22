package com.fule.myapplication.group;

import android.webkit.WebView;

import com.fule.myapplication.R;
import com.fule.myapplication.common.superActivity.ECSuperActivity;

/**
 * 作者:Created by 简玉锋 on 2016/11/25 12:25
 * 邮箱: jianyufeng@38.hn
 */

public class Web extends ECSuperActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.web;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        WebView wb = (WebView) findViewById(R.id.webid);
        wb.getSettings().setJavaScriptEnabled(true);

        wb.loadUrl("https://www.38.hn/interface.php?act=goods_info&goods_id=48&salt=+38fule90763729");
        wb.loadUrl("https://www.baidu.com");
    }

    @Override
    protected void asynData() {

    }

    @Override
    protected void logicControlView() {

    }
}
