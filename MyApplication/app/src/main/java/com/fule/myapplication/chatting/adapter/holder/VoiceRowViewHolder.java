
package com.fule.myapplication.chatting.adapter.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fule.myapplication.R;
import com.fule.myapplication.chatting.adapter.ChatListViewAdapter;
import com.fule.myapplication.chatting.adapter.chat_type.ChattingRowType;
import com.fule.myapplication.chatting.adapter.voice.CCPAnimImageView;
import com.fule.myapplication.chatting.ui.ChattingActivity;

import io.rong.imlib.model.Message;
import io.rong.message.VoiceMessage;


/**
 * 语音的holder对象
 */
public class VoiceRowViewHolder extends BaseHolder {

    public TextView contentTv;
    public TextView voicePlayAnim;
    public CCPAnimImageView voiceAnim;
    public FrameLayout voicePlayFrameLayout;
    public CCPAnimImageView voiceLoading;
    public ProgressBar voiceSending;
    public TextView voiceSendigBG;

    /**
     * 构造方法
     *
     * @param type 类型
     */
    public VoiceRowViewHolder(int type) {
        super(type);

    }
    /**
     * 初始化holder 中的控件
     *
     * @param baseView 根视图
     * @param receive  是否是收消息
     * @return  返回holder对象
     */
    public BaseHolder initBaseHolder(View baseView, boolean receive) {
        super.initBaseHolder(baseView);

        chattingTime = ((TextView) baseView.findViewById(R.id.chatting_time_tv));           //时间
        chattingUser = ((TextView) baseView.findViewById(R.id.chatting_user_tv));           //姓名
        voicePlayAnim = ((TextView) baseView.findViewById(R.id.chatting_voice_play_anim_tv));//
//        checkBox = ((CheckBox) baseView.findViewById(R.id.chatting_checkbox));
//        chattingMaskView = baseView.findViewById(R.id.chatting_maskview);
        uploadState = ((ImageView) baseView.findViewById(R.id.chatting_state_iv));
        contentTv = ((TextView) baseView.findViewById(R.id.chatting_content_itv));
        voicePlayFrameLayout = ((FrameLayout) baseView.findViewById(R.id.chatting_voice_play_content));
        voiceAnim = ((CCPAnimImageView) baseView.findViewById(R.id.chatting_voice_anim));
        voiceAnim.restBackground();

        if (receive) {                                                                      //语音收消息
            type = ChattingRowType.VOICE_ROW_RECEIVED.ordinal();
            voiceAnim.setVoiceFrom(true);
            voiceLoading = ((CCPAnimImageView) baseView.findViewById(R.id.chatting_voice_loading));
            voiceLoading.setVoiceFrom(true);
            voiceLoading.restBackground();
            return this;
        }
                                                                                            //语音发消息
        voiceSending = ((ProgressBar) baseView.findViewById(R.id.chatting_voice_sending));
        progressBar = ((ProgressBar) baseView.findViewById(R.id.uploading_pb));
        voiceSendigBG = ((TextView) baseView.findViewById(R.id.chatting_voice_sending_bg));
        voiceAnim.setVoiceFrom(false);
        type = ChattingRowType.VOICE_ROW_TRANSMIT.ordinal();
        return this;
    }

    /**
     * @param holder
     * @param uploadVisibility
     * @param playVisibility
     * @param receive
     */
    private static void uploadVoiceStatus(VoiceRowViewHolder holder, int uploadVisibility, int playVisibility, boolean receive) {
        holder.uploadState.setVisibility(View.GONE);
        holder.contentTv.setVisibility(playVisibility);
        holder.voicePlayFrameLayout.setVisibility(playVisibility);

        if (receive) {
            holder.voiceLoading.setVisibility(uploadVisibility);
            return;
        }
        holder.voiceSendigBG.setVisibility(uploadVisibility);
    }

