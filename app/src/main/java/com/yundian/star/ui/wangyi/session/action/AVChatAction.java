package com.yundian.star.ui.wangyi.session.action;

import android.widget.Toast;

import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nim.uikit.session.actions.BaseAction;

import com.yundian.star.R;


/**
 * Created by hzxuwen on 2015/6/12.
 */
public class AVChatAction extends BaseAction {


    /**
     * 构造函数
     *
     * @param iconResId 图标 res id
     * @param titleId   图标标题的string res id
     */
    protected AVChatAction(int iconResId, int titleId) {
        super(iconResId, titleId);
    }

    @Override
    public void onClick() {
        if (NetworkUtil.isNetAvailable(getActivity())) {
        } else {
            Toast.makeText(getActivity(), R.string.network_is_not_available, Toast.LENGTH_SHORT).show();
        }
    }

}
