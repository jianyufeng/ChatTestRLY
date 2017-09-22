
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
 * 收消息
 * 文本类视图
 */
public class TextRxRow extends BaseChattingRow {

    /**
     * 构造方法
     *
     * @param type 聊天类型
     */
    public TextRxRow(int type) {
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
        if (convertView == null) {
            convertView = new ChattingItemContainer(inflater, R.layout.chatting_item_from);  //构造文本视图
            //use the view holder pattern to save of already looked up subviews
            TextViewHolder holder = new TextViewHolder(mRowType);                            //创建holder
            convertView.setTag(holder.initBaseHolder(convertView, true));                   //设置标记的同时，初始化holder
        }
        return convertView;
    }

    /**
     * 设置文本数据
     *
     * @param context
     * @param baseHolder 向上转型的 基类holde
     * @param detail     消息体
     * @param position   位置
     */
    @Override
    public void buildChattingData(final Context context, BaseHolder baseHolder,
                                  Message detail, int position) {

        TextViewHolder holder = (TextViewHolder) baseHolder;  //向上转型获取到文本类的holder
        Message message = detail;
        if (message != null) {
            MessageContent messageContent = message.getContent();   //获取消息的类型
            String content = ((TextMessage) messageContent).getContent();  //获取消息的内容
            holder.getDescTextView().setText(content);                  //使用holder 显示消息的文本内容
//				holder.getDescTextView().setMovementMethod(LinkMovementMethod.getInstance());//????

//			else if(messageContent instanceof  Message.Type.CALL){
//				ECCallMessageBody textBody = (ECCallMessageBody) message.getBody();
//				holder.getDescTextView().setText(textBody.getCallText());
//				holder.getDescTextView().setMovementMethod(LinkMovementMethod.getInstance());
//			}
        }
    }

    /**
     * 聊天类型
     *
     * @return
     */
    @Override
    public int getChatViewType() {

        return ChattingRowType.TEXT_ROW_RECEIVED.ordinal();
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
