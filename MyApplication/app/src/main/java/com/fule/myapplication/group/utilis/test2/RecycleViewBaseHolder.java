package com.fule.myapplication.group.utilis.test2;

/**
 * Created by Administrator on 2016/11/16.
 */

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 基础的ViewHolder
 * ViewHolder只作View的缓存,不关心数据内容
 * Created by 简玉锋 on 2016/11/18.
 */
public class RecycleViewBaseHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> viewArray;

    /**
     * 构造ViewHolder
     * @param parent 父类容器
     * @param resId 布局资源文件id
     */
    public RecycleViewBaseHolder(ViewGroup parent, @LayoutRes int resId) {
        super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));
        viewArray = new SparseArray<>();
    }

    /**
     * 构建ViewHolder
     * @param view 布局View
     */
    public RecycleViewBaseHolder(View view) {
        super(view);
        viewArray = new SparseArray<>();
    }

    /**
     * 获取布局中的View
     * @param viewId view的Id
     * @param <T> View的类型
     * @return view
     */
    private  <T extends View>T getView(@IdRes int viewId){
        View view = viewArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            if (view != null) {
                viewArray.put(viewId, view);
            }
        }
        return (T) view;
    }

    /**
     * 获取Context实例
     * @return context
     */
    protected Context getContext() {
        return itemView.getContext();
    }

    /**
     *  设置文本数据
     * @param viewId
     * @param text
     * @return
     */
    public RecycleViewBaseHolder setText(@IdRes int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     *  设置图片
     * @param viewId
     * @param resId
     * @return
     */
    public RecycleViewBaseHolder setImageResource(@IdRes int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    /**
     * 设置点击事件
     * @param viewId
     * @param listener
     * @return
     */
    public RecycleViewBaseHolder setOnClickListener(@IdRes int viewId,
                                                    View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }
    /**
     * 设置显示属性
     * @param viewId
     * @param visibility
     * @return
     */
    public RecycleViewBaseHolder setVisibility(@IdRes int viewId,
                                               int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

}
