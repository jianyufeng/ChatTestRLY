/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package com.fule.myapplication.chatting.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fule.myapplication.R;


/**
 * holder的基类，抽取的公共
 */
public class BaseHolder {

    /**
     * type   聊天的类型
     */
    protected int type;

    /**
     * 上传消息 对应的基础控件
     */
    protected ProgressBar progressBar;    //进度
    protected ImageView chattingAvatar;   //头像
    protected TextView chattingTime;      //时间
    protected TextView chattingUser;      //人名
//    protected CheckBox checkBox;          //选择框  暂时未使用
    /**
     * 文件类的消息
     * The file Im message transmission state, success or failure or sending
     *
     * @see SQLiteManager#IMESSENGER_STATUS_SUCCEED
     * @see SQLiteManager#IMESSENGER_STATUS_SENT
     * @see SQLiteManager#IMESSENGER_STATUS_SENDING
     * @see SQLiteManager#IMESSENGER_STATUS_FAIL
     */
    protected ImageView uploadState;  //发送失败，显示的重发控件
    protected View baseView;           //不同聊天类型的视图。构造方法传过来的
//    protected View clickAreaView;      // 图片类型的点击区域  暂未使用
//    protected View chattingMaskView;   //  标记 暂未使用

    /**
     * 构造方法
     *
     * @param type 聊天的类型
     */
    public BaseHolder(int type) {
        this.type = type;
    }

    /**  构造方法 暂未使用
     * @param baseView
     */
//    public BaseHolder(View baseView) {
//        super();
//        this.baseView = baseView;
//    }

    /**
     * 初始化baseHolder的控件
     *
     * @param baseView 不同布局的视图
     */
    public void initBaseHolder(View baseView) {
        this.baseView = baseView;
        chattingTime = (TextView) baseView.findViewById(R.id.chatting_time_tv);  //时间
        chattingAvatar = (ImageView) baseView.findViewById(R.id.chatting_avatar_iv); //头像
//		clickAreaView = baseView.findViewById(R.id.chatting_click_area);            // 图片类型   的点击区域  ？？？？？
        uploadState = (ImageView) baseView.findViewById(R.id.chatting_state_iv);   //  发送类型  的重新发送控件
    }

    /**
     * @param edit
     */
//    public void setEditMode(boolean edit) {
//        int visibility = edit ? View.VISIBLE : View.GONE;
//        if (checkBox != null && checkBox.getVisibility() != visibility) {
//            checkBox.setVisibility(visibility);
//        }
//
//        if (chattingMaskView != null && chattingMaskView.getVisibility() != visibility) {
//            chattingMaskView.setVisibility(visibility);
//        }
//
//    }

    /**
     * @return the baseView  不同类型的视图
     */
    public View getBaseView() {
        return baseView;
    }

    /**
     * @return the type     不同类型
     */
    public int getType() {
        return type;
    }

    /**
     * @return the progressBar   进度
     */
    public ProgressBar getUploadProgressBar() {
        return progressBar;
    }

    /**
     * @return the chattingAvatar  头像
     */
    public ImageView getChattingAvatar() {
        return chattingAvatar;
    }

    /**
     * @return the chattingTime   时间
     */
    public TextView getChattingTime() {
        return chattingTime;
    }

    /**
     * @param chattingTime the chattingTime to set    重新的赋予控件 主要是因为 时间 控件是代码动态生成的，所以在此处可以重新替换
     */
    public void setChattingTime(TextView chattingTime) {
        this.chattingTime = chattingTime;
    }

    /**
     * @return the chattingUser   聊天人物的姓名 ，主要是收到他人信息时可能要显示名字
     */
    public TextView getChattingUser() {
        return chattingUser;
    }

    /**
     * @return the checkBox      主要是想实现多选消息  ，暂时未使用
     */
//    public CheckBox getCheckBox() {
//        return checkBox;
//    }

    /**
     * @return the uploadState   消息重发的控件
     */
    public ImageView getUploadState() {
        return uploadState;
    }

    /**
     * @return the clickAreaView     暂未使用
     */
//    public View getClickAreaView() {
//        return clickAreaView;
//    }

    /**
     * @return the chattingMaskView  暂未使用
     */
//    public View getChattingMaskView() {
//        return chattingMaskView;
//    }
}
