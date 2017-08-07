package com.cloudTop.starshare.ui.main.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.BaseFragment;
import com.cloudTop.starshare.been.AssetDetailsBean;
import com.cloudTop.starshare.been.EventBusMessage;
import com.cloudTop.starshare.been.ExpendLineBean;
import com.cloudTop.starshare.been.IdentityInfoBean;
import com.cloudTop.starshare.been.RegisterReturnBeen;
import com.cloudTop.starshare.been.ReturnAmountBean;
import com.cloudTop.starshare.been.StarInfoReturnBean;
import com.cloudTop.starshare.greendao.GreenDaoManager;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.ui.main.activity.BookingStarActivity;
import com.cloudTop.starshare.ui.main.activity.CustomerServiceActivity;
import com.cloudTop.starshare.ui.main.activity.DifferAnswerActivity;
import com.cloudTop.starshare.ui.main.activity.GeneralSettingsActivity;
import com.cloudTop.starshare.ui.main.activity.TransactionDetailActivity;
import com.cloudTop.starshare.ui.main.activity.UserAssetsManageActivity;
import com.cloudTop.starshare.ui.main.activity.UserSettingActivity;
import com.cloudTop.starshare.ui.view.RoundImageView;
import com.cloudTop.starshare.ui.wangyi.common.util.sys.InstallUtil;
import com.cloudTop.starshare.utils.DisplayUtil;
import com.cloudTop.starshare.utils.ImageLoaderUtils;
import com.cloudTop.starshare.utils.LogUtils;
import com.cloudTop.starshare.utils.QRCodeUtil;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.utils.ToastUtils;
import com.cloudTop.starshare.utils.ViewConcurrencyUtils;
import com.netease.nimlib.jsbridge.util.LogUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

import static com.cloudTop.starshare.R.id.img_zxing;

/**
 * Created by Administrator on 2017/5/5.
 *  个人中心
 */

public class UserInfoFragment extends BaseFragment {


