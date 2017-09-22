package com.fule.myapplication.group.grouplist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fule.myapplication.R;
import com.fule.myapplication.common.ToastUtil;
import com.fule.myapplication.common.superActivity.ECSuperActivity;
import com.fule.myapplication.group.Group;
import com.fule.myapplication.group.createGroup.CreateGroupActivity;
import com.fule.myapplication.group.searchGroup.BaseSearchActivity;
import com.fule.myapplication.group.utilis.test2.ExpandRecyclerView;
import com.fule.myapplication.group.utilis.test2.RecycleViewBaseAdapter;
import com.fule.myapplication.group.utilis.test2.RecycleViewBaseHolder;
import com.fule.myapplication.group.utilis.test2.RecycleViewLinearDivider;
import com.fule.myapplication.group.utilis.test2.RecyclerVieweItemClickListener;

import java.util.ArrayList;


/**
 * Created by 简玉锋 on 2016/11/19.
 * 群组列表   显示所加入的群组
 */

public class GroupListActivity extends ECSuperActivity implements View.OnClickListener {
    //群组列表的容器
    private ExpandRecyclerView groupListContainer;
    //空view
    private View emptyView;
    //适配器
    private RecycleViewBaseAdapter<Group, RecycleViewBaseHolder> adapter;
    //群组数据
    private ArrayList<Group> groups;
    //群组个数
    private TextView groupNumber;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        getTopBarView().setTopBarToStatus(1, R.drawable.topbar_back_bt,R.drawable.head_right_save_selector, null, getString(R.string.group_list_create_group),getString(R.string.group_list_title), null, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_recycleview_layout;
    }

    @Override
    public void initData() {
        groups = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Group group = new Group();
            group.setIcon(R.mipmap.ic_launcher);
            group.setGroupName("群组名称" + i);
            groups.add(group);
        }
    }

    @Override
    public void initView() {
        groupListContainer = (ExpandRecyclerView) findViewById(R.id.recycle_view_id);
        emptyView = findViewById(R.id.id_empty_view);
    }

    @Override
    public void asynData() {
        //异步数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (adapter != null) {
                            //刷新数据
                            adapter.fillList(groups);
                            //设置群组数量
                            groupNumber.setText(getString(R.string.group_number, String.valueOf(adapter.getItemCount() - adapter.getExtraViewCount())));
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void logicControlView() {
        //设置布局管理器
//        LinearLayoutManager manager = new LinearLayoutManager(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        groupListContainer.setLayoutManager(manager);
        //设置空View
        groupListContainer.setEmptyView(emptyView);
        //设置分割线
        groupListContainer.addItemDecoration(new RecycleViewLinearDivider(GroupListActivity.this, LinearLayoutManager.VERTICAL));      //创建群组列表的适配器
        //创建群组列表的适配器
        adapter = new RecycleViewBaseAdapter<Group, RecycleViewBaseHolder>(this) {
            @Override
            public RecycleViewBaseHolder createCustomViewHolder(ViewGroup parent, int viewType) {
                return new RecycleViewBaseHolder(parent, R.layout.group_list_item);
            }

            @Override
            public void bindCustomViewHolder(RecycleViewBaseHolder holder, int position) {
                Group item = getItem(position);
                if (item == null) {
                    return;
                }
                holder.setText(R.id.group_name, item.getGroupName());
                holder.setImageResource(R.id.group_icon, item.getIcon());
            }
        };
        //设置适配器
        groupListContainer.setAdapter(adapter);
        //添加头部搜索视图
        adapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.searchlayout, groupListContainer, false));
        View footView = LayoutInflater.from(this).inflate(R.layout.footer_number_layout, groupListContainer, false);
        groupNumber = (TextView) footView.findViewById(R.id.group_number);
        //添加底部群组数量视图
        adapter.addFooterView(footView);
        groupNumber.setText(getString(R.string.group_number, String.valueOf(adapter.getItemCount() - adapter.getExtraViewCount())));
        groupListContainer.addOnItemTouchListener(new RecyclerVieweItemClickListener(groupListContainer, new RecyclerVieweItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //跳转到搜索界面   点击的是头部视图
                if (position == 0) {
                    Intent intent = new Intent(GroupListActivity.this, BaseSearchActivity.class);
                    intent.putExtra(BaseSearchActivity.EXTRA_EDIT_TITLE, getString(R.string.group_list_search_edit_title));
                    intent.putExtra(BaseSearchActivity.EXTRA_EDIT_HINT, getString(R.string.group_list_search_edit_hint));
                    startActivity(intent);
                    return;
                }

                //获取群组  跳转到群组聊天界面
                Group item = adapter.getItem(position);
                if (item == null) { //点击的可能是 头部或底部视图
                    return;
                }
                ToastUtil.showMessage(item.getGroupName());
//                    startActivity(new Intent(context,Cha));

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                hideSoftKeyboard();
                finish();
                break;
            case R.id.text_right:       //跳转到创建群组界面
                Intent intent = new Intent(GroupListActivity.this, CreateGroupActivity.class);
                GroupListActivity.this.startActivity(intent);
                break;

        }
    }

}
