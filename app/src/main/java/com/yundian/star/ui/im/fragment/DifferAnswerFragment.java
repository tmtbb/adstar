package com.yundian.star.ui.im.fragment;

import android.widget.LinearLayout;

import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.ui.wangyi.session.SessionHelper;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/10.
 */

public class DifferAnswerFragment extends BaseFragment {
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;
    @Bind(R.id.ll_goto_address_book)
    LinearLayout ll_goto_address_book ;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_diff_answer;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        nt_title.setTvLeftVisiable(false);
        nt_title.setTitleText(getString(R.string.diff_answer));
    }

    @OnClick(R.id.ll_goto_address_book)
    public void gotoAddressBook(){
        //startActivity(StarCommunicationBookActivity.class);
        SessionHelper.startP2PSession(getActivity(), "17682310986");
    }
}
