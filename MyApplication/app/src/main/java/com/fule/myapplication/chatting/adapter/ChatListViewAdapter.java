package com.fule.myapplication.chatting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fule.myapplication.R;
import com.fule.myapplication.chatting.adapter.chat_type.ChattingRowType;
import com.fule.myapplication.chatting.adapter.chat_type.ChattingsRowUtils;
import com.fule.myapplication.chatting.adapter.chat_type_detal.BaseChattingRow;
import com.fule.myapplication.chatting.adapter.chat_type_detal.IChattingRow;
import com.fule.myapplication.chatting.adapter.chat_type_detal.ImageRxRow;
import com.fule.myapplication.chatting.adapter.chat_type_detal.ImageTxRow;
import com.fule.myapplication.chatting.adapter.chat_type_detal.LocationRxRow;
import com.fule.myapplication.chatting.adapter.chat_type_detal.LocationTxRow;
import com.fule.myapplication.chatting.adapter.chat_type_detal.TextRxRow;
import com.fule.myapplication.chatting.adapter.chat_type_detal.TextTxRow;
import com.fule.myapplication.chatting.adapter.chat_type_detal.VoiceRxRow;
import com.fule.myapplication.chatting.adapter.chat_type_detal.VoiceTxRow;
import com.fule.myapplication.chatting.adapter.holder.BaseHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.rong.imlib.model.Message;

/**
 * Created by Administrator on 2016/8/4.
 * 如果要填加其他类型的界面，在  initRowItems();中添加
 */
public class ChatListViewAdapter extends BaseAdapter {
    /**
     * 初始化所有类型的聊天Item 集合
     */
    private HashMap<Integer, IChattingRow> mRowItems;
    /**
     * 数据
     */
    private ArrayList<Message> datas;
    /**
     * 需要显示时间的Item position
     */
    private ArrayList<String> mShowTimePosition;
    /**
     * 当前语音播放的Item
     */
    public int mVoicePosition = -1;


    private Context mContext;

    /**
     * 构造方法
     *
     * @param ctx
     */
    public ChatListViewAdapter(Context ctx, ArrayList<Message> datas) {
        this.mContext = ctx;
        this.datas = datas;
        this.mShowTimePosition = new ArrayList<>();
        mRowItems = new HashMap<>();
        initRowItems();
    }

