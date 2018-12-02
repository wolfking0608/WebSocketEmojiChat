package com.rance.chatui.adapter.holder;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.rance.chatui.R;
import com.rance.chatui.adapter.ChatAdapter;
import com.rance.chatui.bean.HistoryBean;
import com.rance.chatui.enity.MessageInfo;
import com.rance.chatui.util.Utils;
import com.rance.chatui.widget.BubbleImageView;
import com.rance.chatui.widget.GifTextView;


import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 左边的条目的holder
 */
public class ChatAcceptViewHolder extends BaseViewHolder<MessageInfo> {
    Unbinder unbinder;
    @BindView(R.id.chat_item_date)
    TextView chatItemDate;//显示的时间
    @BindView(R.id.chat_item_header)
    ImageView chatItemHeader;//头像
    @BindView(R.id.chat_item_content_text)
    GifTextView chatItemContentText;//消息内容区
    @BindView(R.id.chat_item_content_image)
    BubbleImageView chatItemContentImage;//图片区
    @BindView(R.id.chat_item_voice)
    ImageView chatItemVoice;//语音
    @BindView(R.id.chat_item_layout_content)
    LinearLayout chatItemLayoutContent;
    @BindView(R.id.chat_item_voice_time)
    TextView chatItemVoiceTime;//声音显示的时间
    private ChatAdapter.onItemClickListener onItemClickListener;
    private Handler handler;

    public ChatAcceptViewHolder(ViewGroup parent, ChatAdapter.onItemClickListener onItemClickListener, Handler handler) {
        super(parent, R.layout.item_chat_accept);
         unbinder = ButterKnife.bind(this, itemView);
        this.onItemClickListener = onItemClickListener;
        this.handler = handler;
    }

    @Override
    public void setData(MessageInfo data) {
        HistoryBean bean = data.getContent();
        String time = bean.getTime();
        long l = Long.parseLong(time)*1000;
        Date date= new Date(l);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String timeStr = formatter.format(date);
        if (TextUtils.isEmpty(timeStr)){
            chatItemDate.setVisibility(View.GONE);
        }else{
            chatItemDate.setVisibility(View.VISIBLE);
            chatItemDate.setText(timeStr);
        }

        Glide.with(getContext()).load(data.getHeader()).into(chatItemHeader);
        chatItemHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onHeaderClick(getDataPosition());
            }
        });


        if (data.getContent() != null) {
            chatItemContentText.setSpanText(handler, bean.getData(), true);//设置图片
            chatItemVoice.setVisibility(View.GONE);
            chatItemContentText.setVisibility(View.VISIBLE);
            chatItemLayoutContent.setVisibility(View.VISIBLE);
            chatItemVoiceTime.setVisibility(View.GONE);
            chatItemContentImage.setVisibility(View.GONE);
        } else if (data.getImageUrl() != null) {
            chatItemVoice.setVisibility(View.GONE);
            chatItemLayoutContent.setVisibility(View.GONE);
            chatItemVoiceTime.setVisibility(View.GONE);
            chatItemContentText.setVisibility(View.GONE);
            chatItemContentImage.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(data.getImageUrl()).into(chatItemContentImage);
            chatItemContentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onImageClick(chatItemContentImage, getDataPosition());
                }
            });
        } else if (data.getFilepath() != null) {
            chatItemVoice.setVisibility(View.VISIBLE);
            chatItemLayoutContent.setVisibility(View.VISIBLE);
            chatItemContentText.setVisibility(View.GONE);
            chatItemVoiceTime.setVisibility(View.VISIBLE);
            chatItemContentImage.setVisibility(View.GONE);
            chatItemVoiceTime.setText(Utils.formatTime(data.getVoiceTime()));
            chatItemLayoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onVoiceClick(chatItemVoice, getDataPosition());
                }
            });
        }
    }

}
