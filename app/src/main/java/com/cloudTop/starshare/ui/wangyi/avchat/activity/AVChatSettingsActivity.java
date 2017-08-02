package com.cloudTop.starshare.ui.wangyi.avchat.activity;

import android.os.Bundle;

import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.cloudTop.starshare.R;

/**
 * Created by liuqijun on 7/19/16.
 * 注意:全局配置,不区分用户
 */
public class AVChatSettingsActivity extends UI {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.avchat_settings_layout);

        ToolBarOptions options = new ToolBarOptions();
        options.titleId = R.string.nrtc_settings;
        setToolBar(R.id.toolbar, options);


    }

}
