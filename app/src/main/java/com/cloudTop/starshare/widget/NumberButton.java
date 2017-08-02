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
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudTop.starshare.R;

/**
 * 购物车商品数量、增加和减少控制按钮。
 */
public class NumberButton extends LinearLayout implements View.OnClickListener, TextWatcher {
    //库存
    private int mInventory = Integer.MAX_VALUE;
    //最大购买数，默认无限制
    private int mBuyMax = Integer.MAX_VALUE;
    //最小购买数，默认无限制
    private int mBuyMin = 1;
    private EditText mCount;
    private OnWarnListener mOnWarnListener;
    private boolean isDoubleType = false;
    private TextView addButton;
    private TextView subButton;
    private Activity myContext;
    private boolean isEditGetFocus = false;

    public NumberButton(Context context) {
        this(context, null);
    }

    public NumberButton(Context context, AttributeSet attrs) {
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
        mCount.setKeyListener(new DigitsKeyListener(false, true));
//        mCount.setOnFocusChangeListener(new android.view.View.
//                OnFocusChangeListener() {
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
//                // mCount.requestFocus();
//            }
//        });
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

    public int getNumber() {
        try {
            return Integer.parseInt(mCount.getText().toString());
        } catch (NumberFormatException e) {
        }
        mCount.setText("" + mBuyMin);
        return mBuyMin;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        int count = getNumber();
        if (id == R.id.button_sub) {
            if (count <= mBuyMin) {
                //超过最小数量
                subButton.setBackgroundResource(R.drawable.subtract_no_can);
            } else {
                subButton.setBackgroundResource(R.drawable.subtract_can);
                addButton.setBackgroundResource(R.drawable.add_can);
                mCount.setText("" + (count - 1));
            }

        } else if (id == R.id.button_add) {
            if (count < Math.min(mBuyMax, mInventory)) {
                //正常添加
                addButton.setBackgroundResource(R.drawable.add_can);
                subButton.setBackgroundResource(R.drawable.subtract_can);
                mCount.setText("" + (count + 1));
            } else if (mInventory < mBuyMax) {
                addButton.setBackgroundResource(R.drawable.add_no_can);
                //库存不足
                warningForInventory();
            } else {
                addButton.setBackgroundResource(R.drawable.add_no_can);
                //超过最大购买数
                warningForBuyMax();
            }

        } /*else if (id == R.id.text_count) {
                mCount.setSelection(mCount.getText().toString().length());
            }*/

    }

    private void onNumberInput() {
        if (getNumber() >= mBuyMax) {
            addButton.setBackgroundResource(R.drawable.add_no_can);
        } else {
            addButton.setBackgroundResource(R.drawable.add_can);
        }
        if (getNumber() <= mBuyMin) {
            subButton.setBackgroundResource(R.drawable.subtract_no_can);
        } else {
            subButton.setBackgroundResource(R.drawable.subtract_can);
        }


        int count = getNumber();
        if (count <= 0 || count < mBuyMin) {
            //手动输入
            mCount.setText("" + mBuyMin);
            warningForBuyMin();
            //return;
        }

        int limit = Math.min(mBuyMax, mInventory);
        if (count > limit) {
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
            onChangeContent.onChange(getNumber());
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

    public NumberButton setCurrentNumber(int currentNumber) {
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

    public int getInventory() {
        return mInventory;
    }

    public NumberButton setInventory(int inventory) {
        mInventory = inventory;
        return this;
    }

    public NumberButton setBuyMin(int BuyMin) {
        mBuyMin = BuyMin;
        return this;
    }

    public int getBuyMax() {
        return mBuyMax;
    }

    public NumberButton setBuyMax(int buyMax) {
        mBuyMax = buyMax;
        return this;
    }

    public NumberButton setOnWarnListener(OnWarnListener onWarnListener) {
        mOnWarnListener = onWarnListener;
        return this;
    }
    public NumberButton setContext(Activity context){
        myContext = context;
        return this;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onNumberInput();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnWarnListener {
        void onWarningForInventory(int inventory);

        void onWarningForBuyMax(int max);

        void onWarningForBuyMin(int min);
    }

    private OnChangeContentListener onChangeContent ;
    public interface OnChangeContentListener {
        void onChange(int num);
    }

    public void setOnChangeContent(OnChangeContentListener content){
        this.onChangeContent = content ;
    }
    private boolean isSoftShowing() {
        //获取当前屏幕内容的高度
        int screenHeight = myContext.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        myContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return screenHeight - rect.bottom != 0;
    }
}
