
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
 * 图片消息  发的
 */
public class ImageTxRow extends BaseChattingRow {

    /**
     * 构造方法
     *
     * @param type 聊天类型
     */
    public ImageTxRow(int type) {
        super(type);
    }

    /**
     * 设置图片视图
     *
     * @param inflater    视图加载器
     * @param convertView 内容视图
     * @return
     */
    @Override
    public View buildChatView(LayoutInflater inflater, View convertView) {
        //创建视图
        if (convertView == null) {
            convertView = new ChattingItemContainer(inflater, R.layout.chatting_item_to_picture); //构造图片视图
            ImageRowViewHolder holder = new ImageRowViewHolder(mRowType);                        // 构建 图片对应的 holder对象
            convertView.setTag(holder.initBaseHolder(convertView, false));                       // 设置标记的同时，初始化holder

        }
        return convertView;                                                                     //返回视图
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

        ImageRowViewHolder holder = (ImageRowViewHolder) baseHolder;   //向上转型获取到文本类的holder

        ImageMessage content = (ImageMessage) detail.getContent();
        //设置图片内容
        holder.chattingContentIv.setImageURI(content.getThumUri());

        //创建图片点击事件的标记
        ViewHolderTag holderTag = ViewHolderTag.createTag(detail,
                ViewHolderTag.TagType.TAG_VIEW_PICTURE, position);
        //获取点击事件
//        OnClickListener onClickListener = ((ChattingActivity) context).mChattingFragment
//                .getChattingAdapter().getOnClickListener();
        //设置标记 设置点击事件
//        holder.chattingContentIv.setTag(holderTag);
//        holder.chattingContentIv.setOnClickListener(onClickListener);
//        getMsgStateResId(position, holder, detail, onClickListener);     //添加图片发送状态，以及添加重发点击事件

//        设置图片的大小尺寸
//        int startWidth = userData.indexOf("outWidth://");
//        int startHeight = userData.indexOf(",outHeight://");
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.chattingContentIv
//                .getLayoutParams();
//        if (startWidth != -1 && startHeight != -1 && start != -1) {
//            int imageMinWidth = /* DemoUtils.getImageMinWidth(context) */ResourceHelper
//                    .fromDPToPix(context, isGif ? 200 : 100);
//            int width = DemoUtils.getInt(userData.substring(startWidth
//                    + "outWidth://".length(), startHeight), imageMinWidth);
//            int height = DemoUtils.getInt(userData.substring(startHeight
//                    + ",outHeight://".length(), start - 1), imageMinWidth);
//            holder.chattingContentIv.setMinimumWidth(imageMinWidth);
//            params.width = imageMinWidth;
//            int _height = height * imageMinWidth / width;
//            if (_height > ResourceHelper.fromDPToPix(context, 230)) {
//                _height = ResourceHelper.fromDPToPix(context, 230);
//                holder.chattingContentIv
//                        .setScaleType(ImageView.ScaleType.CENTER_CROP);
//            }
//            if (width != 0) {
//                holder.chattingContentIv.setMinimumHeight(_height);
//                params.height = _height;
//            } else {
//                holder.chattingContentIv.setMinimumHeight(imageMinWidth);
//                params.height = imageMinWidth;
//            }
//            holder.chattingContentIv.invalidate();
//            holder.chattingContentIv.setLayoutParams(params);
//        }
        // }
    }

    /**
     * 聊天类型
     *
     * @return
     */
    @Override
    public int getChatViewType() {
        return ChattingRowType.IMAGE_ROW_TRANSMIT.ordinal();
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
        // TODO Auto-generated method stub
        return false;
    }

}
