package com.fule.myapplication.group.createGroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fule.myapplication.R;
import com.fule.myapplication.common.activity.until.ActivityTransition;
import com.fule.myapplication.common.superActivity.ECSuperActivity;
import com.fule.myapplication.common.superActivity.ui.overFlorw.SelectPicture;
import com.fule.myapplication.group.Group;
import com.fule.myapplication.group.groupDetails.GroupDetailsActivity;

import static com.fule.myapplication.R.array.select_pic;

/**
 * 作者:Created by 简玉锋 on 2016/11/21 18:17
 * 邮箱: jianyufeng@38.hn
 */
@ActivityTransition(2)
public class CreateGroupActivity extends ECSuperActivity implements View.OnClickListener {
    private Context context;
    //群图像
    private AddIconImageView groupIcon;
    //选择群组图像
    private SelectPicture selectPicture;
    //群组名称
    private EditText mNameEdit;
    //确定提交
    private TextView confirm;

    //创建的群
    private Group group;
    // 群组名称的输入变化
    final private TextWatcher textWatcher = new TextWatcher() {
        private int fliteCounts = 0;

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            fliteCounts = s.toString().trim().length();
            if (fliteCounts > 0) {
                confirm.setEnabled(true);
                return;
            }
            confirm.setEnabled(false);
        }
    };
    //选择图片的弹窗
    private AbsListView.OnItemClickListener itemsOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // 根据传入的数据集合的位置进行判断  初始位置重0开始
            /*
            <item>拍照</item>
            <item>从相册选择</item>
            <item>使用默认图像</item>
            <item>取消</item>
            */
            selectPicture.dismiss();//隐藏popWindow
            switch (position) {
                case 0: //拍照
                    Toast.makeText(view.getContext(), getResources().getStringArray(R.array.select_pic)[position], Toast.LENGTH_SHORT).show();
                    break;
                case 1://从相册选择

                    break;
                case 2://使用默认图像
                    //1 上传图片到服务器 2 成功后设置到本地
                    groupIcon.setTag(1);
                    groupIcon.setImageResource(R.drawable.group_default_icon);
                    break;
                case 3:
                    if (CreateGroupActivity.this.selectPicture.isShowing()) {
                        CreateGroupActivity.this.selectPicture.dismiss();
                    }
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //设置导航栏内容
        getTopBarView().setTopBarToStatus(1, R.drawable.topbar_back_bt, R.drawable.head_right_save_selector, null, getString(R.string.create_group_confirm), getString(R.string.create_group_edit), null, this);
        confirm = getTopBarView().getRightText();
        confirm.setEnabled(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.create_group_activity;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //群组图像的相关设置
        groupIcon = (AddIconImageView) findViewById(R.id.add_icon);
        groupIcon.setTag(0);
        groupIcon.setOnClickListener(this);
        //群组名称的设置
        mNameEdit = (EditText) findViewById(R.id.group_name);
        mNameEdit.addTextChangedListener(textWatcher);


    }

    @Override
    protected void asynData() {

    }

    @Override
    protected void logicControlView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left: //回退按钮事件
                hideSoftKeyboard();
                finish();
                break;
            case R.id.add_icon: //选择群组图像
                hideSoftKeyboard();
                //使用PopupWindow 设置群图像
                selectPicture = new SelectPicture(this, select_pic, itemsOnClick);
                selectPicture.showAtLocation(findViewById(R.id.top_layout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.text_right:    //确定按钮  提交创建群组
                    startActivity(new Intent(this, GroupDetailsActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
