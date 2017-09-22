package com.fule.myapplication.chatting.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fule.myapplication.R;
import com.fule.myapplication.chatting.adapter.chat_type.ChattingRowType;

/**
 * 位置类的holder 对象
 */

public class LocationViewHolder extends BaseHolder {

	public View chattingContent;
	/**
	 * TextView that display IMessage description.
	 */
	public TextView descTextView;
//	public EmojiconTextView descTextView;

	public RelativeLayout relativeLayout; //位置的容器
	
	/**
	 * @param type
	 */
	public LocationViewHolder(int type) {
		super(type);

	}
	
	public BaseHolder initBaseHolder(View baseView , boolean receive) {
		super.initBaseHolder(baseView);

		chattingTime = (TextView) baseView.findViewById(R.id.chatting_time_tv);  //时间
		chattingUser = (TextView) baseView.findViewById(R.id.chatting_user_tv);  // 姓名
		descTextView = (TextView) baseView.findViewById(R.id.tv_location);        //地理位置
//		checkBox = (CheckBox) baseView.findViewById(R.id.chatting_checkbox);
//		chattingMaskView = baseView.findViewById(R.id.chatting_maskview);
		chattingContent = baseView.findViewById(R.id.chatting_content_area);    //布局中的根视图
		relativeLayout=(RelativeLayout) baseView.findViewById(R.id.re_location); //位置的图片
		if(receive) {                                                     //收的
			type = ChattingRowType.LOCATION_ROW_RECEIVED.ordinal();
			return this;
		}
		
		uploadState = (ImageView) baseView.findViewById(R.id.chatting_state_iv);   //重发
		progressBar = (ProgressBar) baseView.findViewById(R.id.uploading_pb);      //进度
		type = ChattingRowType.LOCATION_ROW_TRANSMIT.ordinal();          //发送
		return this;
	}

	/**
	 *  Display imessage text
	 * @return  //地理位置控件
	 *
	 */
	public TextView getDescTextView() {
		if(descTextView == null) {
			descTextView = (TextView) getBaseView().findViewById(R.id.chatting_content_itv);
		}
		return descTextView;
	}
	
	/**
	 *
	 * @return 重发控件
	 */
	public ImageView getChattingState() {
		if(uploadState == null) {
			uploadState = (ImageView) getBaseView().findViewById(R.id.chatting_state_iv);
		}
		return uploadState;
	}
	
	/**
	 * 
	 * @return   发送进度
	 */
	public ProgressBar getUploadProgressBar() {
		if(progressBar == null) {
			progressBar = (ProgressBar) getBaseView().findViewById(R.id.uploading_pb);
		}
		return progressBar;
	}
}
