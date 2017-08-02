package com.cloudTop.starshare.ui.im.fragment;

import android.widget.LinearLayout;

import com.cloudTop.starshare.base.BaseFragment;
import com.cloudTop.starshare.utils.JudgeIdentityUtils;
import com.cloudTop.starshare.widget.NormalTitleBar;
import com.cloudTop.starshare.R;
import com.cloudTop.starshare.ui.im.activity.StarCommunicationBookActivity;
import com.cloudTop.starshare.ui.im.activity.SystemMessagesActivity;
import com.cloudTop.starshare.utils.JudgeIsSetPayPwd;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/10.
 */

public class DifferAnswerFragment extends BaseFragment {
    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;
    @Bind(R.id.ll_star_address_book)
    LinearLayout ll_star_address_book ;

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


    @OnClick(R.id.ll_star_address_book)
    public void gotoAddressBook(){
        if (JudgeIdentityUtils.isIdentityed(getActivity())){
            startActivity(StarCommunicationBookActivity.class);
        }
    }

    @OnClick(R.id.ll_system_news)
    public void gotoSystemMessage(){
        if (!JudgeIsSetPayPwd.isSetPwd(getActivity())) {
            return;
        }
        startActivity(SystemMessagesActivity.class);
    }

}
