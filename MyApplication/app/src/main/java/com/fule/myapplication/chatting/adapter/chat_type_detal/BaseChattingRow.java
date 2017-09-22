package com.fule.myapplication.chatting.adapter.chat_type_detal;

import android.content.Context;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.View;

import com.fule.myapplication.R;
import com.fule.myapplication.chatting.adapter.holder.BaseHolder;
import com.fule.myapplication.chatting.adapter.holder.ViewHolderTag;
import com.fule.myapplication.chatting.ui.ChattingActivity;
import com.fule.myapplication.common.LogUtil;

import java.util.HashMap;

import io.rong.imlib.model.Message;


/**
 * 抽出聊天的公共部分进行相应的处理
 * 1 主要是对聊天的人物设置图像
 * 2 和设置图像点击事件
 */
public abstract class BaseChattingRow implements IChattingRow {
    private static final String TAG = "BaseChattingRow";
    private HashMap<String, String> hashMap = new HashMap<String, String>();
    int mRowType;

    public BaseChattingRow(int type) {
        mRowType = type;
    }

    /**
     * 处理消息的发送状态设置
     *
     * @param position 消息的列表所在位置
     * @param holder   消息ViewHolder
     * @param l        点击事件
     */
    protected static void getMsgStateResId(int position, BaseHolder holder, Message msg, View.OnClickListener l) {
        if (msg != null && msg.getMessageDirection() == Message.MessageDirection.SEND) {//发送的消息
            Message.SentStatus msgStatus = msg.getSentStatus();
            if (msgStatus == Message.SentStatus.FAILED) {//发送失败
                holder.getUploadState().setImageResource(R.drawable.msg_state_fail_resend);//发送失败显示重发
                holder.getUploadState().setVisibility(View.VISIBLE);
                if (holder.getUploadProgressBar() != null) {  //发送的进度 隐藏进度
                    holder.getUploadProgressBar().setVisibility(View.GONE);
                }
            } else if (msgStatus == Message.SentStatus.SENT || msgStatus == Message.SentStatus.RECEIVED) { //已发送，或对方已读
                holder.getUploadState().setImageResource(0);  //隐藏   （发送失败显示重发）
                holder.getUploadState().setVisibility(View.GONE);
                if (holder.getUploadProgressBar() != null) {   //隐藏进度
                    holder.getUploadProgressBar().setVisibility(View.GONE);
                }

            } else if (msgStatus == Message.SentStatus.SENDING) { //正在发送
                holder.getUploadState().setImageResource(0);     //隐藏   （发送失败显示重发）
                holder.getUploadState().setVisibility(View.GONE);
                if (holder.getUploadProgressBar() != null) {     //显示进度
                    holder.getUploadProgressBar().setVisibility(View.VISIBLE);
                }
            } else {   //其他的发送状态 暂时未有
                if (holder.getUploadProgressBar() != null) {
                    holder.getUploadProgressBar().setVisibility(View.GONE);
                }
                LogUtil.d(TAG, "getMsgStateResId: not found this state");
            }

            ViewHolderTag holderTag = ViewHolderTag.createTag(msg, ViewHolderTag.TagType.TAG_RESEND_MSG, position);//消息重发
            holder.getUploadState().setTag(holderTag);//设标记
            holder.getUploadState().setOnClickListener(l);//设置点击事件
        }
    }

    /**
     * 创建菜单
     *
     * @param contextMenu
     * @param targetView
     * @param detail
     * @return
     */
    public abstract boolean onCreateRowContextMenu(ContextMenu contextMenu, View targetView, Message detail);


    /**
     * 设置聊天显示的名字
     *
     * @param baseHolder
     * @param displayName
     */
    public static void setDisplayName(BaseHolder baseHolder, String displayName) {
        if (baseHolder == null || baseHolder.getChattingUser() == null) {
            return;
        }

        if (TextUtils.isEmpty(displayName)) {  //隐藏
            baseHolder.getChattingUser().setVisibility(View.GONE);
            return;
        }
        baseHolder.getChattingUser().setText(displayName);//显示
        baseHolder.getChattingUser().setVisibility(View.VISIBLE);
    }

    /**
     * 构建数据的
     *
     * @param context
     * @param baseHolder
     * @param detail
     * @param position
     */
    protected abstract void buildChattingData(Context context, BaseHolder baseHolder, Message detail, int position);

