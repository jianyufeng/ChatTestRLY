
package com.fule.myapplication.chatting.adapter.chat_type_detal;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;

import com.fule.myapplication.R;
import com.fule.myapplication.chatting.adapter.chat_type.ChattingRowType;
import com.fule.myapplication.chatting.adapter.holder.BaseHolder;
import com.fule.myapplication.chatting.adapter.holder.VoiceRowViewHolder;
import com.fule.myapplication.chatting.ui.ChattingActivity;

import io.rong.imlib.model.Message;


/**
 * 语音消息  收的
 */
public class VoiceRxRow extends BaseChattingRow {
    /**
     * 构造方法
     *
     * @param type 聊天类型
     */
    public VoiceRxRow(int type) {
        super(type);
    }

    /**
     * 设置声音视图
     *
     * @param inflater    视图加载器
     * @param convertView 内容视图
     * @return
     */
    @Override
    public View buildChatView(LayoutInflater inflater, View convertView) {
        //创建视图
        if (convertView == null) {
            convertView = new ChattingItemContainer(inflater, R.layout.chatting_item_from_voice); //构造语音视图

            //use the view holder pattern to save of already looked up subviews
            VoiceRowViewHolder holder = new VoiceRowViewHolder(mRowType);
            convertView.setTag(holder.initBaseHolder(convertView, true));
        }
        return convertView;
    }

    /**
     * 设置声音数据
     *
     * @param context
     * @param baseHolder 向上转型的 基类holde
     * @param detail     消息体
     * @param position   位置
     */
    @Override
    public void buildChattingData(Context context, BaseHolder baseHolder,
                                  final Message detail, int position) {

        VoiceRowViewHolder holder = (VoiceRowViewHolder) baseHolder;
        if (detail != null) {
            VoiceRowViewHolder.initVoiceRow(holder, detail, position, (ChattingActivity) context, true);
            holder.voiceAnim.setVoiceFrom(true);
        }
    }


    @Override
    public int getChatViewType() {

        return ChattingRowType.VOICE_ROW_RECEIVED.ordinal();
    }

    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu,
                                          View targetView, Message detail) {
        // TODO Auto-generated method stub
        return false;
    }

}
