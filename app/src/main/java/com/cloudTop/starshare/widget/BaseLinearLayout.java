package com.cloudTop.starshare.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.cloudTop.starshare.listener.OnChildViewClickListener;

import butterknife.ButterKnife;


public abstract class BaseLinearLayout extends LinearLayout {
    protected OnChildViewClickListener onChildViewClickListener;
    protected View rootView;
    protected Context context;

    public void setOnChildViewClickListener(OnChildViewClickListener onChildViewClickListener) {
        this.onChildViewClickListener = onChildViewClickListener;
    }

    protected void onChildViewClick(View childView, int action, Object obj) {
        if (onChildViewClickListener != null)
            onChildViewClickListener.onChildViewClick(childView, action, obj);
    }

    protected void onChildViewClick(View childView, int action) {
        onChildViewClick(childView, action, null);
    }


    public BaseLinearLayout(Context context) {
        super(context);
        init(context, null);
    }

    public BaseLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    protected abstract int layoutId();

    protected void init(Context context, AttributeSet attrs) {
        this.context = context;
        if (layoutId() != 0) {
            rootView = LayoutInflater.from(context).inflate(layoutId(), this, true);
            //LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
            //addView(view,layoutParams);
            if (rootView != null)
                //x.view().inject(this, rootView);
                ButterKnife.bind(this, rootView);
            if (attrs != null)
                initAttributeSet(attrs);
        }
        initView();
        initListener();
        initData();
    }

    protected void initAttributeSet(AttributeSet attrs) {

    }

    protected void initView() {

    }

    protected void initListener() {

    }

    protected void initData() {

    }


}
