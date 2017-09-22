package com.fule.myapplication.group.utilis.pulltorefresh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fule.myapplication.R;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 简玉锋 on 2016/11/16.
 * /*
 * Mode.BOTH：同时支持上拉下拉
 * Mode.PULL_FROM_START：只支持下拉Pulling Down
 * Mode.PULL_FROM_END：只支持上拉Pulling Up
 * 如果Mode设置成Mode.BOTH，需要设置刷新Listener为OnRefreshListener2，并实现onPullDownToRefresh()、onPullUpToRefresh()两个方法。
 * 如果Mode设置成Mode.PULL_FROM_START或Mode.PULL_FROM_END，需要设置刷新Listener为OnRefreshListener，同时实现onRefresh()方法。
 * 当然也可以设置为OnRefreshListener2，但是Mode.PULL_FROM_START的时候只调用onPullDownToRefresh()方法，
 * Mode.PULL_FROM的时候只调用onPullUpToRefresh()方法.
 * <p>
 * setOnRefreshListener(OnRefreshListener listener):设置刷新监听器；
 * setOnLastItemVisibleListener(OnLastItemVisibleListener listener):设置是否到底部监听器；
 * setOnPullEventListener(OnPullEventListener listener);设置事件监听器；
 * onRefreshComplete()：设置刷新完成
 * <p>
 * <p>
 * * pulltorefresh.setOnScrollListener()
 * SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
 * SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
 * SCROLL_STATE_IDLE(0) 停止滚动
 * <p>
 * * setOnLastItemVisibleListener
 * 当用户拉到底时调用
 * <p>
 * * setOnTouchListener是监控从点下鼠标 （可能拖动鼠标）到放开鼠标（鼠标可以换成手指）的整个过程 ，他的回调函数是onTouchEvent（MotionEvent event）,
 * 然后通过判断event.getAction()是MotionEvent.ACTION_UP还是ACTION_DOWN还是ACTION_MOVE分别作不同行为。
 * setOnClickListener的监控时间只监控到手指ACTION_DOWN时发生的行为
 */

public class GroupListActivityTest extends AppCompatActivity {

    private List<String> datas;

    //群组列表的容器
    private PullToRefreshListView groupsContainer;
    private AbsAdapter<String> adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_list_activity_test);
        //1  初始化数据  获取本地数据 或者获取缓存数据
        initData();
        //2  初始化控件  查找控件
        initView();
        //3  异步获取数据
        asynData();

        //4  设置控件对应的逻辑  如设置适配器 设置事件监听  控件的隐藏及显示
        logicControlView();


    }

    private void logicControlView() {
        /**
         * 设置下拉刷新的listview的动作
         */
        groupsContainer.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //设置下拉时显示的日期和时间
                String lable = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE |
                                DateUtils.FORMAT_ABBREV_ALL);
                // 更新显示的label
                ILoadingLayout loadingLayoutProxy = refreshView.getLoadingLayoutProxy(true,false);

                //下拉时，显示的主要文字，一般的情况下，我们不修改它
                loadingLayoutProxy.setReleaseLabel("下拉刷新");
                //下拉时，第二行显示的文字
                loadingLayoutProxy.setLastUpdatedLabel("最近更新:" + lable);
                //数据刷新的时候，显示的文字
                loadingLayoutProxy.setRefreshingLabel("正在刷新...");
                //刷新结束后，显示的文字，没有什么意义，一间而过
                loadingLayoutProxy.setPullLabel("@刷新成功");
                // 更新显示的label
                ILoadingLayout loadingLayoutProxy1 = refreshView.getLoadingLayoutProxy(false,true);
                //下拉时，显示的主要文字，一般的情况下，我dfs们不修改它
                loadingLayoutProxy1.setReleaseLabel("上拉刷新");
                //下拉时，第二行显示的文字
                loadingLayoutProxy1.setLastUpdatedLabel("最近更新dff:" + lable);
                //数据刷新的时候，显示的文字
                loadingLayoutProxy1.setRefreshingLabel("正在刷新？？？");
                //刷新结束后，显示的文字，没有什么意义，一间而过
                loadingLayoutProxy1.setPullLabel("刷新成功");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initData();
                                groupsContainer.onRefreshComplete();
                                adapter.refreshData(datas);
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//设置下拉时显示的日期和时间
                String lable = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE |
                                DateUtils.FORMAT_ABBREV_ALL);
                // 更新显示的label
                ILoadingLayout loadingLayoutProxy = groupsContainer.getLoadingLayoutProxy();
                loadingLayoutProxy.setLastUpdatedLabel(lable);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                datas.clear();
                                // 加载完成后停止刷新
                                groupsContainer.onRefreshComplete();
                                // 通知数据改变了
                                adapter.refreshData(datas);
                            }
                        });
                    }
                }).start();
            }
        });
        // 添加滑动到底部的监听器
        groupsContainer.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                Toast.makeText(getApplication(), "已经到底了", Toast.LENGTH_SHORT).show();
            }
        });

        // groupsContainer.isScrollingWhileRefreshingEnabled();//看刷新时是否允许滑动
        groupsContainer.setScrollingWhileRefreshingEnabled(true);  //在刷新时允许继续滑动
        //mPullRefreshListView.getMode();//得到模式
        //上下都可以刷新的模式。这里有两个选择：Mode.PULL_FROM_START，Mode.BOTH，PULL_FROM_END
/**
 * 设置反馈音效
 */
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<>(this);
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        groupsContainer.setOnPullEventListener(soundListener);
        groupsContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int headerViewsCount = groupsContainer.getRefreshableView().getHeaderViewsCount();
                int footViewsCount = groupsContainer.getRefreshableView().getFooterViewsCount();
                Toast.makeText(getApplication(), headerViewsCount + "已经到底了" + position + ":::" + footViewsCount, Toast.LENGTH_SHORT).show();
            }
        });
        adapter = new AbsAdapter<String>(this, datas, R.layout.chatting_item_from) {
            @Override
            public void showItemContenData(ViewHolder viewHolder, String data, ViewGroup parent, int position) {
                viewHolder.setTextViewContent(R.id.chatting_content_itv, data);
            }
        };
        ListView refreshableView = groupsContainer.getRefreshableView();
        View mEmptyView = findViewById(R.id.empty_conversation_tv);
        groupsContainer.setEmptyView(mEmptyView);
        refreshableView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplication(), "已经到dsfdsfsd底了" + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        refreshableView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.chat_footer_view, null));
        refreshableView.addFooterView(LayoutInflater.from(this).inflate(R.layout.chat_footer_view, null));
//        refreshableView.addFooterView(mEmptyView);
        refreshableView.setAdapter(adapter);


    }

    private void asynData() {

    }

    private void initData() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        for (int i = 0; i < 50; i++) {
            datas.add("item" + i);
        }

    }

    private void initView() {
        groupsContainer = (PullToRefreshListView) findViewById(R.id.userDefinedList_pull_refresh_list);
    }
}
