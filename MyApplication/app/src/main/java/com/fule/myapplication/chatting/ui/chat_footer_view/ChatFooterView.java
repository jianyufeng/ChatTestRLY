package com.fule.myapplication.chatting.ui.chat_footer_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/8/13.
 */
public class ChatFooterView extends LinearLayout {


    public ChatFooterView(Context context) {
        this(context, null);
    }

    public ChatFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     *  构造方法
     * @param context 环境
     * @param attrs
     * @param defStyleAttr
     */
    public ChatFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        //初始化视图
        initChatFooter(context);
    }

    private void initChatFooter(Context context) {
//        inflate(context, R.layout.)
    }
}
