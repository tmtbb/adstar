package com.cloudTop.starshare.ui.main.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.been.ResultBeen;
import com.cloudTop.starshare.listener.OnAPIListener;
import com.cloudTop.starshare.networkapi.NetworkAPIFactoryImpl;
import com.cloudTop.starshare.utils.SharePrefUtil;
import com.cloudTop.starshare.utils.ToastUtils;
import com.cloudTop.starshare.widget.EasySwitchButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/17.
 * 定制语音
 */

public class AskToVoiceActivity extends BaseActivity {

    @Bind(R.id.comment)
    EditText comment;
    @Bind(R.id.tv_word_number)
    TextView tv_word_number;
    @Bind(R.id.tv_back)
    TextView tv_back;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.tv_voice_consume_rule)
    TextView tv_voice_consume_rule;
    @Bind(R.id.radio_group)
    RadioGroup radio_group;
    private String star_code;
    private int isPublish = 1; //0代表私有，1代表公开
    private int cType = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ask_voice;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        star_code = intent.getStringExtra("star_code");
        ((EasySwitchButton) findViewById(R.id.esb_button_2)).setOnCheckChangedListener(new MyEasyOnOpenedListener());
        initListener();
    }



    private void initListener() {
        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_word_number.setText(String.valueOf(s.length()));
                if (s.length() >= 100) {
                    ToastUtils.showShort("超过字数范围");
                }
            }
        });
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_1:
                        cType = 0;
                        tv_voice_consume_rule.setText(String.format(getString(R.string.voice_consume_rule),15));
                        break;
                    case R.id.rb_2:
                        cType = 1;
                        tv_voice_consume_rule.setText(String.format(getString(R.string.voice_consume_rule),30));
                        break;
                    case R.id.rb_3:
                        cType = 3;
                        tv_voice_consume_rule.setText(String.format(getString(R.string.voice_consume_rule),60));
                        break;
                }
            }
        });
    }

    /**
     * EasySwitchButton 的点击事件
     *
     * @author SvenHe
     */
    private class MyEasyOnOpenedListener implements
            EasySwitchButton.OnOpenedListener {

        @Override
        public void onChecked(View v, boolean isOpened) {
            if (isOpened) {
                isPublish = 1;
            } else {
                isPublish = 0;
            }
        }
    }

    @OnClick({R.id.tv_back, R.id.tv_right})
    public void onClickSwtich(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_right:
                PostQuestions();
                break;

        }
    }

    private void PostQuestions() {
        if (TextUtils.isEmpty(comment.getText().toString().trim())){
            ToastUtils.showShort("请输入定制问题内容");
            return;
        }
        NetworkAPIFactoryImpl.getInformationAPI().postQuestion(SharePrefUtil.getInstance().getUserId(),
                star_code, SharePrefUtil.getInstance().getToken(), 2, isPublish, cType, comment.getText().toString().trim(), "",0,"",
                new OnAPIListener<ResultBeen>() {
                    @Override
                    public void onError(Throwable ex) {

                    }

                    @Override
                    public void onSuccess(ResultBeen been) {
                        if (been!=null){
                            if (been.getResult()==0){
                                ToastUtils.showShort("定制语音发布成功");
                                comment.getText().clear();
                            }else if (been.getResult()==1){
                                ToastUtils.showShort("您持有该网红的时间不足");
                            }
                        }
                    }
                });
    }
}
