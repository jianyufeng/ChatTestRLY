
package com.fule.myapplication.chatting.adapter.chat_type_detal;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;

import com.fule.myapplication.R;
import com.fule.myapplication.chatting.adapter.chat_type.ChattingRowType;
import com.fule.myapplication.chatting.adapter.holder.BaseHolder;
import com.fule.myapplication.chatting.adapter.holder.TextViewHolder;

import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.TextMessage;


/**
 * 文本消息  发送
 */
public class TextTxRow extends BaseChattingRow {
    /**
     * 构造方法
     *
     * @param type 类型
     */
    public TextTxRow(int type) {
        super(type);
    }

    /**
     * 设置文本视图
     *
     * @param inflater    视图加载器
     * @param convertView 内容视图
     * @return
     */
    @Override
    public View buildChatView(LayoutInflater inflater, View convertView) {
        //创建视图
        if (convertView == null || ((BaseHolder) convertView.getTag()).getType() != mRowType) { //????
            convertView = new ChattingItemContainer(inflater, R.layout.chatting_item_to);  //构造文本视图
            TextViewHolder holder = new TextViewHolder(mRowType);   //创建holder
            convertView.setTag(holder.initBaseHolder(convertView, false));     //设置标记的同时，初始化holder
        }
        return convertView;
    }

    /**
     * 设置文本数据
     *
     * @param context
     * @param baseHolder 向上转型的 基类holde
     * @param msg        消息体
     * @param position   位置
     */
    @Override
    public void buildChattingData(Context context, BaseHolder baseHolder,
                                  Message msg, int position) {
        TextViewHolder holder = (TextViewHolder) baseHolder;
        if (msg != null) {
                MessageContent messageContent = msg.getContent();     //获取消息内容
                holder.getDescTextView().setText(((TextMessage) messageContent).getContent());  // 使用holder 显示文本信息
//                holder.getDescTextView().setMovementMethod(LinkMovementMethod.getInstance());  //添加文本链接
//                OnClickListener onClickListener = ((ChattingActivity) context).mChattingFragment.getChattingAdapter().getOnClickListener();  //获取适配器中的监听器，所有的聊天界面的监听都使用 ChattingListClickListener 。
//                getMsgStateResId(position, holder, msg, onClickListener);              //添加文本发送状态以及添加 文本的重发监听
        }
    }

    /**
     * 聊天类型
     *
     * @return
     */
    @Override
    public int getChatViewType() {
        return ChattingRowType.TEXT_ROW_TRANSMIT.ordinal();
    }

    /**
     * 菜单 暂未使用
     *
     * @param contextMenu
     * @param targetView
     * @param detail
     * @return
     */
    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu,
                                          View targetView, Message detail) {

        return false;
    }


}
