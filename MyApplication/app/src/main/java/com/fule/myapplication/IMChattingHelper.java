package com.fule.myapplication;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongCommonDefine;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.TypingMessage.TypingStatus;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ImageMessage;
import io.rong.message.LocationMessage;
import io.rong.message.RecallNotificationMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * Created by 简玉锋 on 2016/8/11.
 * 1  在使用时需要初始化 RongIMClient， 2 然后连接调用  RongIMClient.connect()  连接一次就行了，融云的SDK会自动重新连接
 * 处理所有的和融云相关的消息
 * ConversationType 分为:
 * APP_PUBLIC_SERVICE  应用公众服务。 CHATROOM 聊天室。 CUSTOMER_SERVICE 客服。 DISCUSSION 讨论组。
 * GROUP 群组。 NONE 。 PRIVATE 私聊。 PUBLIC_SERVICE 公众服务平台。 PUSH_SERVICEpush推送SYSTEM系统。
 *
 *  2016-11-16 检查过时  1 updateMessageReceiptStatus  2  getReadReceipt  3
 */
public class IMChattingHelper implements RongIMClient.OnReceiveMessageListener {
    private static IMChattingHelper sInstance;

    //SDK的IM 客户端核心类
    private RongIMClient rongIMClient;

    /**
     * 构造方法
     */
    private IMChattingHelper() {
        // 获取 IMLib 接口类实例，需在执行 init 方法初始化 SDK 后获取, 否则返回值为 NULL。
        rongIMClient = RongIMClient.getInstance();
    }

    /**
     * 获取实例
     * 单利模式
     *
     * @return
     */
    public static IMChattingHelper getInstance() {
        if (sInstance == null) synchronized (new Object()) {
            sInstance = new IMChattingHelper();
        }
        return sInstance;
    }

//    public void init(){
//
//    }
//    public void con(){
//        RongIMClient.init();
//    }

    //****************获取融云服务器连接状态发送变化监听 1 个方法 start ********************************//

