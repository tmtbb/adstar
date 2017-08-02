/*
 * Copyright 2016. SHENQINCI(沈钦赐)<946736079@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudTop.starshare.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.utils.LogUtils;

/**
 * 价格浮点型数据
 */
public class NumberBoubleButton extends LinearLayout implements View.OnClickListener, TextWatcher {
    //库存
    private double mInventory = Double.MAX_VALUE;
    //最大购买数，默认无限制
    private double mBuyMax = Double.MAX_VALUE;
    //最小购买数，默认无限制
    private double mBuyMin = 0.01;
    private EditText mCount;
    private OnWarnListener mOnWarnListener;
    private TextView addButton;
    private TextView subButton;
    private Activity myContext;
    private boolean isEditGetFocus = false;

    public NumberBoubleButton(Context context) {
        this(context, null);
    }

    public NumberBoubleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
//
//    public NumberButton(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout, this);

        addButton = (TextView) findViewById(R.id.button_add);
        addButton.setOnClickListener(this);
        subButton = (TextView) findViewById(R.id.button_sub);
        subButton.setOnClickListener(this);

        mCount = ((EditText) findViewById(R.id.text_count));
        mCount.addTextChangedListener(this);
        mCount.setOnClickListener(this);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberButton);
        boolean editable = typedArray.getBoolean(R.styleable.NumberButton_editable, true);
        int buttonWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_buttonWidth, -1);
        int textWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_textWidth, -1);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.NumberButton_textSize, -1);
        int textColor = typedArray.getColor(R.styleable.NumberButton_textColor, 0xff000000);
        typedArray.recycle();

        setEditable(editable);
        mCount.setTextColor(textColor);
        subButton.setTextColor(textColor);
        addButton.setTextColor(textColor);

        if (textSize > 0)
            mCount.setTextSize(textSize);

        if (buttonWidth > 0) {
            LayoutParams textParams = new LayoutParams(buttonWidth, LayoutParams.MATCH_PARENT);
            subButton.setLayoutParams(textParams);
            addButton.setLayoutParams(textParams);
        }
        if (textWidth > 0) {
            LayoutParams textParams = new LayoutParams(textWidth, LayoutParams.MATCH_PARENT);
            mCount.setLayoutParams(textParams);
        }


        mCount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

