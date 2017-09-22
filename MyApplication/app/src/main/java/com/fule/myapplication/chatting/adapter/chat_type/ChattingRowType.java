
package com.fule.myapplication.chatting.adapter.chat_type;

/**
 * * Created by Administrator on 2016/8/4.
 * 分聊天类型   由于适配器中使用的是整型进行分类，因此衍生出本类
 * 例：C200R  C200T    C:聊天 ，    200 定义的类型  对照ChattingsRowUtils的整型   ，R:收到的消息  ，T  传输   发送的消息。
 */
public enum ChattingRowType {

    /**
     * 文本消息     收到的
     */
    TEXT_ROW_RECEIVED("C2000R", Integer.valueOf(0)),

    /**
     * 文本消息     发送的
     */
    TEXT_ROW_TRANSMIT("C2000T", Integer.valueOf(1)),
    /**
     * 显示图片消息  收到的
     */
    IMAGE_ROW_RECEIVED("C2001R", Integer.valueOf(2)),

    /**
     * 显示图片消息  发送的
     */
    IMAGE_ROW_TRANSMIT("C2001T", Integer.valueOf(3)),

    /**
     * 语音消息  收到的
     */
    VOICE_ROW_RECEIVED("C2002R", Integer.valueOf(4)),

    /**
     * 语音消息  发送的
     */
    VOICE_ROW_TRANSMIT("C2002T", Integer.valueOf(5)),

    /**
     * 位置消息  收到的
     */
    LOCATION_ROW_RECEIVED("C2003R", Integer.valueOf(6)),
    /**
     * 位置消息  发送的
     */
    LOCATION_ROW_TRANSMIT("C2003T", Integer.valueOf(7));
//    /**
//     * display a file of message received
//     */
//    FILE_ROW_RECEIVED("C1024R", Integer.valueOf(3)),
//
//    /**
//     * display a file of message transmitted
//     */
//    FILE_ROW_TRANSMIT("C1024T", Integer.valueOf(4)),
//


//    /**
//     * chatting item for system .such as time
//     */
//    CHATTING_SYSTEM("C186R", Integer.valueOf(9)),

//    CALL_ROW_RECEIVED("C2400R", Integer.valueOf(12)),
//    CALL_ROW_TO("C2400T", Integer.valueOf(13));

//    CALL_ROW_TRANSMIT("C2400T" , Integer.valueOf(13));


    private final Integer mId;
    private final Object mDefaultValue;

    /**
     * Constructor of <code>ChattingRowType</code>.
     *
     * @param id           The unique identifier of the setting
     * @param defaultValue The default value of the setting
     */
    private ChattingRowType(Object defaultValue, Integer id) {
        this.mId = id;
        this.mDefaultValue = defaultValue;
    }

    /**
     * Method that returns the unique identifier of the setting.
     *
     * @return the mId
     */
    public Integer getId() {
        return this.mId;
    }

    /**
     * Method that returns the default value of the setting.
     *
     * @return Object The default value of the setting
     */
    public Object getDefaultValue() {
        return this.mDefaultValue;
    }


    /**
     * Method that returns an instance of {@link ECPreferenceSettings} from its.
     * unique identifier
     *
     * @param value The unique identifier
     * @return CCPPreferenceSettings The navigation sort mode
     */
    public static ChattingRowType fromValue(String value) {
        ChattingRowType[] values = values();
        int cc = values.length;
        for (int i = 0; i < cc; i++) {
            if (values[i].mDefaultValue.equals(value)) {
                return values[i];
            }
        }
        return null;
    }

}