    /**
     * 构建基础的数据
     *
     * @param context
     * @param baseHolder
     * @param detail
     * @param position
     */
    @Override
    public void buildChattingBaseData(Context context, BaseHolder baseHolder, Message detail, int position) {


        buildChattingData(context, baseHolder, detail, position);     // 处理其他使用逻辑
        setContactPhoto(baseHolder, detail);//设置聊天用户头像
//        if (((ChattingActivity) context).isPeerChat() && detail.getDirection() == ECMessage.Direction.RECEIVE) {//群聊
//            ECContacts contact = ContactSqlManager.getContactByFriendName(detail.getForm());
//            if (contact != null) {
//                if (TextUtils.isEmpty(contact.getNickname())) {
//                    contact.setNickname(contact.getFriendname());
//                }
//                setDisplayName(baseHolder, contact.getNickname());
//            } else {
//                setDisplayName(baseHolder, detail.getForm());
//            }
//        }
        setContactPhotoClickListener(context, baseHolder, detail);//设置聊天界面图像的点击事件
    }

    /**
     * 点击头像的事件
     *
     * @param context
     * @param baseHolder
     * @param detail
     */
    private void setContactPhotoClickListener(final Context context, BaseHolder baseHolder, final Message detail) {
        if (baseHolder.getChattingAvatar() != null && detail != null) {
            baseHolder.getChattingAvatar().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {  //点击跳转联系人详情
                    //图像点击事件  先获取联系的信息在做相关处理
//                    ECContacts contact = ContactSqlManager.getContactByFriendName(detail.getForm());
//                    ClientUser c = CCPAppManager.getClientUser();
//                    if (detail.getForm().equals(c.getUserId())) {
//                        contact = ClientUser.toECContacts(c);
//                    }
//                    if (contact == null || contact.getFriendname() == null) {
//                        return;
//                    }
//                    Log.i(TAG, "onClick: " + contact.getFriendname());
//                    Log.i(TAG, "onClick: " + contact.getNickname());
//                    Log.i(TAG, "onClick: " + contact.getFriendid());
//                    Intent intent = new Intent(context, ContactInfoActivity.class);
//                    intent.putExtra(MyString.CONTACT, contact);
//                    context.startActivity(intent);
                }
            });

            /***
             * 群组头像的长按事件  @某人
             */
            baseHolder.getChattingAvatar().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (context instanceof ChattingActivity) {
                        final ChattingActivity activity = (ChattingActivity) context;//@群组中的某个人 ，将其依次添加
//                        if (activity.isPeerChat() && !activity.mChattingFragment.mAtsomeone) {
//                            activity.mChattingFragment.mAtsomeone = true;
//                            // 群组
//                            ECContacts contact = ContactSqlManager.getContactByFriendName(detail.getForm());
//                            if (contact != null) {
//                                if (TextUtils.isEmpty(contact.getNickname())) {
//                                    contact.setNickname(contact.getFriendname());
//                                }
//                                activity.mChattingFragment.getChattingFooter().setLastText(activity.mChattingFragment.getChattingFooter().getLastText() + "@" + contact.getNickname() + (char) (8197));
//                                activity.mChattingFragment.getChattingFooter().setMode(1);
//                                v.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        activity.mChattingFragment.mAtsomeone = false;
//                                    }
//                                }, 2000L);
//                            }
//                        }
                        return true;
                    }
                    return false;
                }
            });
        }
    }


    /**
     * 设置聊天用户头像
     *
     * @param baseHolder
     * @param
     */
    private void setContactPhoto(BaseHolder baseHolder, Message message) {
        if (baseHolder.getChattingAvatar() == null) {//没有图像控件， 没有目标 ？？？？(问题是啥情况下 不加载用户图像)
            return;
        }
        if (message.getMessageDirection() == Message.MessageDirection.SEND) { //设置自己图像

            baseHolder.getChattingAvatar().setImageResource(R.mipmap.ic_launcher);

        } else if (message.getMessageDirection() == Message.MessageDirection.RECEIVE) {////设置对方的图像

            baseHolder.getChattingAvatar().setImageResource(R.mipmap.ic_launcher);

        }
    }

}