    @Bind(R.id.iv_user_info_bg)
    ImageView userInfoBg;
    @Bind(R.id.tv_user_name)
    TextView userName;
    @Bind(R.id.tv_user_total_assets)
    TextView userTotalAssets;
    @Bind(R.id.tv_order_star)
    TextView userOrderStar;
    @Bind(R.id.zxing_butten)
    TextView zxing_butten;
    @Bind(R.id.ll_me_deal)
    LinearLayout ll_me_deal;
    @Bind(R.id.headImage)
    RoundImageView headImage;
    @Bind(R.id.ll_user_money_bag)
    LinearLayout moneyBag;
    @Bind(R.id.ll_user_order_star)
    LinearLayout orderStar;
    @Bind(R.id.ll_customer_service)
    LinearLayout customerService;
    @Bind(R.id.ll_common_problem)
    LinearLayout commonProblem;
    @Bind(R.id.ll_general_settings)
    LinearLayout generalSettings;
    @Bind(R.id.btn_my_referee)
    Button myReferee;
    @Bind(R.id.iv_star_talk)
    ImageView starTalk;
    @Bind(R.id.red_talk_tip)
    View redTalkTip;
    private boolean flag = true;
    private TextView version;
    private TextView tv_acc_num;
    private Bitmap bitmap;
    private String expendLine = "";
    TextView tv_success_num;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_user_info;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        initFindById();
//        if (!TextUtils.isEmpty(SharePrefUtil.getInstance().getPhoneNum())) {
//            initData();
//            LogUtils.loge("---登陆成功了,更新数据和请求余额");
//            requestBalance();
//        }
        checkunReadMsg();
        testStar();
        getExpendLine();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String versionName = SharePrefUtil.getInstance().getVersion();
                if (TextUtils.isEmpty(versionName)) {
                    String versionName1 = InstallUtil.getVersionName(getActivity(), getActivity().getPackageName());
                    LogUtils.loge("versionName1:" + versionName1);
                    version.setText("版本号: " + versionName1);
                } else {
                    version.setText("版本号: " + versionName);
                }
            }
        }, 1000);
    }

    private void getExpendLine() {
        NetworkAPIFactoryImpl.getInformationAPI().getExpendLine("PROMOTION_URL", new OnAPIListener<ExpendLineBean>() {
            @Override
            public void onError(Throwable ex) {

            }

            @Override
            public void onSuccess(ExpendLineBean expendLineBean) {
                if (expendLineBean!=null&&expendLineBean.getParam_value()!=null){
                    LogUtils.loge("推广链接"+expendLineBean.getParam_value().toString());
                    expendLine = expendLineBean.getParam_value();
                }
            }
        });
    }

    private void initFindById() {
        version = (TextView) rootView.findViewById(R.id.tv_version);
        tv_acc_num = (TextView) rootView.findViewById(R.id.tv_acc_num);
        tv_success_num = (TextView) rootView.findViewById(R.id.tv_success_num);
    }

    private void initData() {
//        String referee = SharePrefUtil.getInstance().getUserReferee();
//        if (referee == null) {
//            myReferee.setText(getString(R.string.my_referee));
//        } else {
//            myReferee.setText(String.format(getString(R.string.dialog_title_referee2), referee));
//        }
        String userPhotoUrl = SharePrefUtil.getInstance().getUserPhotoUrl();
        if (TextUtils.isEmpty(userPhotoUrl)) {
            headImage.setImageResource(R.drawable.user_default_head);
        } else {
            ImageLoaderUtils.displaySmallPhotoRound(getContext(), headImage, userPhotoUrl);
        }
        String userNickName = SharePrefUtil.getInstance().getUserNickName();
        if (TextUtils.isEmpty(userNickName)) {
            userName.setText(SharePrefUtil.getInstance().getPhoneNum());
        } else {
            userName.setText(userNickName);
        }

        userOrderStar.setText(SharePrefUtil.getInstance().getOrderStar() + "");
    }


    @OnClick({R.id.iv_user_info_bg, R.id.headImage, R.id.ll_user_money_bag, R.id.ll_user_order_star,
            R.id.ll_customer_service, R.id.ll_common_problem, R.id.ll_general_settings, R.id.btn_my_referee, R.id.iv_star_talk,R.id.ll_me_deal
    ,R.id.zxing_butten})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_info_bg:
