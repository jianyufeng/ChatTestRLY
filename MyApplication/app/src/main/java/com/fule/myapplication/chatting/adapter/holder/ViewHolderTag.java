
package com.fule.myapplication.chatting.adapter.holder;


import io.rong.imlib.model.Message;
/**
 *
 *
 */
public class ViewHolderTag {

	public int position;
	public Message detail;
	public int type;
	public int rowType;
	public boolean receive;
	public boolean voipcall;
	
	
	public static ViewHolderTag createTag(Message detail , int type , int position) {
		ViewHolderTag holderTag = new ViewHolderTag();
		holderTag.position = position;
		holderTag.type = type;
		holderTag.detail = detail;
		return holderTag;
	}
	
	public static ViewHolderTag createTag(Message detail , int type , int position , int rowType , boolean receive) {
		ViewHolderTag holderTag = new ViewHolderTag();
		holderTag.position = position;
		holderTag.type = type;
		holderTag.rowType = rowType;
		holderTag.detail = detail;
		holderTag.receive = receive;
		return holderTag;
	}
	
	/**
	 * 
	 * @param detail
	 * @param position
	 * @return
	 */
	public static ViewHolderTag createTag(Message detail , int position) {
		ViewHolderTag holderTag = new ViewHolderTag();
		holderTag.position = position;
		holderTag.detail = detail;
		holderTag.type = TagType.TAG_PREVIEW;
		return holderTag;
	}
	
	public static ViewHolderTag createTag(Message detail , int type , int position , boolean voipcall) {
		ViewHolderTag holderTag = new ViewHolderTag();
		holderTag.position = position;
		holderTag.detail = detail;
		holderTag.type = type;
		holderTag.voipcall = voipcall;
		return holderTag;
	}
	
	public static class TagType{
		public static final int TAG_PREVIEW = 0;
		public static final int TAG_VIEW_FILE = 1;
		public static final int TAG_VOICE = 2;
		public static final int TAG_VIEW_PICTURE = 3;
		public static final int TAG_RESEND_MSG = 4;
		public static final int TAG_VIEW_CONFERENCE = 5;
		public static final int TAG_VOIP_CALL = 6;
		public static final int TAG_IM_LOCATION = 7;
	}
}
