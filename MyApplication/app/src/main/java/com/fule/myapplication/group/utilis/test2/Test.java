package com.fule.myapplication.group.utilis.test2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fule.myapplication.R;

import java.util.ArrayList;

/**
 * Created by 简玉锋 on 2016/11/16.
 */

public class Test extends AppCompatActivity {



    //群组列表的容器
    private ExpandRecyclerView groupsContainer;
    private RecycleRefreshAndLoadLayout refreshLayout;
    private View emptyView;

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
    RecycleViewBaseAdapter<String, RecycleViewBaseHolder> adapter;
    private void logicControlView() {
        groupsContainer.setEmptyView(emptyView);
         adapter = new RecycleViewBaseAdapter<String, RecycleViewBaseHolder>(this) {
            @Override
            public RecycleViewBaseHolder createCustomViewHolder(ViewGroup parent, int viewType) {
                return new RecycleViewBaseHolder(parent,R.layout.chatting_item_from);
            }

             @Override
             public void bindCustomViewHolder(RecycleViewBaseHolder holder, int position) {
                holder.setText(R.id.chatting_content_itv,getItem(holder));
             }
         };
//        View inflate = LayoutInflater.from(this).inflate(R.layout.chat_footer_view, null);
//        adapter.addHeaderView(inflate);
//        adapter.addFooterView(LayoutInflater.from(this).inflate(R.layout.chat_footer_view,null));
        groupsContainer.setAdapter(adapter);
//        final GridLayoutManager manager = new GridLayoutManager(this, 2);
//        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (position==0){
//
//                    return 2;
//                }
//                return 1;
//            }
//        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5000);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            adapter.removeHeaderView();
//                        }
//                    });
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        groupsContainer.setLayoutManager(manager);

        groupsContainer.addOnItemTouchListener(new RecyclerVieweItemClickListener(groupsContainer, new RecyclerVieweItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(view.getContext(), "item"+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                String item = adapter.getItem(position);
                if (item==null){
                    adapter.appendItem("见见");
                    Toast.makeText(view.getContext(), "null"+position, Toast.LENGTH_SHORT).show();
                    return;
                }
                adapter.removeItem(position );
                Toast.makeText(view.getContext(), "onItemLongClick item"+position, Toast.LENGTH_SHORT).show();
            }
        }));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList datas = new ArrayList();
                for (int i = datas.size(); i < 10; i++) {
                    datas.add("refresh item"+i);
                }
                adapter.fillList(datas);
                refreshLayout.setRefreshing(false);
                refreshLayout.setLoading(false);
            }
        });
        refreshLayout.setOnLoadListener(new RecycleRefreshAndLoadLayout.OnLoadListener() {

            @Override
            public void onLoad() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    refreshLayout.setLoading(false);
                                    refreshLayout.setRefreshing(false);

//                                    ArrayList<String> strings = new ArrayList<>();
//                                    for (int i = 0; i < 10; i++) {
//                                        strings.add("onLoad item"+i);
//                                    }
                                    adapter.cleanAllDatas();
                                }
                            });

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
//                datas.clear();
//                for (int i = datas.size(); i < 400; i++) {
//                    datas.add(datas.size(),"load item"+i);
//                }
//                adapter.appendList(datas);
//                refreshLayout.setRefreshing(false);
//                refreshLayout.setLoading(false);
//                Toast.makeText(getApplicationContext(),"到底了",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void asynData() {

    }

    private void initData() {

    }

    private void initView() {
        refreshLayout= (RecycleRefreshAndLoadLayout) findViewById(R.id.recycle_container);
        refreshLayout.setColorSchemeColors(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        groupsContainer = (ExpandRecyclerView) findViewById(R.id.recycle_view_id);
        emptyView=findViewById(R.id.id_empty_view);
        refreshLayout.setRefreshing(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(false);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
