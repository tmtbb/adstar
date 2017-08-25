package com.cloudTop.starshare.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


/**
 * Created by Administrator on 2017/2/27.
 */
public class NumberUtils {
    /**
     * 四舍五入,保留2位小数
     */
    public static String halfAdjust2(double number) {
        if (number < 0.005){
            return "0.00";
        }
        return String.format("%.2f", number - 0.005);
    }

    /**
     * 四舍五入,保留4位小数
     */
    public static String halfAdjust4(double number) {
        return String.format("%.4f", number);
    }

    /**
     * 四舍五入,保留5位小数
     */
    public static String halfAdjust5(double number) {
        return String.format("%.5f", number);
    }

    /**
     * 四舍五入,保留6位小数
     */
    public static String halfAdjust6(double number) {
        return String.format("%.6f", number);
    }

    /**
     * 设置EditText 输入的小数的位数
     *
     * @param editText       editText对象
     * @param DECIMAL_DIGITS 小数位数(1,2,....)
     */
    public static void setEditTextPoint(final EditText editText, final int DECIMAL_DIGITS) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > DECIMAL_DIGITS) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + DECIMAL_DIGITS + 1);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 格式化银行卡 加*
     * 3749 **** **** 330
     *
     * @param cardNo 银行卡
     * @return 3749 **** **** 330
     */
    public static String formatCard(String cardNo) {
        String card = "**** **** **** ";
        card += cardNo.substring(cardNo.length() - 4);
        return card;
    }

}
