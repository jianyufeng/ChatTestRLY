package com.fule.myapplication.common.activity.until;

import android.content.Context;
import android.content.SharedPreferences;

import com.fule.myapplication.common.LogUtil;

/**
 * 作者:Created by 简玉锋 on 2016/11/21 14:41
 * 邮箱: jianyufeng@38.hn
 */

public class CCPAppManager {

    /**
     * 包名
     */
    public static String pkgName = "com.fule.myapplication";
    /**
     * Android 应用上下文
     */
    private static Context mContext = null;
    /**
     * 设置上下文对象
     *
     * @param context
     */
    public static void setContext(Context context) {
        mContext = context;
        pkgName = context.getPackageName();
        LogUtil.d(LogUtil.getLogUtilsTag(CCPAppManager.class),
                "setup application context for package: " + pkgName);
    }
    /**
     * 返回上下文对象
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }


    public static String getPackageName() {
        return pkgName;
    }
    /**
     * 返回SharePreference配置文件名称
     *
     * @return
     */
    public static String getSharePreferenceName() {
        return pkgName + "_preferences";
    }
    //返回SharePreference对象
    public static SharedPreferences getSharePreference() {
        if (mContext != null) {
            return mContext.getSharedPreferences(getSharePreferenceName(),Context.MODE_PRIVATE);
        }
        return null;
    }

}
