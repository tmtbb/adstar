package com.cloudTop.starshare.ui.wangyi.chatroom.fragment.tab;


import com.cloudTop.starshare.ui.wangyi.chatroom.fragment.MasterFragment;
import com.cloudTop.starshare.R;

/**
 * 主播基类fragment
 * Created by hzxuwen on 2015/12/14.
 */
public class MasterTabFragment extends ChatRoomTabFragment {
    private MasterFragment fragment;
    @Override
    protected void onInit() {
        fragment = (MasterFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.master_fragment);
    }

    @Override
    public void onCurrent() {
        super.onCurrent();
        if (fragment != null) {
            fragment.onCurrent();
        }
    }
}
