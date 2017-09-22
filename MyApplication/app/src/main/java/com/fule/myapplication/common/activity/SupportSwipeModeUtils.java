package com.fule.myapplication.common.activity;

import android.content.SharedPreferences;
import android.os.Build;

import com.fule.myapplication.common.LogUtil;
import com.fule.myapplication.common.activity.until.CCPAppManager;
import com.fule.myapplication.common.activity.until.DemoUtils;


/**
 *
 * Created by Jorstin on 2015/6/23.
 *
 */
public class SupportSwipeModeUtils {

    private static final String TAG = "SupportSwipeModeUtils";

    private static int mode = 0;

    public static void switchSwipebackMode(boolean enable) {
        SharedPreferences sharePreference = CCPAppManager.getSharePreference();
        boolean supportSwipe = sharePreference.getBoolean("settings_support_swipe", true);
        if(supportSwipe != enable) {
            sharePreference.edit().putBoolean("settings_support_swipe", enable).commit();
        }
        LogUtil.d(TAG , "switchSwipebackMode, from " + supportSwipe + " to " + enable);
    }

    //是否支持滑动  可是配置 settings_support_swipe 为false则就不能侧滑
    public static boolean isEnable() {
        if(DemoUtils.nullAsNil(Build.VERSION.INCREMENTAL).toLowerCase().contains("flyme")
                || DemoUtils.nullAsNil(Build.DISPLAY).toLowerCase().contains("flyme")) {
            return false;
        }

        if(mode == 0) {
            if(!CCPAppManager.getSharePreference().getBoolean("settings_support_swipe", true)) {
                mode = 2;
            } else {
                mode = 1;
            }
        }
        return mode == 1;
    }
 }
