package com.fule.myapplication.common.activity;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fule.myapplication.R;
import com.fule.myapplication.common.LogUtil;
import com.fule.myapplication.common.activity.until.AnimatorUtils;
import com.fule.myapplication.common.activity.until.MethodInvoke;
import com.fule.myapplication.common.activity.until.SDKVersionUtils;

/**
 * Created by Jorstin on 2015/6/19.
 */
public class ECFragmentActivity extends AppCompatActivity
        implements SwipeActivityManager.SwipeListener, SwipeBackLayout.OnSwipeGestureDelegate {
    private static final String TAG = "ECFragmentActivity";
    //
    public SwipeBackLayout mSwipeBackLayout;

    public boolean mOnDragging;
    //动画集合
    private WindowAnimation mAnimation = new WindowAnimation();

    //启动Activity 带有动画
    private void onStartActivityAction(Intent intent) {
        /***
         *给activity跳转加上动画效果
         */
        if (intent == null) {
            super.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            return;
        }
        String className = null;
        ComponentName component = intent.getComponent();
        if (component != null) {
            className = component.getClassName();
            if (!(className.startsWith(component.getPackageName()))) {
                className = component.getPackageName() + component.getClassName();
            }
        } else {
            return;
        }
        if ((0x2 & MethodInvoke.getTransitionValue(className)) != 0) {
            super.overridePendingTransition(mAnimation.openEnter, mAnimation.openExit);
            return;
        }
        if ((0x4 & MethodInvoke.getTransitionValue(className)) != 0) {
            MethodInvoke.startTransitionNotChange(this);
            return;
        }
        MethodInvoke.startTransitionPopin(this);
    }

    //Activity创建时调用  主要是对侧滑处理
    public boolean onActivityCreate() {
        if (isSupperSwipe()) {
            //获取根部局
            ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
            //加载自定义的滑动布局
            mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                    R.layout.swipeback_layout, viewGroup, false);
            //初始化设置
            mSwipeBackLayout.init();
            //设置Activity的窗体为透明
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置根视图的背景
//            getWindow().getDecorView().setBackgroundDrawable(null);
            //获取内容视图
            ViewGroup childAtView = (ViewGroup) viewGroup.getChildAt(0);
            childAtView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.android_bg_color));
            viewGroup.removeView(childAtView);
            mSwipeBackLayout.addView(childAtView);
            mSwipeBackLayout.setContentView(childAtView);
            viewGroup.addView(this.mSwipeBackLayout);
            //设置手势拖拽  的监听
            mSwipeBackLayout.setSwipeGestureDelegate(this);
            return true;
        }

        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setEnableGesture(false);
        }
        if (!isFinishing()) {
            SwipeActivityManager.pushCallback(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SwipeActivityManager.popCallback(this);
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setEnableGesture(true);
            mSwipeBackLayout.mScrolling = false;
        }
    }

    //是否支持侧边滑动的控制
    private boolean isSupperSwipe() {
        if (/*SDKVersionUtils.isSmallerVersion(19) &&*/ SupportSwipeModeUtils.isEnable()) {
            if (isEnableSwipe()) {
                return true;
            }
        }
        return false;
    }

    //使能侧边滑动
    protected boolean isEnableSwipe() {
        return true;
    }

    @Override
    public void onScrollParent(float scrollPercent) {
        LogUtil.v(TAG, "on swipe " + scrollPercent + " ,duration " + Long.valueOf(240L));
        View decorView = getWindow().getDecorView();
        if ((decorView instanceof ViewGroup) && (((ViewGroup) decorView).getChildCount() > 0)) {
            decorView = ((ViewGroup) decorView).getChildAt(0);
        }
        if (Float.compare(1.0F, scrollPercent) <= 0) {
            AnimatorUtils.startViewAnimation(decorView, 0.0F);
            return;
        }
        AnimatorUtils.startViewAnimation(decorView, -1.0F * decorView.getWidth() / 4 * (1.0F - scrollPercent));
    }

    @Override
    public void notifySettle(boolean open, int speed) {
        LogUtil.v(TAG, "on settle " + open + ", speed " + speed);
        View decorView = getWindow().getDecorView();
        if ((decorView instanceof ViewGroup) && (((ViewGroup) decorView).getChildCount() > 0)) {
            decorView = ((ViewGroup) decorView).getChildAt(0);
        }
        long duration = 120L;
        if (speed <= 0) {
            duration = 240L;
        }
        if (open) {
            AnimatorUtils.updateViewAnimation(decorView, duration, 0.0F, null);
            return;
        }
        AnimatorUtils.updateViewAnimation(decorView, duration, -1 * decorView.getWidth() / 4, null);
    }

    @Override
    public boolean isEnableGesture() {
        return false;
    }

    @Override
    public void onSwipeBack() {
        if (!(isFinishing())) {
            finish();
        }
        mOnDragging = false;
    }

    @Override
    public void onDragging() {
        mOnDragging = true;
    }

    @Override
    public void onCancel() {
        mOnDragging = false;
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (isSupperSwipe() && event.getKeyCode() == KeyEvent.KEYCODE_BACK && mSwipeBackLayout.isSwipeBacking()) {
            LogUtil.d(TAG, "IS SwipeBack ING, ignore KeyBack Event");
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    //Activity 关闭
    @Override
    public void finish() {
        super.finish();
        if (isEnableSwipe()) {
            SwipeActivityManager.notifySwipe(1.0F);
        }
        //Activity 关闭的动画
        super.overridePendingTransition(mAnimation.closeEnter, mAnimation.closeExit);
//        if((0x2 & MethodInvoke.getAnnotationValue(super.getClass())) == 0) {
//            return ;
//        }
//        if ((0x4 & MethodInvoke.getAnnotationValue(super.getClass())) != 0) {
//            MethodInvoke.startTransitionNotChange(this);
//            return ;
//        }
//        MethodInvoke.startTransitionPopout(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return;
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void startActivities(Intent[] intents) {
        super.startActivities(intents);
        onStartActivityAction(null);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void startActivities(Intent[] intents, Bundle bundle) {
        super.startActivities(intents, bundle);
        onStartActivityAction(null);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        onStartActivityAction(intent);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startActivity(Intent intent, Bundle bundle) {
        super.startActivity(intent, bundle);
        onStartActivityAction(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        onStartActivityAction(intent);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        onStartActivityAction(intent);
    }


    public static class WindowAnimation {
        public static int activityOpenEnterAnimation;
        public static int activityOpenExitAnimation;
        public static int activityCloseEnterAnimation;
        public static int activityCloseExitAnimation;

        public int openEnter = activityOpenEnterAnimation;
        public int openExit = activityOpenExitAnimation;
        public int closeEnter = activityCloseEnterAnimation;
        public int closeExit = activityCloseExitAnimation;

        static {
            if (!(SDKVersionUtils.isSmallerVersion(19) && SupportSwipeModeUtils.isEnable())) {
                activityOpenEnterAnimation = R.anim.slide_right_in;
                activityOpenExitAnimation = R.anim.slide_left_out;
                activityCloseEnterAnimation = R.anim.slide_left_in;
                activityCloseExitAnimation = R.anim.slide_right_out;
            } else {
                activityOpenEnterAnimation = R.anim.pop_in;
                activityOpenExitAnimation = R.anim.anim_not_change;
                activityCloseEnterAnimation = R.anim.anim_not_change;
                activityCloseExitAnimation = R.anim.pop_out;
            }
        }
    }
}
