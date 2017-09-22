package com.fule.myapplication.common.activity.until;

import android.os.Build;

/**
 *
 * Created by Jorstin on 2015/6/23.
 * 当前运行时版本与指定版本的比对
 */
public class SDKVersionUtils {
    //运行时的版本小于指定的版本
    public static boolean isSmallerVersion(int version) {
        return (Build.VERSION.SDK_INT < version);
    }
    //是否是大于或等于的版本
    public static boolean isGreaterorEqual(int version) {
        return (Build.VERSION.SDK_INT >= version);
    }
    //是否是小于或等于的版本
    public static boolean isSmallerorEqual(int version) {
        return (Build.VERSION.SDK_INT <= version);
    }
}