//                requestBalance();
                break;
            case R.id.headImage:
                ViewConcurrencyUtils.preventConcurrency();  //防止并发
                startActivity(UserSettingActivity.class);
                break;
            case R.id.ll_user_money_bag:
                ViewConcurrencyUtils.preventConcurrency();  //防止并发
                startActivity(UserAssetsManageActivity.class);
                break;
            case R.id.ll_user_order_star:
                ViewConcurrencyUtils.preventConcurrency();  //防止并发
                startActivity(BookingStarActivity.class);
                break;
            case R.id.ll_customer_service:
                ViewConcurrencyUtils.preventConcurrency();  //防止并发
                startActivity(CustomerServiceActivity.class);
                break;
            case R.id.ll_common_problem:
                break;
            case R.id.ll_general_settings:
                ViewConcurrencyUtils.preventConcurrency();  //防止并发
                startActivity(GeneralSettingsActivity.class);
                break;
            case R.id.btn_my_referee:
                ViewConcurrencyUtils.preventConcurrency();  //防止并发
                showDialog();
                break;

            case R.id.iv_star_talk:
                ViewConcurrencyUtils.preventConcurrency();  //防止并发
                startActivity(DifferAnswerActivity.class);
                break;
            case R.id.ll_me_deal:
                ViewConcurrencyUtils.preventConcurrency();  //防止并发
                Intent intent = new Intent(getActivity(),TransactionDetailActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.zxing_butten:
                ViewConcurrencyUtils.preventConcurrency();  //防止并发
                showPopupWindow();
                break;
        }
    }

    private void checkunReadMsg() {
        int unreadNum = NIMClient.getService(MsgService.class).getTotalUnreadCount();
        if (unreadNum > 0) {
            starTalk.setImageResource(R.drawable.news_remind_unread);
//            redTalkTip.setVisibility(View.VISIBLE);
        } else {
            starTalk.setImageResource(R.drawable.news_remind_no);
//            redTalkTip.setVisibility(View.GONE);
        }
    }

    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogLayout = inflater.inflate(R.layout.dialog_user_referee, null);
        final EditText edtInput = (EditText) dialogLayout.findViewById(R.id.et_input_referee);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setView(dialogLayout)
                .setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (TextUtils.isEmpty(edtInput.getText().toString().trim())) {
                                    ToastUtils.showShort(getResources().getString(R.string.dialog_input_tip));
                                } else {
                                    SharePrefUtil.getInstance().putUserReferee(edtInput.getText().toString().trim());
                                    myReferee.setText(String.format(getString(R.string.dialog_title_referee2), edtInput.getText().toString().trim()));
                                    dialog.dismiss();
                                }
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        });
        builder.show();
    }

    //接收消息
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void ReciveMessageEventBus(EventBusMessage eventBusMessage) {
        switch (eventBusMessage.Message) {
            case 1:  //成功
                LogUtils.loge("用户登录成功了，请刷新数据----------");
                requestBalance();
                requestIdentity();
                requestStarCount();
                requestReturnMount();
                break;
        }
    }

    private void requestReturnMount() {
        NetworkAPIFactoryImpl.getDealAPI().getReturnAmount(SharePrefUtil.getInstance().getUserId(),new OnAPIListener<ReturnAmountBean>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("佣金-----------");
            }

            @Override
            public void onSuccess(ReturnAmountBean returnAmountBean) {
                LogUtils.loge("佣金-----------" + returnAmountBean.toString());
                if (returnAmountBean.getResult()==1){
                    //tv_current_price.setText(String.format("%.2f", priceinfoBean.getCurrentPrice()));
                    tv_acc_num.setText(String.format("%.2f", returnAmountBean.getTotal_amount()));
                    tv_success_num.setText(String.valueOf(returnAmountBean.getTotal_num()));
                }
            }
        });
    }

    private void requestIdentity() {
        NetworkAPIFactoryImpl.getDealAPI().identity(new OnAPIListener<IdentityInfoBean>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("实名信息失败-----------");
            }

            @Override
            public void onSuccess(IdentityInfoBean identityInfoBean) {
                LogUtils.loge("实名信息成功-----------" + identityInfoBean.toString());
                SharePrefUtil.getInstance().setRealName(identityInfoBean.getRealname());
                SharePrefUtil.getInstance().setIdnum(identityInfoBean.getId_card());
            }
        });
    }

    private static boolean isSaveWangYi = false;

    private void requestBalance() {
        NetworkAPIFactoryImpl.getDealAPI().balance(new OnAPIListener<AssetDetailsBean>() {
            @Override
            public void onSuccess(AssetDetailsBean bean) {
                LogUtils.loge("余额请求成功:" + bean.toString());
                userTotalAssets.setText(String.format("%.2f",bean.getBalance()));
                if (bean.getIs_setpwd() != -100) {
                    SharePrefUtil.getInstance().saveAssetInfo(bean);
                }
                if (!TextUtils.isEmpty(bean.getHead_url()) && !TextUtils.isEmpty(bean.getNick_name())) {
                    SharePrefUtil.getInstance().putUserNickName(bean.getNick_name());
                    SharePrefUtil.getInstance().putUserPhotoUrl(bean.getHead_url());
                }

                // SharePrefUtil.getInstance().saveAssetInfo(bean);
                if (!TextUtils.isEmpty(SharePrefUtil.getInstance().getUserNickName()) && isSaveWangYi == false) {
                    updateWangYiInfo();
                    isSaveWangYi = true;
                }
                initData();
            }

            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("余额请求失败:" + ex.getMessage());
            }
        });
    }

    //修改网易头像和昵称
    private void updateWangYiInfo() {
        Map<UserInfoFieldEnum, Object> fields = new HashMap<>(1);
        fields.put(UserInfoFieldEnum.Name, SharePrefUtil.getInstance().getUserNickName());
        fields.put(UserInfoFieldEnum.AVATAR, SharePrefUtil.getInstance().getUserPhotoUrl());
        LogUtils.loge("网易云修改名字昵称" + SharePrefUtil.getInstance().getUserNickName() +
                SharePrefUtil.getInstance().getUserPhotoUrl());
        NIMClient.getService(UserService.class).updateUserInfo(fields)
                .setCallback(new RequestCallbackWrapper<Void>() {
                    @Override
                    public void onResult(int i, Void aVoid, Throwable throwable) {
                        LogUtils.loge(i + "网易云修改名字昵称");
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (flag) {
            EventBus.getDefault().register(this); // EventBus注册广播()  界面可见注册广播
            flag = false;//更改标记,使其不会再进行多次注册
        }
        if (!TextUtils.isEmpty(SharePrefUtil.getInstance().getPhoneNum())) {   //如果登陆后,界面可见需要刷新数据
            requestBalance();
        }
        requestStarCount();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    private void requestStarCount() {
        NetworkAPIFactoryImpl.getUserAPI().starCount(new OnAPIListener<RegisterReturnBeen>() {
            @Override
            public void onError(Throwable ex) {
            }

            @Override
            public void onSuccess(RegisterReturnBeen registerReturnBeen) {
                LogUtils.loge("明星数量:" + registerReturnBeen.toString());
                userOrderStar.setText(registerReturnBeen.getAmount() + "");
            }
        });
    }

    private void testStar() {
        NetworkAPIFactoryImpl.getInformationAPI().starInfo("17682310986", "123", 1, new OnAPIListener<StarInfoReturnBean>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("明星列表失败----.-----------");
            }

            @Override
            public void onSuccess(StarInfoReturnBean starInfoReturnBean) {
                LogUtils.loge("明星列表成功---------");
                if (starInfoReturnBean.getResult() == 1) {
                    GreenDaoManager.getInstance().saveNoteLists(starInfoReturnBean.getList());
                }
                LogUtils.loge("插入成功");
//                List<StarInfo> starInfos = GreenDaoManager.getInstance().loadAllNote();
//                for (int i = 0; i < starInfos.size(); i++) {
//                    LogUtils.loge("查询的数据为:"+starInfos.get(i).getName()+"索引:"+i+"价格:"+starInfos.get(i).getPrice());
//                }
            }
        });
    }

    private boolean isFirstSave = false ;
    private void showPopupWindow() {
        View popView = LayoutInflater.from(getContext()).inflate(R.layout.popwindow_zxing_show, null);
        final ImageView imageView = (ImageView) popView.findViewById(img_zxing);
        final TextView close = (TextView) popView.findViewById(R.id.close);
        if (expendLine.contains("?uid=")){

        }else {
            expendLine = expendLine+"?uid="+SharePrefUtil.getInstance().getUserId();
        }
        LogUtils.loge("expendLine:"+expendLine);
        if (bitmap==null){
            bitmap = QRCodeUtil.createQRCode(expendLine, DisplayUtil.getScreenWidth(getContext()) / 2);
        }
        imageView.setImageBitmap(bitmap);
        final PopupWindow popupWindow = new PopupWindow(getContext());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(popView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x33000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                saveImageToGallery(getContext(), bitmap);
                return false;
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
    }

    /**
     * 保存图片到图库
     *
     * @param context
     * @param bmp
     */
    public  void saveImageToGallery(Context context, Bitmap bmp) {
        if (isFirstSave){
            ToastUtils.showShort("二维码已保存");
            return;
        }
        isFirstSave = true;
        File appDir = new File(Environment.getExternalStorageDirectory(), "XingXiang");   // 首先保存图片
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LogUtil.e("二维码已保存至:" + Environment.getExternalStorageDirectory() + "/XingXiang/" + "目录文件夹下");
        ToastUtils.showShort("二维码已保存");
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

}
