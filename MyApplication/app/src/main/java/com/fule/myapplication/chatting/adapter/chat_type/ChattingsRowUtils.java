package com.fule.myapplication.chatting.adapter.chat_type;

import io.rong.imlib.model.MessageContent;
import io.rong.message.ImageMessage;
import io.rong.message.LocationMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * Created by Administrator on 2016/8/4.
 * 获取聊天视图的类型
 * 使用整形返回
 */
public class ChattingsRowUtils {

    /**
     * 根据消息来分不同的类型
     * 文本消息  2000；
     * 图片消息  2001
     * 位置消息  2002
     * 图文消息  2003
     * 其他消息暂时使用文本消息  2000
     *
     * @param messageContent 获取消息内容。
     * @return
     */
    public static int getChattingMessageType(MessageContent messageContent) {
//        MessageContent messageContent = ecMessage.getContent();
        if (messageContent instanceof TextMessage) {//文本消息
            return 2000;
        } else if (messageContent instanceof ImageMessage) {//图片消息
            return 2001;
        } else if (messageContent instanceof VoiceMessage) {//语音消息
            return 2002;
        } else if (messageContent instanceof LocationMessage) {//位置消息
            return 2003;
        } else if (messageContent instanceof RichContentMessage) {
            return 2004;
        }
//		else if(type==ECMessage.Type.VIDEO){
//			return 1024;
//		}else if(type== ECMessage.Type.CALL){
//			return  2400;
//		}
        return 2000;
    }

//	/**
//	 *
//	 * @param iMessage
//	 * @return
//	 */
//	public static Integer getMessageRowType(ECMessage iMessage) {
//		ECMessage.Type type = iMessage.getType();
//		ECMessage.Direction direction = iMessage.getDirection();
//		if(type == ECMessage.Type.TXT) {
//			if(direction == ECMessage.Direction.RECEIVE) {
//				return ChattingRowType.DESCRIPTION_ROW_RECEIVED.getId();
//			}
//			return ChattingRowType.DESCRIPTION_ROW_TRANSMIT.getId();
//		} else if (type == ECMessage.Type.VOICE) {
//			if(direction == ECMessage.Direction.RECEIVE) {
//				return ChattingRowType.VOICE_ROW_RECEIVED.getId();
//			}
//			return ChattingRowType.VOICE_ROW_RECEIVED.getId();
//		} else if (type == ECMessage.Type.FILE||type==ECMessage.Type.VIDEO) {
//			if(direction == ECMessage.Direction.RECEIVE) {
//				return ChattingRowType.FILE_ROW_RECEIVED.getId();
//			}
//			return ChattingRowType.FILE_ROW_RECEIVED.getId();
//		} else if (type == ECMessage.Type.IMAGE) {
//			if(direction == ECMessage.Direction.RECEIVE) {
//				return ChattingRowType.IMAGE_ROW_RECEIVED.getId();
//			}
//			return ChattingRowType.IMAGE_ROW_RECEIVED.getId();
//		}else if(type==ECMessage.Type.LOCATION){
//			if(direction == ECMessage.Direction.RECEIVE) {
//				return ChattingRowType.LOCATION_ROW_RECEIVED.getId();
//			}
//			return ChattingRowType.LOCATION_ROW_TRANSMIT.getId();
//
//		}
//		return -1;
//	}
}
