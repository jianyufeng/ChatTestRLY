package com.fule.myapplication.chatting.adapter.chat_type_detal;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;

import com.fule.myapplication.R;
import com.fule.myapplication.chatting.adapter.chat_type.ChattingRowType;
import com.fule.myapplication.chatting.adapter.holder.BaseHolder;
import com.fule.myapplication.chatting.adapter.holder.LocationViewHolder;
import com.fule.myapplication.chatting.adapter.holder.ViewHolderTag;

import io.rong.imlib.model.Message;
import io.rong.message.LocationMessage;

/**
 * 位置  收的
 */

public class LocationRxRow extends BaseChattingRow {
	/**
	 * 构造方法
	 *
	 * @param type 聊天类型
	 */
	public LocationRxRow(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 设置位置视图
	 *
	 * @param inflater    视图加载器
	 * @param convertView 内容视图
	 * @return
	 */
	@Override
	public View buildChatView(LayoutInflater inflater, View convertView) {
			
		 if (convertView == null ) {
	            convertView = new ChattingItemContainer(inflater, R.layout.chatting_item_location_from);
	            LocationViewHolder holder = new LocationViewHolder(mRowType);
	            convertView.setTag(holder.initBaseHolder(convertView, true));
	        } 
			return convertView;
	
	}

	/**
	 * 设置位置数据
	 *
	 * @param context
	 * @param baseHolder 向上转型的 基类holde
	 * @param detail     消息体
	 * @param position   位置
	 */
	@Override
	protected void buildChattingData(Context context, BaseHolder baseHolder,
			Message detail, int position) {
		LocationViewHolder holder = (LocationViewHolder) baseHolder;
		Message message = detail;
		if(message != null) {

			LocationMessage textBody = (LocationMessage) message.getContent();

			//添加位置信息   添加位置图片
			holder.descTextView.setText(textBody.getPoi());

			//添加点击事件
			ViewHolderTag holderTag = ViewHolderTag.createTag(detail,
					ViewHolderTag.TagType.TAG_IM_LOCATION, position);
//			OnClickListener onClickListener = ((ChattingActivity) context).mChattingFragment.getChattingAdapter().getOnClickListener();
//			holder.relativeLayout.setTag(holderTag);
//			holder.relativeLayout.setOnClickListener(onClickListener);
		}



	}
	@Override
	public int getChatViewType() {
		// TODO Auto-generated method stub
		return ChattingRowType.LOCATION_ROW_RECEIVED.ordinal();
	}

	@Override
	public boolean onCreateRowContextMenu(ContextMenu contextMenu,
										  View targetView, Message detail) {
		// TODO Auto-generated method stub
		return false;
	}

}
