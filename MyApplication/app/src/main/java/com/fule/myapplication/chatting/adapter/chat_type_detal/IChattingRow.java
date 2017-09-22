/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.cloopen.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package com.fule.myapplication.chatting.adapter.chat_type_detal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.fule.myapplication.chatting.adapter.holder.BaseHolder;

import io.rong.imlib.model.Message;


/**
 * 聊天类型的基类
 */
public interface IChattingRow {

    /**
     * Get a View that displays the data at the specified position in the data set
     * 返回对应的视图
     * @param convertView
     * @return
     */
    View buildChatView(LayoutInflater inflater, View convertView);

    /**
     * 设置视图对应的数据
     * @param context
     * @param detail
     */
    void buildChattingBaseData(Context context, BaseHolder baseHolder, Message detail, int position);

    /**
     * 类型
     * @return
     */
    int getChatViewType();

}
