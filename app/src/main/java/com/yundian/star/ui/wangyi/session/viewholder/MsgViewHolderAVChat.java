package com.yundian.star.ui.wangyi.session.viewholder;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;
import com.yundian.star.R;

/**
 * Created by zhoujianghua on 2015/8/6.
 */
public class MsgViewHolderAVChat extends MsgViewHolderBase {

    private ImageView typeImage;
    private TextView statusLabel;

    public MsgViewHolderAVChat(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.nim_message_item_avchat;
    }

    @Override
    protected void inflateContentView() {
        typeImage = findViewById(R.id.message_item_avchat_type_img);
        statusLabel = findViewById(R.id.message_item_avchat_state);
    }

    @Override
    protected void bindContentView() {
        if (message.getAttachment() == null) {
            return;
        }

        layoutByDirection();

        refreshContent();
    }

    private void layoutByDirection() {


        if (isReceivedMessage()) {

            typeImage.setImageResource(R.drawable.avchat_left_type_video);

            statusLabel.setTextColor(context.getResources().getColor(R.color.color_grey_999999));
        } else {


            typeImage.setImageResource(R.drawable.avchat_right_type_video);

            statusLabel.setTextColor(Color.WHITE);
        }
    }

    private void refreshContent() {


        String textString = "";

        statusLabel.setText(textString);
    }
}
