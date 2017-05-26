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

package com.yundian.star.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yundian.star.R;
import com.yundian.star.utils.LogUtils;

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

        TextView addButton = (TextView) findViewById(R.id.button_add);
        addButton.setOnClickListener(this);
        TextView subButton = (TextView) findViewById(R.id.button_sub);
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
        if (isDoubleType) {

            mCount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            //mCount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            mCount.setKeyListener(new DigitsKeyListener(false,true));
            // mCount.setInputType(InputType.TYPE_CLASS_NUMBER);

            /*mCount.setKeyListener(new NumberKeyListener() {
                @Override
                protected char[] getAcceptedChars() {
                    char[] numberChars = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0','.'};
                    return numberChars;
                }

                @Override
                public int getInputType() {
                    return android.text.InputType.TYPE_CLASS_PHONE;
                }
            });*/


        }
    }

    public int getNumber() {
        try {
            return Integer.parseInt(mCount.getText().toString());
        } catch (NumberFormatException e) {
        }
        mCount.setText("" + mBuyMin);
        return mBuyMin;
    }

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
        if (isDoubleType) {
            double floatcount = getFloatNumber();
            LogUtils.loge("点击..."+String.valueOf(floatcount));
            if (id == R.id.button_sub) {
                if (floatcount <= mBuyMin) {
                    //超过最小数量
                    warningForBuyMin();
                } else {
                    double v1 = floatcount-0.01;
                    String result1 = String .format("%.2f",v1);
                    mCount.setText(result1);
                }

            } else if (id == R.id.button_add) {
                LogUtils.loge("1");
                if (floatcount < Math.min(mBuyMax, mInventory)) {
                    double v2 = floatcount+0.01;
                    String result2 = String .format("%.2f",v2);
                    mCount.setText(result2);
                } else if (mInventory < mBuyMax) {
                    //库存不足
                    warningForInventory();
                } else {
                    //超过最大购买数
                    warningForBuyMax();
                }

            } else if (id == R.id.text_count) {
                mCount.setSelection(mCount.getText().toString().length());
            }

        } else {
            int count = getNumber();
            if (id == R.id.button_sub) {
                if (count <= mBuyMin) {
                    //超过最小数量
                    warningForBuyMin();
                } else {
                    mCount.setText("" + (count - 1));
                }

            } else if (id == R.id.button_add) {
                if (count < Math.min(mBuyMax, mInventory)) {
                    //正常添加
                    mCount.setText("" + (count + 1));
                } else if (mInventory < mBuyMax) {
                    //库存不足
                    warningForInventory();
                } else {
                    //超过最大购买数
                    warningForBuyMax();
                }

            } else if (id == R.id.text_count) {
                mCount.setSelection(mCount.getText().toString().length());
            }
        }
    }

    private void onNumberInput() {
        if (isDoubleType) {
            double floatcount = getFloatNumber();
            LogUtils.loge(String.valueOf(floatcount));
            if (floatcount <= 0 || floatcount < mBuyMin) {
                //手动输入
                mCount.setText("" + mBuyMin);
                warningForBuyMin();
                //return;
            }

            int limit = Math.min(mBuyMax, mInventory);
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
        } else {
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
            /*if (isDoubleType){
            }else {
                mCount.setKeyListener(new DigitsKeyListener());
            }*/

        } else {
            mCount.setFocusable(false);
            mCount.setKeyListener(null);
        }
    }

    public NumberButton setCurrentNumber(int currentNumber) {
        if (currentNumber < 1) mCount.setText("1");
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isDoubleType) {
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
        }
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

    public NumberButton setDoubleType(boolean isDouble) {
        isDoubleType = isDouble;
        return this;
    }

}