//        mCount.setOnFocusChangeListener(new OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                    isEditGetFocus = true;
//                } else {
//                    isEditGetFocus = false;
//                    // 此处为失去焦点时的处理内容
//                }
//            }
//        });
//        mCount.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isEditGetFocus) {
//                    //判断隐藏软键盘是否弹出
//                    if (myContext!=null){
//                        if (isSoftShowing()){
//                            return;
//                        }else {
//                            mCount.setFocusable(false);
//                            mCount.setEnabled(false);
//                            mCount.setFocusableInTouchMode(false);
//                            mCount.clearFocus();
//                            mCount.postDelayed(runnable, 90);
//                            LogUtils.loge("连续点击");
//                        }
//                    }
//                }
//            }
//        });
    }
    private boolean isSoftShowing() {
        //获取当前屏幕内容的高度
        int screenHeight = myContext.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        myContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return screenHeight - rect.bottom != 0;
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mCount.setFocusable(true);
            mCount.setEnabled(true);
            mCount.setFocusableInTouchMode(true);
            mCount.requestFocus();
        }
    };

    public double getFloatNumber() {
        try {
            return Double.valueOf(mCount.getText().toString());
        } catch (NumberFormatException e) {
            LogUtils.loge(e.toString());
        }
        mCount.setText("" + mBuyMin);
        return mBuyMin;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        double floatcount = getFloatNumber();
        LogUtils.loge("点击..." + String.valueOf(floatcount));
        if (id == R.id.button_sub) {
            if (floatcount <= mBuyMin) {
                //超过最小数量
                subButton.setBackgroundResource(R.drawable.subtract_no_can);
            } else {
                subButton.setBackgroundResource(R.drawable.subtract_can);
                addButton.setBackgroundResource(R.drawable.add_can);
                double v1 = floatcount - 0.01;
                String result1 = String.format("%.2f", v1);
                mCount.setText(result1);
            }

        } else if (id == R.id.button_add) {
            LogUtils.loge("1");
            if (floatcount < Math.min(mBuyMax, mInventory)) {
                addButton.setBackgroundResource(R.drawable.add_can);
                subButton.setBackgroundResource(R.drawable.subtract_can);
                double v2 = floatcount + 0.01;
                String result2 = String.format("%.2f", v2);
                mCount.setText(result2);
            } else if (mInventory < mBuyMax) {
                addButton.setBackgroundResource(R.drawable.add_no_can);
                //库存不足
                warningForInventory();
            } else {
                addButton.setBackgroundResource(R.drawable.add_no_can);
                //超过最大购买数
                warningForBuyMax();
            }

        }


    }

    private void onNumberInput() {
        if (getFloatNumber() >= mBuyMax) {
            addButton.setBackgroundResource(R.drawable.add_no_can);
        } else {
            addButton.setBackgroundResource(R.drawable.add_can);
        }
        if (getFloatNumber() <= mBuyMin) {
            subButton.setBackgroundResource(R.drawable.subtract_no_can);
        } else {
            subButton.setBackgroundResource(R.drawable.subtract_can);
        }


        double floatcount = getFloatNumber();
        LogUtils.loge(String.valueOf(floatcount));
        if (floatcount <= 0 || floatcount < mBuyMin) {
            //手动输入
            mCount.setText("" + mBuyMin);
            warningForBuyMin();
            //return;
        }

        double limit = Math.min(mBuyMax, mInventory);
        if (floatcount > limit) {
            //超过了数量
            mCount.setText(limit + "");
            if (mInventory < mBuyMax) {
                //库存不足
                warningForInventory();
            } else {
                //超过最大购买数
                warningForBuyMax();
            }
        }
        if (onChangeContent!=null){
            onChangeContent.onChange(getFloatNumber());
        }
    }

    /**
     * 超过的库存限制
     * Warning for inventory.
     */
    private void warningForInventory() {
        if (mOnWarnListener != null) mOnWarnListener.onWarningForInventory(mInventory);
    }

    /**
     * 超过的最大购买数限制
     * Warning for buy max.
     */
    private void warningForBuyMax() {
        if (mOnWarnListener != null) mOnWarnListener.onWarningForBuyMax(mBuyMax);
    }

    /**
     * 小于最小购买数量
     * Warning for buy min.
     */
    private void warningForBuyMin() {
        if (mOnWarnListener != null) mOnWarnListener.onWarningForBuyMin(mBuyMin);
    }


    private void setEditable(boolean editable) {
        if (editable) {
            mCount.setFocusable(true);
        } else {
            mCount.setFocusable(false);
            mCount.setKeyListener(null);
        }
    }

    public NumberBoubleButton setCurrentNumber(double currentNumber) {
        if (currentNumber <= mBuyMin) {
            mCount.setText(String.valueOf(mBuyMin));
            subButton.setBackgroundResource(R.drawable.subtract_no_can);
        } else {
            subButton.setBackgroundResource(R.drawable.subtract_can);
        }

        if (currentNumber >= mBuyMax) {
            addButton.setBackgroundResource(R.drawable.add_no_can);
        } else {
            addButton.setBackgroundResource(R.drawable.add_can);
        }
        mCount.setText("" + Math.min(Math.min(mBuyMax, mInventory), currentNumber));
        return this;
    }

    public double getInventory() {
        return mInventory;
    }

    public NumberBoubleButton setInventory(double inventory) {
        mInventory = inventory;
        return this;
    }

    public NumberBoubleButton setBuyMin(double BuyMin) {
        mBuyMin = BuyMin;
        return this;
    }

    public double getBuyMax() {
        return mBuyMax;
    }

    public NumberBoubleButton setBuyMax(double buyMax) {
        mBuyMax = buyMax;
        return this;
    }
    public NumberBoubleButton setContext(Activity context){
        myContext = context;
        return this;
    }

    public NumberBoubleButton setOnWarnListener(OnWarnListener onWarnListener) {
        mOnWarnListener = onWarnListener;
        return this;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 3);
                    LogUtils.loge("10");
                    mCount.setText(s);
                    mCount.setSelection(s.length());
                    LogUtils.loge(s.toString());
                }
            }
            if (s.toString().trim().substring(0).equals(".")) {
                s = "0" + s;
                mCount.setText(s);
                mCount.setSelection(2);
                LogUtils.loge("11");
            }

            if (s.toString().startsWith("0")
                    && s.toString().trim().length() > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    mCount.setText(s.subSequence(0, 1));
                    mCount.setSelection(1);
                    LogUtils.loge("12");
                    return;
                }
            }

        onNumberInput();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnWarnListener {
        void onWarningForInventory(double inventory);

        void onWarningForBuyMax(double max);

        void onWarningForBuyMin(double min);
    }
    private OnChangeContentListener onChangeContent ;
    public interface OnChangeContentListener {
        void onChange(double price);
    }

    public void setOnChangeContent(OnChangeContentListener content){
        this.onChangeContent = content ;
    }
}
