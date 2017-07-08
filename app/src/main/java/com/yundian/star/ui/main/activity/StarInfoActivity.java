package com.yundian.star.ui.main.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.app.AppConstant;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.HaveStarTimeBeen;
import com.yundian.star.been.StarExperienceBeen;
import com.yundian.star.greendao.GreenDaoManager;
import com.yundian.star.greendao.StarInfo;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.im.activity.StarCommunicationBookActivity;
import com.yundian.star.ui.main.adapter.HorizontalRcvAdapter;
import com.yundian.star.ui.main.adapter.StarBuyExcAdapter;
import com.yundian.star.utils.HorizontalItemDecorator;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.JudgeIdentityUtils;
import com.yundian.star.utils.ListViewUtil;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.MyListView;
import com.yundian.star.widget.ZoomImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/7/8.
 */

public class StarInfoActivity extends BaseActivity implements View.OnClickListener {
    private String code;
    private TextView back;
    private TextView tv_meet_starts;
    private TextView tv_buy_time;
    private ImageView imag_meesage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_star_info;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        code = getIntent().getStringExtra(AppConstant.STAR_CODE);
        back = (TextView) findViewById(R.id.tv_back);
        tv_meet_starts = (TextView) findViewById(R.id.tv_meet_starts);
        tv_buy_time = (TextView) findViewById(R.id.tv_buy_time);
        imag_meesage = (ImageView) findViewById(R.id.imag_meesage);
        getHaveCodeTime();
        initListener();
        getStarExperience();
        initHorizontalRecview();
    }

    private void initListener() {
        back.setOnClickListener(this);
        tv_meet_starts.setOnClickListener(this);
        tv_buy_time.setOnClickListener(this);
        imag_meesage.setOnClickListener(this);
    }

    private void initHorizontalRecview() {
        final ArrayList<String> arrayList = new ArrayList();
        for (int i =0 ;i<4;i++){
            arrayList.add("1");
        }
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rlv);
        final LinearLayoutManager layoutManager  = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        HorizontalRcvAdapter adapter = new HorizontalRcvAdapter(mContext, arrayList);
        recyclerView.addItemDecoration(new HorizontalItemDecorator((int) mContext.getResources().getDimension(R.dimen.dp_10)));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter.setOnImageClick(new HorizontalRcvAdapter.OnImageClick() {
            @Override
            public void onClik(int position) {
                showPopupWindow(arrayList.get(position));
            }
        });
    }


    private void getStarExperience() {
        NetworkAPIFactoryImpl.getInformationAPI().getStarExperience(code, new OnAPIListener<StarExperienceBeen>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(StarExperienceBeen o) {
                if (o.getResult()==1&&o.getList()!=null){
                    initExp(o);
                }
            }
        });
    }
    private void initExp(StarExperienceBeen o) {
        StarBuyExcAdapter buyExcAndAchAdapter = new StarBuyExcAdapter(this, o.getList());
        MyListView listExpView1 = (MyListView)findViewById(R.id.listview_exp);
        listExpView1.setVerticalScrollBarEnabled(true);
        listExpView1.setVisibility(View.VISIBLE);
        listExpView1.setAdapter(buyExcAndAchAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(listExpView1);
    }
    private void showPopupWindow(String prc_url) {
        View popView = LayoutInflater.from(this).inflate(R.layout.popwindow_imegview, null);
        ZoomImageView zoomImageView = (ZoomImageView)popView.findViewById(R.id.zoomimage);
        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(popView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        ImageLoaderUtils.displayWithDefaultImg(this,zoomImageView,prc_url,R.drawable.infos_news_defolat);
        zoomImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM,0,0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                break;
            case R.id.tv_meet_starts:
                if (JudgeIdentityUtils.isIdentityed(StarInfoActivity.this)){
                    Intent intent3 = new Intent(StarInfoActivity.this,MeetStarActivity.class);
                    List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(code);
                    if (starInfos.size()!=0){
                        StarInfo starInfo = starInfos.get(0);
                        intent3.putExtra(AppConstant.STAR_HEAD_URL,starInfo.getPic_url());
                        intent3.putExtra(AppConstant.STAR_NAME,starInfo.getName());
                    }
                    intent3.putExtra(AppConstant.BUY_TRANSFER_INTENT_TYPE,1);
                    intent3.putExtra(AppConstant.STAR_CODE,code);
                    startActivity(intent3);
                }
                break;
            case R.id.tv_buy_time:
                Intent intent = new Intent(this,BuyTransferIndentActivity.class);
                intent.putExtra(AppConstant.BUY_TRANSFER_INTENT_TYPE,0);
                List<StarInfo> starInfos = GreenDaoManager.getInstance().queryLove(code);
                if (starInfos.size()!=0){
                    StarInfo starInfo = starInfos.get(0);
                    intent.putExtra(AppConstant.STAR_HEAD_URL,starInfo.getPic_url());
                    intent.putExtra(AppConstant.STAR_NAME,starInfo.getName());
                }
                //intent.putExtra(AppConstant.STAR_WID,wid);
                intent.putExtra(AppConstant.STAR_CODE,code);
                startActivity(intent);
                break;
            case R.id.imag_meesage:
                if (haveStarTime>0){
                    if (JudgeIdentityUtils.isIdentityed(this)) {
                        startActivity(StarCommunicationBookActivity.class);
                    }
                }else {
                    ToastUtils.showShort("您未持有改明星时间，请购买");
                }
                break;

        }
    }
    private int haveStarTime ;
    private void getHaveCodeTime() {
        NetworkAPIFactoryImpl.getInformationAPI().getHaveStarTime(SharePrefUtil.getInstance().getUserId(),
                code, new OnAPIListener<HaveStarTimeBeen>() {
                    @Override
                    public void onError(Throwable ex) {

                    }

                    @Override
                    public void onSuccess(HaveStarTimeBeen haveStarTimeBeen) {
                        haveStarTime = haveStarTimeBeen.getStar_time();
                        LogUtils.loge("持有时间" + haveStarTimeBeen.toString());
                    }
                });
    }
}
