package com.fule.myapplication;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.fule.myapplication.common.LogUtil;
import com.fule.myapplication.common.activity.until.CCPAppManager;

import io.rong.imlib.RongIMClient;

/**
 * Created by Administrator on 2016/8/3.
 */
public class App extends Application {
    private static App instance;

    /**
     * 单例，返回一个实例
     * @return
     */
    public static App getInstance() {
        if (instance == null) {
            LogUtil.w("[com.fule.myapplication.App] instance is null.");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CCPAppManager.setContext(instance);
        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIMClient 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
            RongIMClient.init(this);
        }
    }

    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
