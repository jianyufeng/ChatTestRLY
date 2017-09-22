package com.fule.myapplication.chatting.adapter;

import android.content.Context;
import android.database.Cursor;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/4.
 */
public abstract class ChatBaseAdapter<T> extends BaseAdapter {

    /**
     * 数据Cursor
     */
    private Cursor mCursor;
    /**
     * 数据缓存
     */
    private Map<Integer, T> mData;
    /**
     * 适配器使用数据类型
     */
    protected T t;
    /**
     * 上下文对象
     */
    protected Context mContext;
    /**
     * 数据总数
     */
    protected int mCount;

    /**
     * 构造方法
     *
     * @param context 环境
     * @param t       适配器使用数据类型
     */
    public ChatBaseAdapter(Context context, T t) {
        this.mContext = context;
        this.t = t;
        this.mCount = -1;
    }

    /**
     * 设置数据源
     *
     * @param cursor 数据源
     */
    protected void setCursor(Cursor cursor) {
        mCursor = cursor;
        this.mCount = -1;
    }

    /**
     * 初始化数据缓存
     */
    public void initCache() {
        if (mData != null) {
            return;
        }
        mData = new HashMap<Integer, T>();
    }
    /**
     * 返回一个数据类型Cursor
     * @return
     */
    protected Cursor getCursor() {
        if(mCursor == null) {
            initCursor();
        }
        return mCursor;
    }
    /***
     * 关闭数据库
     */
    public void closeCursor() {
        if(mData != null) {
            mData.clear();
        }
        if(mCursor != null) {
            mCursor.close();
        }
        mCount = -1;
    }

    @Override
    public int getCount() {
        if(mCount < 0) {
            mCount = getCursor().getCount();
        }
        return mCount;
    }

    @Override
    public T getItem(int position) {
        /**
         * 数据的判断
         */
        if(position < 0 || !getCursor().moveToPosition(position)) {
            return null;
        }
        /**
         * 取出数据
         */
        if(mData == null) {
            return getItem(this.t, getCursor());
        }
        /**
         * 取出缓存数据
         */
        T _t = mData.get(Integer.valueOf(position));
        if(_t == null) {
            _t = getItem(null, getCursor());
        }
        mData.put(Integer.valueOf(position), _t);
        return _t;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    protected abstract void notifyChange();
    protected abstract void initCursor();
    protected abstract T getItem(T t , Cursor cursor);
}
