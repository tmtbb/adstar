package com.yundian.star.ui.main.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.nim.uikit.common.media.picker.PickImageHelper;
import com.netease.nim.uikit.session.constant.Extras;
import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.been.IdentityInfoBean;
import com.yundian.star.been.RequestResultBean;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.NetworkAPIFactoryImpl;
import com.yundian.star.ui.view.RoundImageView;
import com.yundian.star.utils.FormatUtil;
import com.yundian.star.utils.ImageLoaderUtils;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.SharePrefUtil;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/23.
 */

public class UserSettingActivity extends BaseActivity {
    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.headImage)
    RoundImageView headImage;
    @Bind(R.id.ll_user_phone)
    LinearLayout userPhone;
    @Bind(R.id.ll_user_pet_name)
    LinearLayout userPetName;
    @Bind(R.id.ll_user_name)
    LinearLayout userName;
    @Bind(R.id.ll_user_card_number)
    LinearLayout userCardNumber;
    @Bind(R.id.iv_imageview)
    ImageView imageView;
    @Bind(R.id.ll_user_head_icon)
    LinearLayout llUserHeadIcon;
    @Bind(R.id.tv_user_phone)
    TextView tvUserPhone;
    @Bind(R.id.tv_user_pet_name)
    TextView tvUserPetName;
    @Bind(R.id.tv_user_real_name)
    TextView tvUserRealName;
    @Bind(R.id.tv_user_card_number)
    TextView tvUserCardNumber;
    @Bind(R.id.ll_set_user_info)
    LinearLayout llSetUserInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_setting;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        ntTitle.setTitleText("个人信息");
        initData();
    }

    private void initData() {
        requestIdentity();
        tvUserPhone.setText(FormatUtil.formatPhone(SharePrefUtil.getInstance().getPhoneNum()));
        String userNickName = SharePrefUtil.getInstance().getUserNickName();
        if (TextUtils.isEmpty(userNickName)) {
            tvUserPetName.setText(SharePrefUtil.getInstance().getPhoneNum());
        } else {
            tvUserPetName.setText(userNickName);
        }

        String userPhotoUrl = SharePrefUtil.getInstance().getUserPhotoUrl();
        if (TextUtils.isEmpty(userPhotoUrl)) {
            headImage.setImageResource(R.drawable.user_default_head);
        } else {
            ImageLoaderUtils.display(mContext, headImage, userPhotoUrl);
        }
    }


    @OnClick({R.id.headImage, R.id.ll_user_phone, R.id.ll_user_pet_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.headImage:
//                showSelector(R.string.user_info, 100);
                break;
            case R.id.ll_user_phone:
                break;
            case R.id.ll_user_pet_name:
                //修改昵称
                resetNikeName();
                break;
        }
    }

    private void resetNikeName() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogLayout = inflater.inflate(R.layout.dialog_user_referee, null);
        final EditText edtInput = (EditText) dialogLayout.findViewById(R.id.et_input_referee);
        TextView title = (TextView) dialogLayout.findViewById(R.id.dialog_title);
        title.setText("请输入昵称");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(dialogLayout)
                .setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (TextUtils.isEmpty(edtInput.getText().toString().trim())) {
                                    ToastUtils.showShort(getResources().getString(R.string.dialog_input_tip));
                                } else {
                                    //接入昵称
                                    String nikeName = edtInput.getText().toString().trim();
                                    saveNikeName(nikeName);
//                                    SharePrefUtil.getInstance().putUserNickName(edtInput.getText().toString().trim());
//                                    myReferee.setText(String.format(getString(R.string.dialog_title_referee2), edtInput.getText().toString().trim()));
//                                    dialog.dismiss();
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

    private void saveNikeName(final String nikeName) {
        NetworkAPIFactoryImpl.getDealAPI().nikeName(nikeName, new OnAPIListener<RequestResultBean>() {
            @Override
            public void onError(Throwable ex) {
                LogUtils.loge("设置昵称失败----");
                ToastUtils.showStatusView("修改失败",false);
            }

            @Override
            public void onSuccess(RequestResultBean resultBean) {
                if (resultBean.getResult() == 1) {
                    ToastUtils.showStatusView("修改成功", true);
                    SharePrefUtil.getInstance().putUserNickName(nikeName);
                    tvUserPetName.setText(nikeName);
                }
            }
        });
    }

    /**
     * 打开图片选择器
     */
    private void showSelector(int titleId, final int requestCode) {
        PickImageHelper.PickImageOption option = new PickImageHelper.PickImageOption();
        option.titleResId = titleId;
        option.multiSelect = false;
        option.crop = true;
        option.cropOutputImageWidth = 720;
        option.cropOutputImageHeight = 720;
        PickImageHelper.pickImage(UserSettingActivity.this, requestCode, option);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.logd("jieshoudao 拍摄的回调:" + requestCode);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            String path = data.getStringExtra(Extras.EXTRA_FILE_PATH);
            updateAvatar(path);
        }
    }

    /**
     * 更新头像
     */
    private void updateAvatar(final String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }

        File file = new File(path);
        if (file == null) {
            return;
        }
        LogUtils.loge("获取到上传的图片的地址:" + path);
        SharePrefUtil.getInstance().putUserPhotoUrl(path);
        ImageLoaderUtils.display(mContext, headImage, path);
    }

    private void requestIdentity() {
        NetworkAPIFactoryImpl.getDealAPI().identity(new OnAPIListener<IdentityInfoBean>() {
            @Override
            public void onError(Throwable ex) {
                if (tvUserCardNumber!=null||tvUserRealName!=null){
                    LogUtils.loge("实名信息失败-----------");
                    tvUserCardNumber.setText("未实名认证");
                    tvUserRealName.setText("未实名认证");
                }
            }

            @Override
            public void onSuccess(IdentityInfoBean identityInfoBean) {
                LogUtils.loge("实名信息成功-----------" + identityInfoBean.toString());
                if (identityInfoBean != null){
                    tvUserRealName.setText(identityInfoBean.getRealname());
                    tvUserCardNumber.setText(FormatUtil.formatCard(identityInfoBean.getId_card()));
                    SharePrefUtil.getInstance().setRealName(identityInfoBean.getRealname());
                    SharePrefUtil.getInstance().setIdnum(identityInfoBean.getId_card());
                }else{
                    tvUserCardNumber.setText("未实名认证");
                    tvUserRealName.setText("未实名认证");
                }

            }
        });
    }

}
