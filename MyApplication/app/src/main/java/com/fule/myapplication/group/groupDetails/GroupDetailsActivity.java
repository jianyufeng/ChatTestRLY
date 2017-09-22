package com.fule.myapplication.group.groupDetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fule.myapplication.R;
import com.fule.myapplication.common.ToastUtil;
import com.fule.myapplication.common.superActivity.ECSuperActivity;
import com.fule.myapplication.group.groupDetails.bean.GroupMember;
import com.fule.myapplication.group.memberDetails.GroupMemberDetailsActivity;
import com.fule.myapplication.group.utilis.test2.ExpandRecyclerView;
import com.fule.myapplication.group.utilis.test2.RecycleViewBaseAdapter;
import com.fule.myapplication.group.utilis.test2.RecycleViewBaseHolder;
import com.fule.myapplication.group.utilis.test2.RecyclerVieweItemClickListener;

import java.util.ArrayList;

/**
 * 作者:Created by 简玉锋 on 2016/11/25 15:56
 * 邮箱: jianyufeng@38.hn
 * 群组详情
 */

public class GroupDetailsActivity extends ECSuperActivity implements View.OnClickListener {
    private Context mContext;
    //群组成员
    private ExpandRecyclerView membersContainer;
    //群图像
    private GroupDetailsSettingItem groupIconItem;
    //群组名称
    private GroupDetailsSettingItem groupNameItem;
    //群组公告
    private GroupDetailsSettingItem groupDeclare;
    //群组权限
    private GroupDetailsSettingItem groupPermission;
    //群组免打扰
    private GroupDetailsSettingItem groupMsgNotice;
    //群组历史消息清除
    private GroupDetailsSettingItem groupClearMsg;
    //群组成员适配器
    private RecycleViewBaseAdapter<GroupMember, RecycleViewBaseHolder> groupMemberAdapter;
    //群组成员
    private ArrayList<GroupMember> groupMembers;

    private Button groupDissolve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //设置导航栏内容
        getTopBarView().setTopBarToStatus(1, R.drawable.topbar_back_bt, null, getString(R.string.group_details_title), this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.group_details;
    }

    @Override
    protected void initData() {
        groupMembers = new ArrayList<>();
        //创建数据
        for (int i = 0; i < 59; i++) {
            GroupMember groupMember = new GroupMember();
            groupMember.setIcon(R.drawable.group_default_icon);
            groupMember.setName("grpMeb" + i);
            groupMembers.add(groupMember);
        }

    }

    @Override
    protected void initView() {
        membersContainer = (ExpandRecyclerView) findViewById(R.id.group_members);
        groupIconItem = (GroupDetailsSettingItem) findViewById(R.id.group_icon);
        groupIconItem.setOnClickListener(this); //设置群组图像的点击事件
        groupNameItem = (GroupDetailsSettingItem) findViewById(R.id.group_name);
        groupNameItem.setOnClickListener(this);//设置群组名称的点击事件
        groupDeclare = (GroupDetailsSettingItem) findViewById(R.id.group_declare);
        groupDeclare.setOnClickListener(this);//设置群组公告的点击事件
        groupPermission = (GroupDetailsSettingItem) findViewById(R.id.group_permission);
        groupPermission.setOnClickListener(this);//设置群组权限的点击事件
        groupMsgNotice = (GroupDetailsSettingItem) findViewById(R.id.group_msg_notice);
        //设置群组消息通知的点击事件
        groupMsgNotice.getCheckedTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupMsgNotice.toggle();
            }
        });
        groupClearMsg = (GroupDetailsSettingItem) findViewById(R.id.group_clean_msg);
        groupClearMsg.setOnClickListener(this); //设置群组历史消息的点击事件
        //群组解散按钮
        groupDissolve = (Button) findViewById(R.id.group_dissolve);
        groupDissolve.setOnClickListener(this); //设置群解散点击事件
    }

    @Override
    protected void asynData() {
        //异步数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        groupIconItem.getRightImage().setImageResource(R.mipmap.ic_launcher);
                        groupNameItem.setRightText("群组111");
                        groupDeclare.setShowNextPage(false);
                        groupDeclare.setRightText("fsdsdfsdfsd");
                        if (groupMemberAdapter != null) {
                            //刷新数据
                            groupMemberAdapter.fillList(groupMembers);
                            //设置群组数量
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    protected void logicControlView() {
        //设置布局管理器
        GridLayoutManager manager = new GridLayoutManager(this, 5);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        membersContainer.setLayoutManager(manager);
        membersContainer.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {;
                    outRect.top = (int) getResources().getDimension(R.dimen.android_width_18);
            }
        });
        //设置适配器
        groupMemberAdapter = new RecycleViewBaseAdapter<GroupMember, RecycleViewBaseHolder>(this) {
            @Override
            public RecycleViewBaseHolder createCustomViewHolder(ViewGroup parent, int viewType) {
                //创建布局
                return new RecycleViewBaseHolder(parent, R.layout.group_details_group_member_item);
            }

            @Override
            public void bindCustomViewHolder(RecycleViewBaseHolder holder, int position) {
                //绑定数据
                GroupMember item = getItem(position);
                if (item == null) {
                    return;
                }
                holder.setText(R.id.group_member_name, item.getName());
                holder.setImageResource(R.id.group_member_icon, item.getIcon());
            }
        };
        //设置适配器
        membersContainer.setAdapter(groupMemberAdapter);
        //设置成员的点击事件
        membersContainer.addOnItemTouchListener(new RecyclerVieweItemClickListener(membersContainer, new RecyclerVieweItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //获取成员 跳转到群组成员的详情
                GroupMember item = groupMemberAdapter.getItem(position);
                if (item == null) {  //点击的可能是 头部或底部视图
                    return;
                }
              //跳转到群组成员的详
                startActivity(new Intent(mContext, GroupMemberDetailsActivity.class));

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onClick(View v) {
        ToastUtil.showMessage("fdffsdfds");
        switch (v.getId()) {
            case R.id.btn_left: //回退按钮事件
                hideSoftKeyboard();
                finish();
                break;
            case R.id.group_icon: //跳转到群组图像设置界面

                break;
            case R.id.group_name: //跳转到群组名称设置界面

                break;
            case R.id.group_declare: //跳转到群组公告设置界面

                break;
            case R.id.group_permission: //跳转到群组权限设置界面

                break;
            case R.id.group_clean_msg: //跳转到群组名称设置界面

                break;
            case R.id.group_dissolve: //群组解散逻辑处理

                break;
        }
    }
}