    public void addDatas(List<Message> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(Message data) {
        this.datas.add(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Message getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message item = (Message) getItem(position);
        if (item == null) {
            return null;
        }
        /**显示时间的判断*/
        boolean showTimer = false;
        if (position == 0) {
            showTimer = true;
        }
        // 两条消息如果超过3分钟就显示时间
        if (position != 0) {
            Message previousItem = getItem(position - 1);
            if (mShowTimePosition.contains(item.getMessageId())
                    || (item.getSentTime() - previousItem.getSentTime() >= 180000L)) { //时间的使用 ？？？是使用发送 环视使用接受到的时间
                showTimer = true;
            }
        }
        /** 为创建对应视图*/
        int messageType = ChattingsRowUtils.getChattingMessageType(item.getContent());//由消息获取消息类型
        BaseChattingRow chattingRow = getBaseChattingRow(messageType, item.getMessageDirection() == Message.MessageDirection.SEND);//由消息类型拼接为消息类型视图
        View chatView = chattingRow.buildChatView(LayoutInflater.from(mContext), convertView);    //创建消息视图
        BaseHolder baseHolder = (BaseHolder) chatView.getTag();                                  // 获取视图对应的holder

        /**创建视图后设置对应视图中的时间*/
        //设置时间内容
        if (showTimer) {
            baseHolder.getChattingTime().setVisibility(View.VISIBLE); //时间可见
            baseHolder.getChattingTime().setBackgroundResource(R.color.colorAccent); //设置背景色
            baseHolder.getChattingTime().setText(item.getSentTime() + "");//设置时间 需要格式化时间-----暂时使用的是发送时间 ？？？？？？？？？
//            baseHolder.getChattingTime().setTextColor(mChatNameColor[0]);//设置文本颜色
//            baseHolder.getChattingTime().setPadding(mHorizontalPadding, mVerticalPadding, mHorizontalPadding, mVerticalPadding);//设置边距
        } else {
            baseHolder.getChattingTime().setVisibility(View.GONE);  //时间不可见
            baseHolder.getChattingTime().setShadowLayer(0.0F, 0.0F, 0.0F, 0);
            baseHolder.getChattingTime().setBackgroundResource(0);
        }
        /** 为对应视图添加数据*/
        chattingRow.buildChattingBaseData(mContext, baseHolder, item, position);

        return chatView;
    }

    /**
     * 返回消息的类型ID
     */
    @Override
    public int getItemViewType(int position) {
        Message message = (Message) getItem(position);
        return getBaseChattingRow(ChattingsRowUtils.getChattingMessageType(message.getContent()), message.getMessageDirection() == Message.MessageDirection.SEND).getChatViewType();
    }

    /**
     * 消息类型数
     */
    @Override
    public int getViewTypeCount() {
        return ChattingRowType.values().length;
    }


    /**
     * 初始化不同的聊天Item View
     */
    void initRowItems() {
        mRowItems.put(ChattingRowType.TEXT_ROW_TRANSMIT.ordinal(), new TextTxRow(ChattingRowType.TEXT_ROW_TRANSMIT.ordinal()));   //文本类-发消息
        mRowItems.put(ChattingRowType.TEXT_ROW_RECEIVED.ordinal(), new TextRxRow(ChattingRowType.TEXT_ROW_RECEIVED.ordinal()));   //文本类-收消息
        mRowItems.put(ChattingRowType.IMAGE_ROW_TRANSMIT.ordinal(), new ImageTxRow(ChattingRowType.IMAGE_ROW_TRANSMIT.ordinal())); //图片类-发消息
        mRowItems.put(ChattingRowType.IMAGE_ROW_RECEIVED.ordinal(), new ImageRxRow(ChattingRowType.IMAGE_ROW_RECEIVED.ordinal())); //图片类-收消息
        mRowItems.put(ChattingRowType.LOCATION_ROW_TRANSMIT.ordinal(), new LocationTxRow(ChattingRowType.LOCATION_ROW_TRANSMIT.ordinal()));//位置类-发消息
        mRowItems.put(ChattingRowType.LOCATION_ROW_RECEIVED.ordinal(), new LocationRxRow(ChattingRowType.LOCATION_ROW_RECEIVED.ordinal()));//位置类-收消息
        mRowItems.put(ChattingRowType.VOICE_ROW_TRANSMIT.ordinal(), new VoiceRxRow(ChattingRowType.VOICE_ROW_TRANSMIT.ordinal()));        //语音类=发消息
        mRowItems.put(ChattingRowType.VOICE_ROW_RECEIVED.ordinal(), new VoiceTxRow(ChattingRowType.VOICE_ROW_RECEIVED.ordinal()));         //语音类=收消息
//        mRowItems.put(Integer.valueOf(9), new ChattingSystemRow(9));
//        mRowItems.put(Integer.valueOf(12), new CallRxRow(12));
//        mRowItems.put(Integer.valueOf(13), new CallTxRow(13));
    }

    /**
     * 根据消息类型返回相对应的消息Item
     *
     * @param rowType
     * @param isSend
     * @return
     */
    public BaseChattingRow getBaseChattingRow(int rowType, boolean isSend) {
        //拼接聊天类型  参见 ChattingRowType
        StringBuilder builder = new StringBuilder("C").append(rowType);
        if (isSend) {
            builder.append("T");
        } else {
            builder.append("R");
        }
        //获取聊天类型
        ChattingRowType fromValue = ChattingRowType.fromValue(builder.toString());
        //由聊天类型获取聊天对应的 消息类型
        IChattingRow iChattingRow = mRowItems.get(fromValue.getId().intValue());
        return (BaseChattingRow) iChattingRow;
    }
}
