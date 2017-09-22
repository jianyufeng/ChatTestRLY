package com.fule.myapplication.common.superActivity.ui.overFlorw;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.fule.myapplication.R;
import com.fule.myapplication.group.utilis.pulltorefresh.AbsAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义选择图像菜单选项，popWindow 的形式
 * Created by Jorstin on 2015/3/18.
 */
public class SelectPicture extends PopupWindow {
    private View mMenuView;
    private ListView ListView;
    public SelectPicture(Activity context, int resourceIdArray, AdapterView.OnItemClickListener itemsOnClick) {
        super(context);
        String[] stringArray = context.getResources().getStringArray(resourceIdArray);
        List<String> strings = Arrays.asList(stringArray);//数据
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.select_pic_popwindow_menu,null);//加载布局
        ListView = (android.widget.ListView) mMenuView.findViewById(R.id.comm_popup_list);//查找数组容器
        ListView.setAdapter(new AbsAdapter<String>(context, strings, R.layout.select_pic_menu_item) {//设置适配器
            @Override
            public void showItemContenData(ViewHolder viewHolder, String data, ViewGroup parent, int position) {
                viewHolder.setTextViewContent(R.id.item_tv, data);
            }
        });
        if (itemsOnClick != null) { //设置item的点击事件
            ListView.setOnItemClickListener(itemsOnClick);
        }
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);     //给popWindow添加视图
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT); //设置尺寸  使用充满屏幕 为显示Dialog 的样子
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.select_pic_popwindow_anim_style);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

}

