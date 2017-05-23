package com.yundian.star.ui.main.fragment;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yundian.star.R;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.utils.LogUtils;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/22.
 */

public class CommentMarketFragment extends BaseFragment {
    @Bind(R.id.lrv)
    LRecyclerView lrv ;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_comment_market;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        getData();
    }

    private void getData() {
        NetworkAPIFactoryImpl.getInformationAPI().getFansComments("13072714518","1001", new OnAPIListener<Object>() {
                    @Override
                    public void onError(Throwable ex) {

                    }

                    @Override
                    public void onSuccess(Object o) {
                        LogUtils.loge(o.toString());
                    }
                }
        );
    }
}