    public static void initVoiceRow(VoiceRowViewHolder holder, Message detail, int position, ChattingActivity activity, boolean receive) {
        if (holder == null) {
            return;
        }

        VoiceMessage vBody = (VoiceMessage) detail.getContent();
        int duration = vBody.getDuration();
        if (duration < 1) {
            duration = 1;
        }

        holder.voiceAnim.setVisibility(View.GONE);

        //设置语音的点击事件
        ViewHolderTag holderTag = ViewHolderTag.createTag(detail, ViewHolderTag.TagType.TAG_VOICE, position, holder.type, receive);
        holder.voicePlayAnim.setTag(holderTag);
//        holder.voicePlayAnim.setOnClickListener(activity.mChattingFragment.getChattingAdapter().getOnClickListener());

        ChatListViewAdapter adapterForce =null; //?????????
        if (adapterForce.mVoicePosition == position) {
            uploadVoiceStatus(holder, View.GONE, View.VISIBLE, receive);
            holder.voiceAnim.setVisibility(View.VISIBLE);
            holder.voiceAnim.startVoiceAnimation();
//            holder.voiceAnim.setWidth(DensityUtil.fromDPToPix(activity, getTimeWidth(duration)));
            holder.contentTv.setTextColor(Color.parseColor("#7390A0"));
            holder.contentTv.setShadowLayer(2.0F, 1.2F, 1.2F, Color.parseColor("#ffffffff"));
            holder.contentTv.setVisibility(View.VISIBLE);
            holder.contentTv.setText(activity.getString(R.string.fmt_time_length, duration));

//            holder.voicePlayAnim.setWidth(DensityUtil.fromDPToPix(activity, getTimeWidth(duration)));
            return;
        } else {
            holder.voiceAnim.stopVoiceAnimation();
            holder.voiceAnim.setVisibility(View.GONE);

        }


        if (detail.getSentStatus() == Message.SentStatus.SENT) { //???????????
            holder.contentTv.setTextColor(Color.parseColor("#7390A0"));
            holder.contentTv.setShadowLayer(2.0F, 1.2F, 1.2F, Color.parseColor("#ffffffff"));
            holder.contentTv.setVisibility(View.VISIBLE);
            holder.contentTv.setText(activity.getString(R.string.fmt_time_length, duration));

//            holder.voiceAnim.setWidth(DensityUtil.fromDPToPix(activity, getTimeWidth(duration)));
//            holder.voicePlayAnim.setWidth(DensityUtil.fromDPToPix(activity, getTimeWidth(duration)));

            uploadVoiceStatus(holder, View.GONE, View.VISIBLE, receive);
        } else {
            holder.contentTv.setShadowLayer(0.0F, 0.0F, 0.0F, 0);

            if (detail.getSentStatus() == Message.SentStatus.FAILED) {
                uploadVoiceStatus(holder, View.GONE, View.VISIBLE, receive);
                holder.contentTv.setVisibility(View.GONE);
            } else {
                uploadVoiceStatus(holder, View.VISIBLE, View.GONE, receive);
            }
            holder.voiceAnim.setWidth(80);
            holder.voicePlayAnim.setWidth(80);

        }

        if (!receive) {
//            holder.voiceAnim.setBackgroundResource(R.drawable.chatto_bg);
//            holder.voicePlayAnim.setBackgroundResource(R.drawable.chatto_bg);
        } else {
//            holder.voiceAnim.setBackgroundResource(R.drawable.chatfrom_bg);
//            holder.voicePlayAnim.setBackgroundResource(R.drawable.chatfrom_bg);
        }

        holder.contentTv.setBackgroundColor(0);


    }

    /**
     * @param time
     * @return
     */
    public static int getTimeWidth(int time) {
        if (time <= 2)
            return 80;
        if (time < 10)
            return (80 + 9 * (time - 2));
        if (time < 60)
            return (80 + 9 * (7 + time / 10));
        return 204;
    }
}
