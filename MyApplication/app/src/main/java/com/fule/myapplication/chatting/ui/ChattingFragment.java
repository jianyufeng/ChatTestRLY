package com.fule.myapplication.chatting.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fule.myapplication.R;
import com.fule.myapplication.chatting.adapter.ChatListViewAdapter;
import com.fule.myapplication.common.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * Created by Administrator on 2016/8/4.
 */
public class ChattingFragment extends Fragment implements AbsListView.OnScrollListener {
    private static final String TAG = "ChattingFragment";
    /**
     * 联系人账号  收件人
     */
    public final static String RECIPIENTS = "recipients";
    /**
     * 联系人名称  收件人的昵称
     */
    public final static String CONTACT_USER = "contact_user";
    /**
     * 依赖的Activity
     */
    public final static String FROM_CHATTING_ACTIVITY = "from_chatting_activity";
    /**
     * 聊天记录的View
     */
    ListView mListView;

    /**
     * 会话联系人账号
     */
    private String mRecipients;
    /**
     * 联系人名称
     */
    private String mUsername;
    /**
     * 聊天适配器
     */
    private ChatListViewAdapter mChattingAdapter;
    /**
     * 消息数据
     */
    private ArrayList<Message> datas;


    /********
     * 使用监听器属性  start
     ******************/
    //listView消息的长按事件 包括文本的复制，及删除
    private AdapterView.OnItemLongClickListener mOnItemLongClickListener
            = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            final int itemPosition = position;
            if (mChattingAdapter != null) {
                int headerViewsCount = mListView.getHeaderViewsCount();
                if (itemPosition < headerViewsCount) {
                    return false;
                }
                int _position = itemPosition - headerViewsCount;

                if (mChattingAdapter == null || mChattingAdapter.getItem(_position) == null) {
                    return false;
                }
                Message item = mChattingAdapter.getItem(_position);
                String title = mUsername;
                if (item.getMessageDirection() == Message.MessageDirection.SEND) {
                    title = "send";
//                    title = CCPAppManager.getClientUser().getUserName();
                }
                AlertDialog dialog;
                if (item.getContent() instanceof TextMessage) {
                    // 文本有复制功能
//                    dialog = new AlertDialog(getActivity() , R.array.chat_menu);
                } else {
//                    dialog = new ECListDialog(getActivity() , new String[]{getString(R.string.menu_del)});
                }
//                dialog.setOnDialogItemClickListener(new ECListDialog.OnDialogItemClickListener() {
//                    @Override
//                    public void onDialogItemClick(Dialog d, int position) {
//                        handleContentMenuClick(itemPosition ,position);
//                    }
//                });
//                dialog.setTitle(title);
//                dialog.show();
                return true;
            }
            return false;
        }
    };

    /********
     * 使用监听器属性  end
     **************************/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化联系人信息
        initActivityState(savedInstanceState);
        //初始化数据
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatting, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 初始化界面资源
        initView(view);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(v);
            }

        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 读取聊天界面联系人会话参数信息
     *
     * @param savedInstanceState
     */
    private void initActivityState(Bundle savedInstanceState) {
        //获取参数
        Intent intent = getActivity().getIntent();
        //获取联系人的账号
        mRecipients = intent.getStringExtra(RECIPIENTS);
        //获取联系人的昵称
        mUsername = intent.getStringExtra(CONTACT_USER);
        //如果联系人账号为空，退出聊天界面
        if (TextUtils.isEmpty(mRecipients)) {
            ToastUtil.showMessage("联系人账号不存在");
            getActivity().finish();
            return;
        }
        //如果联系人没有昵称，则显示账号
        if (TextUtils.isEmpty(mUsername)) {
            mUsername = mRecipients;
        }
        //初始化数据
        datas = new ArrayList<>();
        mChattingAdapter = new ChatListViewAdapter(getActivity(), datas);
    }

    /**
     * 初始化数据源
     */
    private void initData() {

        RongIMClient.getInstance().getHistoryMessages(Conversation.ConversationType.PRIVATE, mRecipients, "RC:TxtMsg", -1, 1, new RongIMClient.ResultCallback<List<Message>>() {

            @Override
            public void onSuccess(List<Message> messages) {
                if (messages != null) {
                    // mChattingAdapter.addDatas(messages);
                }
                Log.i(TAG, "onSuccess: " + messages);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i(TAG, "onError: " + errorCode);
            }
        });
//        List<Message> historyMessages = RongIMClient.getInstance().getHistoryMessages(Conversation.ConversationType.PRIVATE, mRecipients, -1, 10);

//        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
//            @Override
//            public void onSuccess(List<Conversation> conversations) {
//                Log.i(TAG, "onSuccess: " + conversations);
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                Log.i(TAG, "onError: " + errorCode);
//            }
//        });
        RongIMClient.getInstance().getLatestMessages(Conversation.ConversationType.PRIVATE, mRecipients, 100, new RongIMClient.ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                Log.i(TAG, "onSuccess: " + messages);
                //mChattingAdapter.addDatas(messages);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i(TAG, "onError: " + errorCode);

            }
        });
        RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(final Message message, int i) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChattingAdapter.addData(message);
                    }
                });
                return false;
            }
        });
//
        RongIMClient.getInstance().getHistoryMessages(Conversation.ConversationType.PRIVATE, mRecipients, "RC:VcMsg", -1, 100, new RongIMClient.ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                Log.i(TAG, "onSuccess: " + messages);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i(TAG, "onError: " + errorCode);
            }
        });

    }

    public void send(View view) {
        /** @param conversationType      会话类型
         * @param targetId              会话ID
         * @param content               消息的内容，一般是MessageContent的子类对象
         * @param pushContent           接收方离线时需要显示的push消息内容
         * @param pushData              接收方离线时需要在push消息中携带的非显示内容
         * @param SendMessageCallback   发送消息的回调
         * @param ResultCallback        消息存库的回调，可用于获取消息实体
         *
         */
        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, "jianyufeng1", TextMessage.obtain("我是消息内容"), null, null,
                new RongIMClient.SendMessageCallback() {
                    @Override
                    public void onError(Integer messageId, RongIMClient.ErrorCode e) {
                        Log.i(TAG, "onError: " + e);
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.i(TAG, "onSuccess: " + integer);
                    }

                }, new RongIMClient.ResultCallback<Message>() {
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        Log.d(TAG, "onError: " + errorCode);
                    }

                    @Override
                    public void onSuccess(Message message) {
                        Log.i(TAG, "onSuccess: " + message);
                        mChattingAdapter.addData(message);
                    }

                });
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.chatting_history_lv);
        mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        mListView.setItemsCanFocus(false);
        mListView.setOnScrollListener(this);
        mListView.setKeepScreenOn(false);
        mListView.setStackFromBottom(false);
        mListView.setFocusable(false);
        mListView.setFocusableInTouchMode(false);
        mListView.setOnItemLongClickListener(mOnItemLongClickListener);
//        registerForContextMenu(mListView);  暂不使用
        //设置触摸事件 隐藏键盘
        mListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                hideSoftKeyboard(); //隐藏键盘
//                if (mChattingFooter != null) {//隐藏底部视图
                // After the input method you can use the record button.
                // mGroudChatRecdBtn.setEnabled(true);
                // mChatFooter.setMode(1);
//                    mChattingFooter.hideBottomPanel();
//                }
                return false;
            }
        });

        mListView.setAdapter(mChattingAdapter);
    }

    /*ListView的滑动方法start*/
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override  //控制查看历史数据
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
    /*ListView的滑动方法end*/
}
