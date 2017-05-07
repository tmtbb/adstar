package com.yundian.star.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.utils.CountUtil;
import com.yundian.star.utils.FormatUtil;
import com.yundian.star.utils.StringUtil;
import com.yundian.star.utils.ToastUtils;

import butterknife.Bind;


/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2016-11-26 15:00
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class WPEditText extends BaseLinearLayout {
    @Bind(R.id.editIcon)
    protected ImageView editIcon;
    @Bind(R.id.editEye)
    protected ImageView editEye;
    @Bind(R.id.editText)
    protected EditText editText;
    @Bind(R.id.rightText)
    protected Button rightText;
    @Bind(R.id.leftText)
    protected TextView leftText;
    @Bind(R.id.lineView)
    protected View lineView;

    public WPEditText(Context context) {
        super(context);
    }
    public WPEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected int layoutId() {
        return R.layout.ly_edit_text;
    }
    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WPEditText);
            if (typedArray.hasValue(R.styleable.WPEditText_edit_icon))
                editIcon.setImageResource(typedArray.getResourceId(R.styleable.WPEditText_edit_icon, R.mipmap.ic_launcher));
            if (typedArray.hasValue(R.styleable.WPEditText_edit_show_eye))
                editEye.setVisibility(typedArray.getBoolean(R.styleable.WPEditText_edit_show_eye, false) ? VISIBLE : GONE);
            if (typedArray.hasValue(R.styleable.WPEditText_edit_show_icon))
                editIcon.setVisibility(typedArray.getBoolean(R.styleable.WPEditText_edit_show_icon, false) ? VISIBLE : GONE);

            if (typedArray.hasValue(R.styleable.WPEditText_edit_show_right_text))
                rightText.setVisibility(typedArray.getBoolean(R.styleable.WPEditText_edit_show_right_text, false) ? VISIBLE : GONE);
            if (typedArray.hasValue(R.styleable.WPEditText_edit_right_text))
                rightText.setText(typedArray.getString(R.styleable.WPEditText_edit_right_text));

            if (typedArray.hasValue(R.styleable.WPEditText_edit_left_text)) {
                leftText.setVisibility(VISIBLE);
                lineView.setVisibility(VISIBLE);
                leftText.setText(typedArray.getString(R.styleable.WPEditText_edit_left_text));
            }


            if (typedArray.hasValue(R.styleable.WPEditText_edit_pwd))
                if (typedArray.getBoolean(R.styleable.WPEditText_edit_pwd, false)) {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            if (typedArray.hasValue(R.styleable.WPEditText_edit_hint))
                editText.setHint(typedArray.getString(R.styleable.WPEditText_edit_hint));
            if (typedArray != null)
                typedArray.recycle();
            typedArray = null;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        if (editEye != null && editEye.getVisibility() == VISIBLE) {
            editEye.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchPasswordState();
                }
            });
        }
        if (rightText != null && rightText.getVisibility() == VISIBLE) {
            rightText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    String text = (String) rightText.getTag();
                    if (StringUtil.isEmpty(text)) {
                        ToastUtils.show("请输入手机号码", 2000);
                    } else {
                        //Utils.isMobile(text)
                        if (FormatUtil.isMobileNO(text))
                            new CountUtil(rightText).start();
                        else
                            ToastUtils.show("请输入正确的手机号码", 2000);
                    }

                }
            });
        }


    }

    public void switchPasswordState() {
        if (editText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editEye.setImageResource(R.drawable.icon_password_2);
        } else {
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editEye.setImageResource(R.drawable.icon_password_1);
        }
        editText.setSelection(editText.getText().length());
    }

    public String getEditTextString() {
        if (editText != null) {
            return editText.getText().toString().trim();
        }

        return "";
    }

    public View getRightText() {
        return rightText;
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        if (editText != null)
            editText.addTextChangedListener(textWatcher);
    }

    public EditText getEditText() {
        return editText;
    }

    public void setInputType(int type) {
        if (editText != null) {
            editText.setInputType(type);
        }
    }
}
