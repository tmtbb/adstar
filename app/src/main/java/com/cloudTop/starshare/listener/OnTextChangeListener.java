package com.cloudTop.starshare.listener;

import android.text.TextWatcher;

/**
 * Created by Benjamin on 17/1/13.
 */

public interface OnTextChangeListener {
    void addITextChangedListener(TextWatcher textWatcher);

    String getEditTextString();
}
