package com.cloudTop.starshare.ui.main.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.base.BaseActivity;
import com.cloudTop.starshare.utils.ToastUtils;
import com.cloudTop.starshare.widget.EasySwitchButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/17.
 * 向他提问
 */

public class AskToAnswersActivity extends BaseActivity{

    @Bind(R.id.comment)
    EditText comment;
    @Bind(R.id.tv_word_number)
    TextView tv_word_number;
    @Bind(R.id.tv_back)
    TextView tv_back;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.img_video)
    ImageView img_video;
    @Override
    public int getLayoutId() {
        return R.layout.activity_ask_answers;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        String star_code = intent.getStringExtra("star_code");
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
                if (s.length()>=100){
                    ToastUtils.showShort("超过字数范围");
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
            if (isOpened){

            }else {

            }
        }
    }

    @OnClick({R.id.tv_back,R.id.tv_right,R.id.img_video})
    public void onClickSwtich(View view){
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_right:

                break;
            case R.id.img_video:
                Intent intent = new Intent(this, VideoRecordActivity.class);
               // intent.putExtra("PreviewSizeLevel", "480P");
               // intent.putExtra("PreviewSizeRatio", "16:9");
               // intent.putExtra("EncodingSizeLevel", "640x360");
               // intent.putExtra("EncodingBitrateLevel", "1000Kbps");
                startActivity(intent);
                break;
        }
    }
}
