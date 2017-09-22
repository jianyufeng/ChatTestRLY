package com.fule.myapplication.group.searchGroup;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fule.myapplication.R;
import com.fule.myapplication.common.ToastUtil;
import com.fule.myapplication.common.activity.until.ActivityTransition;
import com.fule.myapplication.common.superActivity.ECSuperActivity;
import com.fule.myapplication.group.Group;
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
@ActivityTransition(2)
public class BaseSearchActivity extends ECSuperActivity implements TextWatcher,View.OnClickListener{

    //标题
    public static final String EXTRA_EDIT_TITLE = "edit_title";
    // hint值
    public static final String EXTRA_EDIT_HINT = "edit_hint";

    private Context context;
    //群组列表的容器
    private ExpandRecyclerView groupListContainer;
    //空view
    private View emptyView;
    //适配器
    private RecycleViewBaseAdapter<Group, RecycleViewBaseHolder> adapter;
    //群组数据
    private ArrayList<Group> groups;
    //输入的内容
    private EditText inputContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }
    String hint;
    @Override
    protected int getLayoutId() {
        return R.layout.group_list_search;
    }

    @Override
    public void initData() {
        String title = getIntent().getStringExtra(EXTRA_EDIT_TITLE);
        hint = getIntent().getStringExtra(EXTRA_EDIT_HINT);
        getTopBarView().setTopBarToStatus(1, R.drawable.topbar_back_bt, null, title, this);



        groups = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
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
        inputContent = (EditText) findViewById(R.id.input_content);
        inputContent.addTextChangedListener(this);
        inputContent.setHint(hint);
    }

    @Override
    public void asynData() {
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

                        if (adapter != null) {
                            //刷新数据
                            adapter.fillList(groups);
                            //设置群组数量
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void logicControlView() {
        //设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        groupListContainer.setLayoutManager(manager);
        //设置空View
        groupListContainer.setEmptyView(emptyView);
        //设置分割线
        groupListContainer.addItemDecoration(new RecycleViewLinearDivider(BaseSearchActivity.this, LinearLayoutManager.VERTICAL));      //创建群组列表的适配器
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
        groupListContainer.addOnItemTouchListener(new RecyclerVieweItemClickListener(groupListContainer, new RecyclerVieweItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //获取群组  跳转到群组聊天界面
                Group item = adapter.getItem(position);
                if (item == null) {  //点击的可能是 头部或底部视图
                    return;
                }

//                    startActivity(new Intent(context,Cha));
                ToastUtil.showMessage(item.getGroupName());

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String keyWords = s.toString().trim();
        if (TextUtils.isEmpty(keyWords)) return;
        ToastUtil.showMessage(keyWords + "");

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                hideSoftKeyboard();
                finish();
                break;
        }
    }
}
