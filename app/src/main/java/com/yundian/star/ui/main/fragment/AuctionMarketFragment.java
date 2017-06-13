package com.yundian.star.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseFragment;
import com.yundian.star.ui.view.MySeekBar;

import butterknife.Bind;

import static com.yundian.star.R.id.rb_1;


/**
 * Created by Administrator on 2017/5/22.
 * 粉丝拍卖
 */

public class AuctionMarketFragment extends BaseFragment {

    @Bind(R.id.iv_src)
    ImageView iv_src ;
    @Bind(R.id.tv_residue_time)
    TextView tv_residue_time ;
    @Bind(R.id.tv_have_name)
    TextView tv_have_name ;
    @Bind(R.id.tv_have_time)
    TextView tv_have_time ;
    @Bind(R.id.seekBar)
    MySeekBar seekBar ;
    @Bind(R.id.fl_auction_content)
    FrameLayout fl_auction_content;
    private AutionTopFragment aution_buy;
    private AutionTopFragment aution_shell;
    private String code;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_auction_market;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        if (getArguments()!=null){
            code = getArguments().getString(AppConstant.STAR_CODE);
        }
        initListener();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (savedInstanceState==null){
            Bundle bundle1 = new Bundle();
            bundle1.putString(AppConstant.STAR_CODE,code);
            bundle1.putInt(AppConstant.AUCTION_TYPE,1);
            aution_buy = new AutionTopFragment();
            aution_buy.setArguments(bundle1);
            Bundle bundle2= new Bundle();
            bundle2.putString(AppConstant.STAR_CODE,code);
            bundle2.putInt(AppConstant.AUCTION_TYPE,2);
            aution_shell = new AutionTopFragment();
            aution_shell.setArguments(bundle2);
            transaction.add(R.id.fl_auction_content, aution_buy,"AutionBuy");
            transaction.add(R.id.fl_auction_content, aution_shell,"AutionShell");
        }else {
            aution_buy = (AutionTopFragment) getChildFragmentManager().findFragmentByTag("AutionBuy");
            aution_shell = (AutionTopFragment) getChildFragmentManager().findFragmentByTag("AutionShell");
        }
        transaction.commit();
    }

    private void initListener() {
        RadioGroup radioGroup = (RadioGroup)rootView.findViewById(R.id.radio_group);
        RadioButton button = (RadioButton)radioGroup.findViewById(R.id.rb_1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (group.getId()){
                    case rb_1 :
                        SwitchTo(0);
                        break;
                    case R.id.rb_2 :
                        SwitchTo(1);
                        break;
                }

            }
        });
        radioGroup.check(button.getId());
    }
    /**
     * 切换
     */
    private void SwitchTo(int position) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                transaction.hide(aution_shell);
                transaction.show(aution_buy);
                transaction.commit();
                break;
            case 1:
                transaction.hide(aution_buy);
                transaction.show(aution_shell);
                transaction.commit();
                break;
            default:
                break;
        }
    }
}
