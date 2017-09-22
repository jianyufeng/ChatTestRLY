
package com.fule.myapplication.chatting.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fule.myapplication.R;
import com.fule.myapplication.chatting.adapter.chat_type.ChattingRowType;


/**
 * 文本类的holder 对象
 */
public class TextViewHolder extends BaseHolder {

    public View chattingContent;
    /**
     * 显示文本内容的控件
     */
//	private EmojiconTextView descTextView;
    private TextView descTextView;

    /**
     * 构造方法
     *
     * @param type 类型
     */
    public TextViewHolder(int type) {
        super(type);

    }

    /**
     * 初始化holder 中的控件
     *
     * @param baseView 根视图
     * @param receive  是否是收消息
     * @return  返回holder对象
     */
    public BaseHolder initBaseHolder(View baseView, boolean receive) {
        super.initBaseHolder(baseView);     // 初始化基类holder 主要是时间等控件

        chattingTime = (TextView) baseView.findViewById(R.id.chatting_time_tv);     //时间 可以不用
        chattingUser = (TextView) baseView.findViewById(R.id.chatting_user_tv);     //姓名
        descTextView = (TextView) baseView.findViewById(R.id.chatting_content_itv);  //文本类消息内容控件
//		descTextView = (EmojiconTextView) baseView.findViewById(R.id.chatting_content_itv);
//		checkBox = (CheckBox) baseView.findViewById(R.id.chatting_checkbox);       //??
//		chattingMaskView = baseView.findViewById(R.id.chatting_maskview);          //??
        chattingContent = baseView.findViewById(R.id.chatting_content_area);        //聊天布局中的根视图
        if (receive) {       // 收消息
            type = ChattingRowType.TEXT_ROW_RECEIVED.ordinal();       //设置收消息类型
            return this;
        }
        //发消息
        uploadState = (ImageView) baseView.findViewById(R.id.chatting_state_iv);   //重发的控件
        progressBar = (ProgressBar) baseView.findViewById(R.id.uploading_pb);      //发送的进度
        type = ChattingRowType.TEXT_ROW_TRANSMIT.ordinal();          //设置发消息类型
        return this;
    }

    /**
     * 获取文本类消息内容控件
     *
     * @return 内容控件
     */
    public TextView getDescTextView() {  //EmojiconTextView
        if (descTextView == null) {
            descTextView = (TextView) getBaseView().findViewById(R.id.chatting_content_itv);
        }
        return descTextView;
    }

    /**
     * 获取重发控件
     *
     * @return 重发控件
     */
    public ImageView getChattingState() {
        if (uploadState == null) {
            uploadState = (ImageView) getBaseView().findViewById(R.id.chatting_state_iv);
        }
        return uploadState;
    }

    /**
     * 获取发送进度控件
     *
     * @return 发送进度控件
     */
    public ProgressBar getUploadProgressBar() {
        if (progressBar == null) {
            progressBar = (ProgressBar) getBaseView().findViewById(R.id.uploading_pb);
        }
        return progressBar;
    }

}
