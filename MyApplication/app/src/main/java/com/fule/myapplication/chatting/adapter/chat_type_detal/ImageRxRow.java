
package com.fule.myapplication.chatting.adapter.chat_type_detal;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;

import com.fule.myapplication.R;
import com.fule.myapplication.chatting.adapter.chat_type.ChattingRowType;
import com.fule.myapplication.chatting.adapter.holder.BaseHolder;
import com.fule.myapplication.chatting.adapter.holder.ImageRowViewHolder;
import com.fule.myapplication.chatting.adapter.holder.ViewHolderTag;

import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;

/**
 * 图片类型  收的
 */
public class ImageRxRow extends BaseChattingRow {

    /**
     * 构造方法
     *
     * @param type 聊天类型
     */
    public ImageRxRow(int type) {
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
            convertView = new ChattingItemContainer(inflater,
                    R.layout.chatting_item_from_picture);

            // use the view holder pattern to save of already looked up subviews
            ImageRowViewHolder holder = new ImageRowViewHolder(mRowType);
            convertView.setTag(holder.initBaseHolder(convertView, true));

        }
        return convertView;
    }

    /**
     * 设置图片数据
     *
     * @param context    环境
     * @param baseHolder 向上转型的 基类holde
     * @param detail     消息体
     * @param position   位置
     */
    @Override
    public void buildChattingData(Context context, BaseHolder baseHolder,
                                  Message detail, int position) {

        ImageRowViewHolder holder = (ImageRowViewHolder) baseHolder;

        ImageMessage content = (ImageMessage) detail.getContent();
        //设置图片内容
        holder.chattingContentIv.setImageURI(content.getThumUri());
        //创建图片点击事件的标记
        ViewHolderTag holderTag = ViewHolderTag.createTag(detail,
                ViewHolderTag.TagType.TAG_VIEW_PICTURE, position);
//        View.OnClickListener onClickListener = ((ChattingActivity) context).mChattingFragment
//                .getChattingAdapter().getOnClickListener();
//        holder.chattingContentIv.setTag(holderTag);
//        holder.chattingContentIv.setOnClickListener(onClickListener);


    }

    @Override
    public int getChatViewType() {

        return ChattingRowType.IMAGE_ROW_RECEIVED.ordinal();
    }

    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu,
                                          View targetView, Message detail) {
        // TODO Auto-generated method stub
        return false;
    }

}
