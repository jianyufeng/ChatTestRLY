package com.fule.myapplication.chatting.message;

import android.util.Log;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.message.TextMessage;

/**
 * Created by Administrator on 2016/8/3.
 */
public class RCIMChatting {
    private static final String TAG = "RCIMChatting";

    public static void sendMessage(String msg){
        /**
         * 发送消息。
         * @param conversationType  会话类型
         * @param targetId          会话ID
         */
        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, "jianyufeng",
                TextMessage.obtain(msg), null, null, new RongIMClient.SendMessageCallback() {
                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d(TAG, "发送成功");
                    }

                    @Override
                    public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                        Log.d(TAG, "发送失败");
                    }
                }, null);
//        RongIMClient.getInstance().getRealTimeLocation().v
    }
}
