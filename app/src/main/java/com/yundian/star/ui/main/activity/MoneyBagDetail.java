package com.yundian.star.ui.main.activity;

import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.MyPopupMenuEntity;
import com.yundian.star.listener.OnChildViewClickListener;
import com.yundian.star.ui.view.MyPopupMenu;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.yundian.star.ui.wangyi.DemoCache.getContext;

/**
 * Created by sll on 2017/5/24.
 */

public class MoneyBagDetail extends BaseActivity {

    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.lrv)
    LRecyclerView lrv;
    private MyPopupMenu popupMenu;
    private List<MyPopupMenuEntity> lists;

    @Override
    public int getLayoutId() {
        return R.layout.activity_money_bag_detail;
    }

    @Override
    public void initPresenter() {


    }

    @Override
    public void initView() {
        initData();
    }

    private void initData() {
        ntTitle.setTitleText(getResources().getString(R.string.money_bag_detail));
        ntTitle.setTvLeftVisiable(true);
        ntTitle.setRightImagSrc(R.drawable.about_logo);
        setData();
        ntTitle.setOnRightImagListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("sssssssssssssss");
                popupMenu = new MyPopupMenu(getContext(), lists);
                popupMenu.setOnChildViewClickListener(new OnChildViewClickListener() {
                    @Override
                    public void onChildViewClick(View childView, int action, Object obj) {
                        ToastUtils.showShort("子空间被点击了");
                    }
                });

                popupMenu.showAsDropDown(ntTitle.getRightImage(), 0, 0);
            }
        });
    }

    private void setData() {
        lists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MyPopupMenuEntity entity = new MyPopupMenuEntity();
            entity.setText("日期" + i);
            lists.add(entity);
        }
    }
}
