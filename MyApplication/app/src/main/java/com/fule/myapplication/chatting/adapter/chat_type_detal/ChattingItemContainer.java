
package com.fule.myapplication.chatting.adapter.chat_type_detal;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fule.myapplication.R;
import com.fule.myapplication.common.LogUtil;

/**
 * 聊天类型的布局容器 主要是动态添加了时间  因此必须在每种类型的根视图添加
 * android:layout_below="@+id/chatting_time_tv"   保证其在时间 视图的下面
 * 使用了相对布局作为每种类型的最终根视图
 */
public class ChattingItemContainer extends RelativeLayout {

    public static final String TAG = LogUtil.getLogUtilsTag(ChattingItemContainer.class);

    private int mResource;   //布局中的根视图
    private LayoutInflater mInflater;  //布局加载器

    /**
     * 构造方法
     *
     * @param inflater 布局加载器
     * @param resource 视图 ID
     */
    //@SuppressWarnings("deprecation")  //忽略过时警告
    public ChattingItemContainer(LayoutInflater inflater, int resource) {
        super(inflater.getContext());
        mInflater = inflater;
        mResource = resource;

        //添加时间控件 给 每一个视图 通过代码添加
        TextView textView = new TextView(getContext());//R.style.ChattingUISplit ???
        textView.setId(R.id.chatting_time_tv);                 //设置ID
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12.0F);//设置字体大小
        LayoutParams textViewLayoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        textViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//		textViewLayoutParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.NormalPadding), 0,
//				getResources().getDimensionPixelSize(R.dimen.NormalPadding));
        addView(textView, textViewLayoutParams);         //添加到根视图中


        // 添加内容视图
        View chattingView = mInflater.inflate(mResource, null);
        int id = chattingView.getId();                  //给内容视图添加 ID
        if (id == -1) {                                //如果视图没有ID 默认是-1
            LogUtil.v(TAG, "content view has no id, use defaul id");
            id = R.id.chatting_content_area;
            chattingView.setId(id);                        //设置 ID
        }

		LayoutParams chattingViewLayoutParams = new LayoutParams(
				 LayoutParams.FILL_PARENT,
				 LayoutParams.WRAP_CONTENT);
		 chattingViewLayoutParams.addRule(RelativeLayout.BELOW, R.id.chatting_time_tv);
//		 chattingViewLayoutParams.addRule(RelativeLayout.LEFT_OF, R.id.chatting_checkbox);
		 addView(chattingView, chattingViewLayoutParams);
//
//		 View maskView = new View(getContext());
//		 maskView.setId(R.id.chatting_maskview);
//		 maskView.setVisibility(View.GONE);
//		 LayoutParams maskViewLayoutParams = new LayoutParams(
//				 LayoutParams.FILL_PARENT,
//				 LayoutParams.FILL_PARENT);
//
//		 maskViewLayoutParams.addRule(RelativeLayout.ALIGN_TOP , id);
//		 maskViewLayoutParams.addRule(RelativeLayout.ALIGN_BOTTOM , id);
//		 addView(maskView, maskViewLayoutParams);
    }

}