    /**
     * 设置连接状态变化的监听器。
     * 在自定义UI时，会需要调用此接口判断当前连接状态，来绘制UI及决定逻辑走向。
     *
     * @param listener 连接状态变化的监听器。
     */
    public void setConnectionStatusListener(final BaseInterface<RongIMClient.ConnectionStatusListener.ConnectionStatus> listener) {
        RongIMClient.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                listener.onSuccess(connectionStatus);
            }
        });
    }
    //****************获取融云服务器连接状态发送变化监听   end  ********************************//

    //****************获取当前连接用户的信息 1个方法   start  ********************************//

    /**
     * 获取当前连接用户的信息。等同于 connect(String, ConnectCallback) 成功后返回的 userId。
     *
     * @return 成功后返回的 userId。
     */
    public String getCurrentUserId() {
        return rongIMClient.getCurrentUserId();
    }
    //****************获取当前连接用户的信息 1个方法   end ********************************//

    //****************获取当前服务器连接状态 1个方法   start ********************************//

    /**
     * 获取当前服务器连接状态。在自定义UI时，会需要调用此接口判断当前连接状态，来绘制UI及决定逻辑走向.
     *
     * @return 连接状态枚举。CONNECTED  连接成功。     CONNECTING     连接中。     DISCONNECTED     断开连接。
     * KICKED_OFFLINE_BY_OTHER_CLIENT     用户账户在其他设备登录，本机会被踢掉线。
     * NETWORK_UNAVAILABLE     网络不可用。
     * SERVER_INVALID     服务器异常或无法连接。    TOKEN_INCORRECT     Token 不正确。
     */
    public RongIMClient.ConnectionStatusListener.ConnectionStatus getCurrentConnectionStatus() {
        return rongIMClient.getCurrentConnectionStatus();
    }
    //****************获取当前服务器连接状态 1个方法   end ********************************//

    //****************获取本地时间与服务器时间的差值 1个方法   start ********************************//

    /**
     * 获取本地时间与服务器时间的差值。 消息发送成功后，sdk 会与服务器同步时间，消息所在数据库中存储的时间就是服务器时间。
     * System.currentTimeMillis() - getDeltaTime()可以获取服务器当前时间。
     *
     * @return
     */
    public long getDeltaTime() {
        return rongIMClient.getDeltaTime();
    }
    //****************获取本地时间与服务器时间的差值 1个方法   end ********************************//


    //****************获取会话列表 2个方法 start********************************//

    /**
     * 获取所有的会话列表
     *
     * @return
     */
    public void getConversationList(final BaseInterface<List<Conversation>> listener) {
        rongIMClient.getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }

            @Override
            public void onSuccess(List<Conversation> conversations) {
                listener.onSuccess(conversations);

            }
        });
    }

    /**
     * 获取指定会话列表
     *
     * @param listener
     * @param type
     */
    public void getConversationList(final BaseInterface<List<Conversation>> listener, Conversation.ConversationType... type) {
        rongIMClient.getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                listener.onSuccess(conversations);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        }, type);
    }
    //****************获取会话列表 end ********************************//

    //****************获取某一会话信息 1个方法 start ********************************//

    /**
     * 根据不同会话类型的目标 Id，回调方式获取某一会话信息。
     *
     * @param type     会话类型。
     * @param targetId 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
     * @param listener 获取会话信息的回调
     */

    public void getConversation(Conversation.ConversationType type, String targetId, final BaseInterface<Conversation> listener) {
        rongIMClient.getConversation(type, targetId, new RongIMClient.ResultCallback<Conversation>() {
            @Override
            public void onSuccess(Conversation conversation) {
                listener.onSuccess(conversation);

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }
    //****************获取某一会话信息 1个方法 end ********************************//

    //****************更新会话信息 1个方法 start  ********************************//

    public void updateConversationInfo(Conversation.ConversationType type, String targetId, String title, String portrait, final BaseInterface<Conversation> listener) {
        rongIMClient.updateConversationInfo(type, targetId, title, portrait, new RongIMClient.ResultCallback<Conversation>() {
            @Override
            public void onSuccess(Conversation conversation) {
                listener.onSuccess(conversation);

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }
    //****************更新会话信息 end  ********************************//

    //****************从会话列表中移除某一会话 1个方法 start  ********************************//

    /**
     * 从会话列表中移除某一会话，但是不删除会话内的消息。
     * 如果此会话中有新的消息，该会话将重新在会话列表中显示，并显示最近的历史消息。
     *
     * @param type     会话类型。
     * @param targetId 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
     * @param listener 移除会话是否成功的回调
     */

    public void removeConversation(Conversation.ConversationType type, String targetId, final BaseInterface<Boolean> listener) {
        rongIMClient.removeConversation(type, targetId, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                listener.onSuccess(aBoolean);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }

    //****************设置某一会话为置顶或者取消置顶 1个方法 start ********************************//
    public void setConversationToTop(Conversation.ConversationType type, String targetId, Boolean isTop, final BaseInterface<Boolean> listener) {
        rongIMClient.setConversationToTop(type, targetId, isTop, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                listener.onSuccess(aBoolean);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }
    //****************设置某一会话为置顶或者取消置顶 1个方法 end  ********************************/

    /**
     * 清空会话类型列表中的所有会话及会话信息，回调方式通知是否清空成功。
     *
     * @param listener 是否清空成功的回调。
     * @param type     需要清空的会话类型列表。
     */
    public void clearConversations(final BaseInterface<Boolean> listener, @Nullable Conversation.ConversationType... type) {
        rongIMClient.clearConversations(new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                listener.onSuccess(aBoolean);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });

    }

    /**
     * 获取会话消息提醒状态
     *
     * @param type     会话类型
     * @param targetId 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id。
     * @param listener 获取状态的回调。
     */
    public void getConversationNotificationStatus(Conversation.ConversationType type, String targetId, final BaseInterface<List<Conversation>> listener) {
        rongIMClient.getConversationNotificationStatus(type, targetId, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
            /**
             *  枚举常量  DO_NOT_DISTURB   免打扰。   NOTIFY 提醒。
             * @param conversationNotificationStatus
             */
            @Override
            public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    /**
     * 设置会话消息提醒状态。
     *
     * @param type     会话类型。
     * @param targetId 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id。
     * @param status   枚举常量  DO_NOT_DISTURB   免打扰。   NOTIFY 提醒。  是否屏蔽。
     * @param listener 设置状态的回调。
     */
    public void setConversationNotificationStatus(Conversation.ConversationType type, String targetId, Conversation.ConversationNotificationStatus status, final BaseInterface<List<Conversation>> listener) {
        rongIMClient.setConversationNotificationStatus(type, targetId, status, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
            /**
             *  枚举常量  DO_NOT_DISTURB   免打扰。   NOTIFY 提醒。
             * @param conversationNotificationStatus
             */
            @Override
            public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }
///////////////////////上面是Conversation ///////////////////////////////////////////////////////////////////////

    /**
     * 设置消息通知免打扰时间。
     *
     * @param startTime   起始时间 格式 HH:MM:SS。
     * @param spanMinutes 间隔分钟数 0 < spanMinutes < 1440。
     * @param callback    消息通知免打扰时间回调。
     */
    public void setNotificationQuietHours(String startTime, int spanMinutes, final RongIMClient.OperationCallback callback) {
        rongIMClient.setNotificationQuietHours(startTime, spanMinutes, new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                callback.onError(errorCode);
            }
        });
    }

    /**
     * 获取消息通知免打扰时间。
     *
     * @param callback 消息通知免打扰时间回调。
     */
    public void getNotificationQuietHours(final RongIMClient.GetNotificationQuietHoursCallback callback) {
        rongIMClient.getNotificationQuietHours(new RongIMClient.GetNotificationQuietHoursCallback() {
            /**
             * 获取消息通知免打扰时间成功。
             * @param startTime 起始时间 格式 HH:MM:SS。
             * @param spanMinutes  间隔分钟数 0 < spanMins < 1440。
             */
            @Override
            public void onSuccess(String startTime, int spanMinutes) {
                callback.onSuccess(startTime, spanMinutes);
            }

            /**
             * 获取消息通知免打扰时间出错。
             * @param errorCode  获取消息通知免打扰时间错误代码。
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                callback.onError(errorCode);
            }
        });
    }

    /**
     * 移除消息通知免打扰时间。
     *
     * @param callback 移除消息通知免打扰时间回调。
     */
    public void removeNotificationQuietHours(final RongIMClient.OperationCallback callback) {
        rongIMClient.removeNotificationQuietHours(new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                callback.onError(errorCode);
            }
        });
    }

    /**
     * 根据 messageId 设置消息的发送状态。用于UI标记消息为正在发送，对方已接收等状态。
     *
     * @param messageId  消息 Id。
     * @param sentStatus 发送的消息状态，
     *                   Message.SentStatus。DESTROYED    对方已销毁。
     *                   FAILED    发送失败。
     *                   READ    对方已读。
     *                   RECEIVED    对方已接收。
     *                   SENDING    发送中。
     *                   SENT    已发送。
     * @param listener   是否设置成功的回调。
     */
    public void setMessageSentStatus(int messageId, Message.SentStatus sentStatus, final BaseInterface<Boolean> listener) {
        rongIMClient.setMessageSentStatus(messageId, sentStatus, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                listener.onSuccess(aBoolean);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }

    /**
     * 根据 messageId 设置接收到的消息状态。用于UI标记消息为已读，已下载等状态。
     *
     * @param messageId      消息 Id。
     * @param receivedStatus 接收到的消息状态。   接收到的消息的状态。 是一个按位标记的枚举，可以进行位运算。
     * @param listener       是否设置成功的回调。
     */
    public void setMessageReceivedStatus(int messageId, Message.ReceivedStatus receivedStatus, final BaseInterface<Boolean> listener) {
        rongIMClient.setMessageReceivedStatus(messageId, receivedStatus, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                listener.onSuccess(aBoolean);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }

    /**
     * 根据 messageId 设置本地消息的附加信息，用于扩展消息的使用场景。
     * 设置后可以通过 getHistoryMessages(io.rong.imlib.model.Conversation.ConversationType, java.lang.String, int, int)
     * 取出带附加信息的消息。只能用于本地使用，无法同步给远程用户。
     *
     * @param messageId 消息 Id。
     * @param value     消息附加信息，最大 1024 字节。1k
     * @param listener  是否设置成功的回调。
     */
    public void setMessageExtra(int messageId, String value, final BaseInterface<Boolean> listener) {
        rongIMClient.setMessageExtra(messageId, value, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                listener.onSuccess(aBoolean);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }

    /**
     * 清空指定类型，targetId 的某一会话所有聊天消息记录。
     *
     * @param type     会话类型。不支持传入 ConversationType.CHATROOM。
     * @param targetId 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id。
     * @param listener 清空是否成功的回调。
     */
    public void clearMessages(Conversation.ConversationType type, String targetId, final BaseInterface<Boolean> listener) {
        rongIMClient.clearMessages(type, targetId, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                listener.onSuccess(aBoolean);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }

    /**
     * 清除指定会话的消息
     * 此接口会删除指定会话中数据库的所有消息，同时，会清理数据库空间。 如果数据库特别大，超过几百 M，调用该接口会有少许耗时。
     *
     * @param type     指定的会话类型。
     * @param targetId 目标 Id。根据不同的 conversationType，可能是userId, groupId, discussionId。
     * @param listener 是否删除成功的回调。
     */
    public void deleteMessages(Conversation.ConversationType type, String targetId, final BaseInterface<Boolean> listener) {
        rongIMClient.deleteMessages(type, targetId, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                listener.onSuccess(aBoolean);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }

    /**
     * 根据 messageId，删除指定的一条或者一组消息。
     *
     * @param messageIds 要删除的消息 Id 数组
     * @param listener   是否删除成功的回调。
     */
    public void deleteMessages(int[] messageIds, final BaseInterface<Boolean> listener) {
        rongIMClient.deleteMessages(messageIds, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                listener.onSuccess(aBoolean);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }

    /**
     * 根据消息类型，targetId 获取某一会话的文字消息草稿。
     *
     * @param type     会话类型。
     * @param targetId 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
     * @param listener 获取草稿文字内容的回调。
     */
    public void getTextMessageDraft(Conversation.ConversationType type, String targetId, final BaseInterface<String> listener) {
        rongIMClient.getTextMessageDraft(type, targetId, new RongIMClient.ResultCallback<String>() {
            @Override
            public void onSuccess(String s) {
                listener.onSuccess(s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }

    /**
     * 根据消息类型，targetId 获取某一会话的文字消息草稿。用于获取用户输入但未发送的暂存消息。
     *
     * @param type     会话类型。
     * @param targetId 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
     * @param content  草稿的文字内容。
     * @param listener 是否保存成功的回调。
     */
    public void saveTextMessageDraft(Conversation.ConversationType type, String targetId, String content, final BaseInterface<Boolean> listener) {
        rongIMClient.saveTextMessageDraft(type, targetId, content, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                listener.onSuccess(aBoolean);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });

    }

    /**
     * 根据消息类型，targetId 清除某一会话的文字消息草稿。
     *
     * @param type     会话类型。
     * @param targetId 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
     * @param listener 是否清除成功的回调。
     */
    public void clearTextMessageDraft(Conversation.ConversationType type, String targetId, final BaseInterface<Boolean> listener) {
        rongIMClient.clearTextMessageDraft(type, targetId, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                listener.onSuccess(aBoolean);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }
    //****************获取本地消息 4个方法 start********************************//

    /**
     * 获取指定类型，targetId 的最新消息记录。通常在进入会话后，调用此接口拉取该会话的最近聊天记录。
     *
     * @param type     聊天的类型
     * @param targetId 聊天的对象
     * @param count    数量
     * @param listener 回调
     */
    public void getLatestMessages(Conversation.ConversationType type, String targetId, int count, final BaseInterface<List<Message>> listener) {
        rongIMClient.getLatestMessages(type, targetId, count, new RongIMClient.ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                listener.onSuccess(messages);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }

    /**
     * 获取与某个人的消息
     * 获取本地数据库中保存，特定类型，targetId 的N条历史消息记录。通过此接口可以根据情况分段加载历史消息,不支持拉取聊天室 Conversation.ConversationType.CHATROOM 历史消息
     *
     * @param type            会话类型
     * @param targetId        targetId - 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id。
     * @param oldestMessageId oldestMessageId - 最后一条消息的 Id，获取此消息之前的 count 条消息，没有消息第一次调用应设置为:-1。
     * @param count           要获取的消息数量。
     * @param listener        历史消息记录，按照时间顺序从新到旧排列。
     */
    public void getHistoryMessages(Conversation.ConversationType type, String targetId, int oldestMessageId, int count, final BaseInterface<List<Message>> listener) {
        rongIMClient.getHistoryMessages(type, targetId, oldestMessageId, count, new RongIMClient.ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                listener.onSuccess(messages);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }

    /**
     * 获取与某个人的不同类型的消息  如可以获取图片类的消息
     * 获取本地数据库中保存，特定类型，targetId 的N条历史消息记录。
     *
     * @param type            会话类型。
     * @param targetId        目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id。
     * @param objectName      消息类型标识。
     * @param oldestMessageId 最后一条消息的 Id，获取此消息之前的 count 条消息,没有消息第一次调用应设置为:-1。
     * @param count           要获取的消息数量。
     * @param listener        历史消息记录，按照时间顺序从新到旧排列。
     */
    public void getHistoryMessages(Conversation.ConversationType type, String targetId, String objectName, int oldestMessageId, int count, final BaseInterface<List<Message>> listener) {
        rongIMClient.getHistoryMessages(type, targetId, objectName, oldestMessageId, count, new RongIMClient.ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                listener.onSuccess(messages);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }

    /**
     * 根据会话类型的目标 Id，回调方式获取某消息类型的某条消息之前或之后的N条历史消息记录. 注意：返回的消息列表里面不包含oldestMessageId本身。
     *
     * @param type            会话类型。不支持传入 ConversationType.CHATROOM。
     * @param targetId        目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id。
     * @param objectName      消息类型标识。如RC:TxtMsg，RC:ImgMsg，RC:VcMsg等。
     * @param oldestMessageId 最后一条消息的 Id，获取此消息之前的 count 条消息,没有消息第一次调用应设置为:-1。
     * @param count           要获取的消息数量
     * @param direction       要获取的消息相对于 oldestMessageId 的方向 RongCommonDefine.GetMessageDirection 以指定的 message id 作为获取的起始点，时间早于该 id 则为 FRONT，晚于则为 BEHIND。
     * @param listener        获取历史消息记录的回调，按照时间顺序从新到旧排列。
     */
    public void getHistoryMessages(Conversation.ConversationType type, String targetId, String objectName, int oldestMessageId, int count, RongCommonDefine.GetMessageDirection direction, final BaseInterface<List<Message>> listener) {
        rongIMClient.getHistoryMessages(type, targetId, objectName, oldestMessageId,
                count, direction, new RongIMClient.ResultCallback<List<Message>>() {
                    @Override
                    public void onSuccess(List<Message> messages) {
                        listener.onSuccess(messages);
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        listener.onError(errorCode);
                    }
                });
    }
    //****************获取本地消息 end********************************//

    //****************获取远端服务器消息：融云的服务器 需要先开通历史消息漫游功能 start********************************//

    /**
     * 可以从服务器获取之前 40 条以内的消息历史记录（需要先开通历史消息漫游功能）。注意：：：：
     * 获取融云服务器中暂存，特定类型，targetId 的N条（一次不超过40条）历史消息记录。通过此接口可以根据情况分段加载历史消息，节省网络资源，提高用户体验。
     *
     * @param type     会话类型。不支持传入 ConversationType.CHATROOM。
     * @param targetId 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
     * @param dateTime 从该时间点开始获取消息。即：消息中的 sentTime；第一次可传 0，获取最新 count 条。
     * @param count    要获取的消息数量，最多 40 条。
     * @param listener 获取历史消息记录的回调，按照时间顺序从新到旧排列。
     */
    public void getRemoteHistoryMessages(Conversation.ConversationType type, String targetId, long dateTime, int count, final BaseInterface<List<Message>> listener) {
        rongIMClient.getRemoteHistoryMessages(type, targetId, dateTime,
                count, new RongIMClient.ResultCallback<List<Message>>() {
                    @Override
                    public void onSuccess(List<Message> messages) {
                        listener.onSuccess(messages);
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        listener.onError(errorCode);
                    }
                });
    }
    //****************获取远端服务器消息 1 个方法：融云的服务器 需要先开通历史消息漫游功能 end  ********************************//


////////////////////////////以上是消息的处理///////////////////////////////////////////////////////////////////////////////////

    /**
     * 清除指定类型，targetId 的某一会话消息未读状态。
     *
     * @param type     会话类型。不支持传入 ConversationType.CHATROOM。
     * @param targetId 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id。
     * @param listener 清除是否成功的回调。
     */
    public void clearMessagesUnreadStatus(Conversation.ConversationType type, String targetId, final BaseInterface<Boolean> listener) {
        rongIMClient.clearMessagesUnreadStatus(type, targetId, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                listener.onSuccess(aBoolean);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }
    //****************获取未读消息数量 4 个方法 start********************************//

    /**
     * 主要是针对某个特定的人
     * 根据会话类型的目标 Id，回调方式获取来自某用户（某会话）的未读消息数。
     *
     * @param type     会话类型。
     * @param targetId 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id。
     * @param listener 未读消息数的回调。
     */
    public void getUnreadCount(Conversation.ConversationType type, String targetId, final BaseInterface<Integer> listener) {
        rongIMClient.getUnreadCount(type, targetId, new RongIMClient.ResultCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                listener.onSuccess(integer);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }

    /**
     * 主要是针对不同类别  如群组，讨论组
     * 根据会话类型数组，回调方式获取某会话类型的未读消息数。
     *
     * @param types    会话类型。
     * @param listener 未读消息数的回调
     */
    public void getUnreadCount(Conversation.ConversationType[] types, final BaseInterface<Integer> listener) {
        rongIMClient.getUnreadCount(types, new RongIMClient.ResultCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                listener.onSuccess(integer);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }

    /**
     * 主要是针对不同类别  如群组，讨论组
     * 根据会话类型数组，回调方式获取某会话类型的未读消息数。
     *
     * @param types    会话类型。
     * @param listener 未读消息数的回调
     */
    public void getUnreadCount(final BaseInterface<Integer> listener, Conversation.ConversationType... types) {
        rongIMClient.getUnreadCount(new RongIMClient.ResultCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                listener.onSuccess(integer);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        }, types);
    }

    /**
     * 获取所有未读消息数
     *
     * @param listener 消息数的回调
     */
    public void getTotalUnreadCount(final BaseInterface<Integer> listener) {
        rongIMClient.getTotalUnreadCount(new RongIMClient.ResultCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                listener.onSuccess(integer);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }
    //****************获取未读消息数量 end********************************//


    //*************** 构建文本类消息对象 start ********************************//
    public TextMessage buildTextMessage(String msg) {
        return TextMessage.obtain(msg);
    }
    //*************** 构建文本类消息对象 end ********************************//

    //*************** 构建图片类消息对象 start ********************************//
    public ImageMessage buildImageMessage(Uri thumUri, Uri localUri) {
        return ImageMessage.obtain(thumUri, localUri);
    }
    //*************** 构建图片类消息对象 end ********************************//

    //*************** 构建位置类消息对象 start ********************************//
    public LocationMessage buildLocationMessage(double lat, double lng, String poi, Uri imgUri) {
        return LocationMessage.obtain(lat, lng, poi, imgUri);
    }
    //*************** 构建位置类消息对象 end ********************************//

    // *************** 构建语音类消息对象 start ********************************//
    public VoiceMessage buildLocationMessage(Uri uri, int duration) {
        return VoiceMessage.obtain(uri, duration);
    }
    //*************** 构建语音类消息对象 end ********************************//


    //*************** 发送消息 4个方法 start ********************************//
    //SDK 针对普通消息、图片消息（上传到融云默认的图片服务器）提供了不同的接口。所以分了发送

    /**
     * @param type        会话类型。
     * @param targetId    目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
     * @param content     消息内容，例如 TextMessage, ImageMessage。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。 如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。 如果发送 sdk 中默认的消息类型，
     *                    例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 PushNotificationMessage.getPushData() 方法获取。
     * @param listener    发送消息的回调
     */
    public void sendMessage(Conversation.ConversationType type, String targetId, MessageContent content, String pushContent, String pushData, final SendMessageInterface<Message> listener) {
        rongIMClient.sendMessage(type, targetId, content, pushContent, pushData, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                listener.onAttached(message);
            }

            @Override
            public void onSuccess(Message message) {
                listener.onSuccess(message);
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                listener.onError(message, errorCode);
            }
        });

    }

    /**
     * 根据会话类型，发送消息。
     *
     * @param type        会话类型。
     * @param targetId    目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
     * @param content     消息内容，例如 TextMessage, ImageMessage。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。 如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。 如果发送 sdk 中默认的消息类型，
     *                    例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 PushNotificationMessage.getPushData() 方法获取。
     * @param callBack    发送消息的回调，消息经网络发送成功或失败，通过此回调返回。
     * @param listener    获取发送消息实体的回调，消息存储数据库后，通过此回调返回。
     */
    public void sendMessage(Conversation.ConversationType type, String targetId, MessageContent content, String pushContent, String pushData, final SendMessageCallBack<Integer> callBack, final BaseInterface<Message> listener) {
        rongIMClient.sendMessage(type, targetId, content, pushContent, pushData,
                new RongIMClient.SendMessageCallback() {
                    @Override
                    public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                        callBack.sendFail(integer, errorCode);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        callBack.sendSuccess(integer);
                    }
                }
                , new RongIMClient.ResultCallback<Message>() {
                    @Override
                    public void onSuccess(Message message) {
                        listener.onSuccess(message);
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        listener.onError(errorCode);
                    }
                });
    }

    /**
     * 发送消息。
     *
     * @param message     发送消息的实体。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。 如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。 如果发送 sdk 中默认的消息类型，
     *                    例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 PushNotificationMessage.getPushData() 方法获取。
     * @param listener    发送消息的回调，参考 IRongCallback.ISendMessageCallback。
     */
    public void sendMessage(Message message, String pushContent, String pushData, final SendMessageInterface<Message> listener) {
        rongIMClient.sendMessage(message, pushContent, pushData,
                new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                        listener.onAttached(message);
                    }

                    @Override
                    public void onSuccess(Message message) {
                        listener.onSuccess(message);
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        listener.onError(message, errorCode);
                    }
                });
    }

    /**
     * 发送消息
     *
     * @param message     发送消息的实体。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。 如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。 如果发送 sdk 中默认的消息类型，
     *                    例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 PushNotificationMessage.getPushData() 方法获取。
     * @param callBack    发送消息的回调，消息经网络发送成功或失败，通过此回调返回。
     * @param listener    获取发送消息实体的回调，消息存储数据库后，通过此回调返回。 sendMessage(Message, String, String, IRongCallback.ISendMessageCallback)
     */
    public void sendMessage(Message message, String pushContent, String pushData, final SendMessageCallBack<Integer> callBack, final BaseInterface<Message> listener) {
        rongIMClient.sendMessage(message, pushContent, pushData,
                new RongIMClient.SendMessageCallback() {
                    @Override
                    public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                        callBack.sendFail(integer, errorCode);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        callBack.sendSuccess(integer);
                    }
                }
                , new RongIMClient.ResultCallback<Message>() {
                    @Override
                    public void onSuccess(Message message) {
                        listener.onSuccess(message);
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        listener.onError(errorCode);
                    }
                });
    }

    //*************** 发送消息 end ********************************//

    //*************** 发送图片消息 start ********************************//
    //图片消息包括两个主要部分：缩略图和大图，缩略图直接 Base64 编码后放入 content 中，
    // 大图首先上传到文件服务器（融云 SDK 中默认上传到七牛云存储，图片有效期为 1 个月。），然后将云存储上的大图地址放入消息体中。

    /**
     * 根据会话类型，发送图片消息。
     *
     * @param type        会话类型。
     * @param targetId    目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
     * @param content     消息内容，例如 TextMessage, ImageMessage。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。 如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。 如果发送 sdk 中默认的消息类型，
     *                    例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 PushNotificationMessage.getPushData() 方法获取。
     * @param listener    发送消息的回调。 sendMessage(Conversation.ConversationType, String, MessageContent, String, String, IRongCallback.ISendMessageCallback)
     */
    public void sendMessage(Conversation.ConversationType type, String targetId, MessageContent content, String pushContent, String pushData, final SendImageMessageInteface<Message> listener) {
        rongIMClient.sendImageMessage(type, targetId, content, pushContent, pushData, new RongIMClient.SendImageMessageCallback() {
            @Override
            public void onAttached(Message message) {
                listener.onAttached(message);
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                listener.onError(message, errorCode);
            }

            @Override
            public void onSuccess(Message message) {
                listener.onSuccess(message);
            }

            @Override
            public void onProgress(Message message, int progress) {
                listener.onProgress(message, progress);
            }
        });

    }

    /**
     * 发送图片消息
     *
     * @param message     发送消息的实体。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。 如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。 如果发送 sdk 中默认的消息类型，
     *                    例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 PushNotificationMessage.getPushData() 方法获取。
     * @param listener    发送消息的回调 RongIMClient.SendImageMessageCallback。
     */
    public void sendImageMessage(Message message, String pushContent, String pushData, final SendImageMessageInteface<Message> listener) {
        rongIMClient.sendImageMessage(message, pushContent, pushData, new RongIMClient.SendImageMessageCallback() {
            @Override
            public void onAttached(Message message) {
                listener.onAttached(message);
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                listener.onError(message, errorCode);
            }

            @Override
            public void onSuccess(Message message) {
                listener.onSuccess(message);
            }

            @Override
            public void onProgress(Message message, int progress) {
                listener.onProgress(message, progress);
            }
        });
    }

    /**
     * 发送图片消息，可以使用该方法将图片上传到自己的服务器发送，同时更新图片状态。
     * 使用该方法在上传图片时，会回调 RongIMClient.SendImageMessageWithUploadListenerCallback
     * 此回调中会携带 RongIMClient.UploadImageStatusListener 对象，
     * 使用者只需要调用其中的 RongIMClient.UploadImageStatusListener.update(int)
     * 更新进度 RongIMClient.UploadImageStatusListener.success(Uri)
     * 更新成功状态，并告知上传成功后的图片地址
     * RongIMClient.UploadImageStatusListener.error() 更新失败状态
     *
     * @param message     发送消息的实体。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。 如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
     *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 PushNotificationMessage.getPushData() 方法获取。
     * @param listener    发送消息的回调，回调中携带 RongIMClient.UploadImageStatusListener 对象，用户调用该对象中的方法更新状态。 sendImageMessage(Message, String, String, SendImageMessageCallback)
     */
    public void sendImageMessageWithUploadImage(Message message, String pushContent, String pushData, final SendImageMessageInteface<Message> listener) {
        rongIMClient.sendImageMessage(message, pushContent, pushData,
                new RongIMClient.SendImageMessageWithUploadListenerCallback() {
                    @Override
                    public void onAttached(Message message, RongIMClient.UploadImageStatusListener uploadImageStatusListener) {
                        listener.onAttached(message);
//                        uploadImageStatusListener.error();
//                        uploadImageStatusListener.success();
//                        uploadImageStatusListener.update();
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        listener.onError(message, errorCode);
                    }

                    @Override
                    public void onSuccess(Message message) {
                        listener.onSuccess(message);
                    }

                    @Override
                    public void onProgress(Message message, int progress) {
                        listener.onProgress(message, progress);
                    }
                });
    }
    //*************** 发送图片消息 end ********************************//

    //*************** 发送位置消息 start ********************************//
    public void sendLocationMessage(Message message, String pushContent, String pushData, final SendMessageInterface<Message> listener) {
        rongIMClient.sendLocationMessage(message, pushContent, pushData, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                listener.onAttached(message);
            }

            @Override
            public void onSuccess(Message message) {
                listener.onSuccess(message);
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                listener.onError(message, errorCode);
            }
        });
    }
    //***************使用者调用此方法更新坐标位置。 1个方法 start ********************************//

    /**
     * 使用者调用此方法更新坐标位置。
     *
     * @param type      位置共享的会话类型。
     * @param targetId  位置共享的会话 targetId。
     * @param latitude  维度
     * @param longitude 经度
     */
    public void updateRealTimeLocationStatus(Conversation.ConversationType type, String targetId, double latitude, double longitude) {
        rongIMClient.updateRealTimeLocationStatus(type, targetId, latitude, longitude);
    }
    //***************使用者调用此方法更新坐标位置。 1个方法 end ********************************//
    //*************** 发送位置消息 end ********************************//


    //*************** 向本地会话中插入一条消息 1个方法 start ********************************//
    //您也可以在本地存储中插入一条消息，但是不向外发送

    /**
     * 向本地会话中插入一条消息。这条消息只是插入本地会话，不会实际发送给服务器和对方。
     * 该消息不一定插入本地数据库，是否入库由消息的属性决定。
     *
     * @param type         会话类型。
     * @param targetId     目标会话Id。比如私人会话时，是对方的id； 群组会话时，是群id; 讨论组会话时，则为该讨论组的id.
     * @param senderUserId 发送用户 Id。如果是模拟本人插入的消息，则该id设置为当前登录用户即可。如果要模拟对方插入消息，则该id需要设置为对方的id.
     * @param content      消息内容。如TextMessage ImageMessage等。
     * @param listener     获得消息发送实体的回调。
     */

    public void insertMessage(Conversation.ConversationType type, String targetId, String senderUserId, MessageContent content, final BaseInterface<Message> listener) {

        rongIMClient.insertMessage(type, targetId, senderUserId, content, new RongIMClient.ResultCallback<Message>() {
            @Override
            public void onSuccess(Message message) {
                listener.onSuccess(message);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError(errorCode);
            }
        });
    }

    //*************** 向本地会话中插入一条消息 end ********************************//


    //*************** 向会话中发送正在输入的状态 1个方法 start ********************************//

    /**
     * 向会话中发送正在输入的状态
     *
     * @param type              会话类型
     * @param targetId          会话id
     * @param typingContentType 正在输入的消息的类型名 typingContentType为用户当前正在编辑的消息类型名，
     *                          即message中getObjectName的返回值。 如文本消息，应该传类型名"RC:TxtMsg"。 目前只支持单聊
     *                          可使用  MessageObjectName.TXT_NAME
     */
    public void sendTypingStatus(Conversation.ConversationType type, String targetId, String typingContentType) {
        rongIMClient.sendTypingStatus(type, targetId, typingContentType);
    }

    /**
     * 根据conversationType和targetId获取当前会话正在输入的用户列表
     *
     * @param type
     * @param targetId
     */
    public Collection<TypingStatus> getTypingUserListFromConversation(Conversation.ConversationType type, String targetId) {
        return rongIMClient.getTypingUserListFromConversation(type, targetId);
    }

    /**
     * 设置输入状态监听器，当输入状态发生变化时，会回调RongIMClient.TypingStatusListener.onTypingStatusChanged(Conversation.ConversationType, String, Collection)
     * 对于单聊而言，当对方正在输入时，监听会触发一次；当对方不处于输入状态时，该监听还会触发一次，但是回调里上来的输入用户列表为空
     *
     * @param listener 未设置
     */
    public void setTypingStatusListener(final SendMessageInterface<Message> listener) {
        RongIMClient.setTypingStatusListener(new RongIMClient.TypingStatusListener() {
            /**
             * 某个会话输入状态发生变化 对于单聊而言，
             * 当对方正在输入时，监听会触发一次；当对方不处于输入状态时，该监听还会触发一次，但是回调里上来的输入用户列表为空
             * @param type 会话类型
             * @param targetId 会话id
             * @param collection 某个会话正在输入的userList
             */
            @Override
            public void onTypingStatusChanged(Conversation.ConversationType type, String targetId, Collection<TypingStatus> collection) {

            }
        });
    }
    //*************** 向会话中发送正在输入的状态 end ********************************//

    /**
     * 消息撤回
     *
     * @param message  将被撤回的消息
     * @param listener onSuccess里回调{@link RecallNotificationMessage}，
     *                 IMLib 已经在数据库里将被撤回的消息用{@link RecallNotificationMessage} 替换，
     *                 用户需要在界面上对{@link RecallNotificationMessage} 进行展示。
     *                 getOperatorId(); 发起撤回消息的用户id
     *                 getRecallTime();撤回的时间（毫秒）
     *                 getOriginalObjectName();原消息的消息类型名
     *                 撤回通知消息，当用户撤回消息或者收到一条撤回信令消息时，需要根据此通知消息在界面上进行展示。
     */

    public void recallMessage(Message message, final RongIMClient.ResultCallback<RecallNotificationMessage> listener) {
        rongIMClient.recallMessage(message, new RongIMClient.ResultCallback<RecallNotificationMessage>() {
            @Override
            public void onSuccess(RecallNotificationMessage recallNotificationMessage) {
                //撤回成功的处理，根据 recallNotificationMessage 的内容进行界面刷新
                listener.onSuccess(recallNotificationMessage);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                //撤回失败的处理
                listener.onError(errorCode);
            }
        });
    }

    /**
     * 设置撤回消息监听器
     *
     * @param listener 撤回消息监听器
     */
    public void setRecallMessageListener(final RongIMClient.RecallMessageListener listener) {
        RongIMClient.setRecallMessageListener(new RongIMClient.RecallMessageListener() {
            @Override
            public void onMessageRecalled(int messageId, RecallNotificationMessage recallNotificationMessage) {
                listener.onMessageRecalled(messageId, recallNotificationMessage);
                //根据 recallNotificationMessage 的内容进行界面刷新
            }
        });
    }

    //*************** 发送某个会话中的消息阅读回执 1个方法 start ********************************//

    /**
     * 发送某个会话中的消息阅读回执
     *
     * @param type      会话类型
     * @param targetId  目标会话ID
     * @param timestamp 该会话中已读的最后一条消息的发送时间戳Message.getSentTime()
     *                  消息回执功能目前只支持单聊,如果使用Lib可以注册监听setReadReceiptListener ,使用kit直接设置rc_config.xml中rc_read_receipt为true
     */
    public void sendReadReceiptMessage(Conversation.ConversationType type, String targetId, long timestamp) {
        rongIMClient.sendReadReceiptMessage(type, targetId, timestamp);
    }

    /**
     * 获取配置，是否显示消息回执
     *
     * @return true: 显示消息回执； false: 不显示消息回执
     */
//    public Boolean getReadReceipt() {
//        return rongIMClient.getReadReceipt();
//    }

    /**
     * 设置消息回执监听器，当收到消息回执时会回调
     * 其中的RongIMClient.ReadReceiptListener.onReadReceiptReceived(io.rong.imlib.model.Message)
     *
     * @param listener
     */
    public void sendReadReceiptMessage(final BaseInterface<Message> listener) {
        RongIMClient.setReadReceiptListener(new RongIMClient.ReadReceiptListener() {

            @Override
            public void onReadReceiptReceived(Message message) {
                //单聊中收到消息回执的回调。
            }

            @Override
            public void onMessageReceiptRequest(Conversation.ConversationType conversationType, String targetId, String messageUId) {
                //群组和讨论组中，某人发起了回执请求，会话中其余人会收到该请求，并回调此方法。
            }

            @Override
            public void onMessageReceiptResponse(Conversation.ConversationType conversationType, String targetId, String messageUId, HashMap<String, Long> respondUserIdList) {
                //在群组和讨论组中发起了回执请求的用户，当收到接收方的响应时，会回调此方法。
            }

//            @Override
//            public void onReadReceiptReceived(Message message) {
//                //封装了一个ReadReceiptMessage   已读通知消息
//                // if (mConversation != null && mConversation.getTargetId().equals(message.getTargetId()) && mConversation.getConversationType() == message.getConversationType()) {
////                ReadReceiptMessage content = (ReadReceiptMessage) message.getContent();
////                long ntfTime = content.getLastMessageSendTime();
//                //自行进行UI处理，把会话中发送时间戳之前的所有已发送消息状态置为已读
//                listener.onSuccess(message);
//            }
        });
    }

    //*************** 发送某个会话中的消息阅读回执 1个方法 end ********************************//


    //*************** 收消息的监听方法 1个方法 end ********************************//

    /**
     * 设置接收消息事件的监听器。此为异步线程
     *
     * @param message 收到的消息实体。
     * @param left    剩余未拉取消息数目。
     * @return 收到消息是否处理完成。
     */
    @Override
    public boolean onReceived(Message message, int left) {
        if (mOnMessageReportCallback != null) {
            mOnMessageReportCallback.onPushMessage(message);
        }
        return false;
    }

    //*************** 更新数据库，把指定会话里所有发送时间Message.sentTime早于timestamp的消息置为已读 1个方法 start ********************************//

    /**
     * 更新数据库，把指定会话里所有发送时间Message.sentTime早于timestamp的消息置为已读
     *
     * @param type      会话类型
     * @param targetId  会话id
     * @param timestamp 时间戳
     * @param listener  操作成功回调onSuccess，失败回调onFail
     */
//    public void updateMessageReceiptStatus(Conversation.ConversationType type, String targetId, long timestamp, final BaseInterface listener) {
//
//        rongIMClient.updateMessageReceiptStatus(type, targetId, timestamp, new RongIMClient.OperationCallback() {
//            @Override
//            public void onSuccess() {
//                listener.onSuccess(null);
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                listener.onError(errorCode);
//            }
//        });
//    }

    //*************** 更新数据库，把指定会话里所有发送时间Message.sentTime早于timestamp的消息置为已读 1个方法 end ********************************//


    /**
     * 下载文件。 用来获取媒体原文件时调用。如果本地缓存中包含此文件，则从本地缓存中直接获取，否则将从服务器端下载。
     *
     * @param type      会话类型。
     * @param targetId  目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
     * @param mediaType 文件类型。 AUDIO 声音。    FILE  通用文件。    IMAGE 图片。    VIDEO  视频。
     * @param imageUrl  文件的 URL 地址。
     * @param listener  下载文件的回调。
     */
    public void downloadMedia(Conversation.ConversationType type, String targetId, RongIMClient.MediaType mediaType, String imageUrl, final SendImageMessageInteface listener) {
        rongIMClient.downloadMedia(type, targetId, mediaType, imageUrl, new RongIMClient.DownloadMediaCallback() {
            @Override
            public void onProgress(int progress) {
                listener.onProgress("", progress);
            }

            @Override
            public void onSuccess(String s) {
                listener.onSuccess(s);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                listener.onError("", errorCode);
            }
        });
    }

    //****************登出2个方法  的两种方式主要区别是否在退出后是否接受push消息 start********************************//

    /**
     * 断开与融云服务器的连接，并且不再接收 Push 消息。
     * 若想断开连接后仍然接受 Push 消息，可以调用 disconnect()
     */
    public void logout() {
        rongIMClient.logout();
    }

    /**
     * 断开与融云服务器的连接。当调用此接口断开连接后，仍然可以接收 Push 消息。
     * 若想断开连接后不接受 Push 消息，可以调用logout()
     */
    public void disconnect() {
        rongIMClient.disconnect();
    }

    //****************登出 end ********************************//


    //    /******************定义的接口 通用接口使用了泛型***************************/
    public interface BaseInterface<T> {
        void onError(RongIMClient.ErrorCode errorCode);

        void onSuccess(T t);
    }

    //发送状态的回调 网络发送回调
    public interface SendMessageCallBack<T> {
        void sendSuccess(T t);

        void sendFail(T t, RongIMClient.ErrorCode errorCode);
    }

    //发送状态回调  本地加网络
    public interface SendMessageInterface<T> {
        //消息发送失败。
        void onError(T t, RongIMClient.ErrorCode errorCode);

        //消息发送成功。
        void onSuccess(T t);

        //消息已存储数据库
        void onAttached(T t);
    }

    //发送图片消息的回调
    public interface SendImageMessageInteface<T> extends SendMessageInterface {
        void onProgress(T t, int progress);
    }

    /**
     * 消息发送报告
     */
    private OnMessageReportCallback mOnMessageReportCallback;

    /**
     * 对外提供收消息的的监听
     *
     * @param callback
     */
    public static void setOnMessageReportCallback(
            //设置监听器
            OnMessageReportCallback callback) {
        getInstance().mOnMessageReportCallback = callback;
    }

    //收消息的接口
    public interface OnMessageReportCallback {
        //void onMessageReport(ECError error, ECMessage message);

        /**
         * 收到消息 都要通过此方法 前提是设置了监听器  注意此方法好像是异步线程
         *
         * @param message 收到的消息
         */
        void onPushMessage(Message message);
    }


    public class MessageObjectName {
        public String TXT_NAME = "RC:TxtMsg"; //文本类消息
        public String IMG_NAME = "RC:ImgMsg";//图片类消息
        public String VC_NAME = "RC:VcMsg";   //语音类消息
        public String LBS_NAME = "RC:LBSMsg";  //位置类消息
        public String IMG_TEXT_NAME = "RC:ImgTextMsg"; //文本加图片类消息 富媒体消息
    }
}
