
package com.fule.myapplication.chatting.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fule.myapplication.R;
import com.fule.myapplication.chatting.adapter.chat_type.ChattingRowType;

/**
 *  图片类的holder 对象
 *  */
public class ImageRowViewHolder extends BaseHolder {
	//图片内容的控件
	public ImageView chattingContentIv;

//	public View uploadingView; //暂时未用

//	public TextView uploadingText;
//	public ImageView maskView;
//	public ImageView mGifIcon;
	
	/**
	 *  构造方法
	 * @param type   类型
	 */
	public ImageRowViewHolder(int type) {
		super(type);
	}
	/**
	 * 初始化holder 中的控件
	 *
	 * @param baseView 根视图
	 * @param receive  是否是收消息
	 * @return  返回holder对象
	 */
	public BaseHolder initBaseHolder(View baseView , boolean receive) {
		super.initBaseHolder(baseView);
		
		chattingTime = (TextView) baseView.findViewById(R.id.chatting_time_tv);          //时间 可以不用
		chattingContentIv = (ImageView) baseView.findViewById(R.id.chatting_content_iv); //图片控件
//		checkBox = (CheckBox) baseView.findViewById(R.id.chatting_checkbox);
//		chattingMaskView = baseView.findViewById(R.id.chatting_maskview);
//		uploadingView = baseView.findViewById(R.id.uploading_view);
//		mGifIcon = (ImageView) baseView.findViewById(R.id.img_gif);

		if(receive) {
			chattingUser = (TextView) baseView.findViewById(R.id.chatting_user_tv);      //姓名
//			progressBar = (ProgressBar) baseView.findViewById(R.id.downloading_pb);
			type = ChattingRowType.IMAGE_ROW_TRANSMIT.ordinal();                        //图片类型  发送
		} else {
			progressBar = (ProgressBar) baseView.findViewById(R.id.uploading_pb);        //发送的进度
//			uploadingText = (TextView) baseView.findViewById(R.id.uploading_tv);
//			chattingUser = (TextView) baseView.findViewById(R.id.chatting_user_tv);      //姓名
			type = ChattingRowType.IMAGE_ROW_RECEIVED.ordinal();                         //图片类型  收的
		}
//		maskView = (ImageView) baseView.findViewById(R.id.chatting_content_mask_iv);

		return this;
	}
}

