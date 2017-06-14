//package com.yundian.star.ui.view;
//
//import android.content.Context;
//import android.support.annotation.AttrRes;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//
//import com.yundian.star.R;
//
///**
// * Created by sll on 2017/6/12.
// */
//
//public class ErrorViewLayout extends FrameLayout {
//
//    public ErrorViewLayout(@NonNull Context context) {
//        super(context);
//        init(context, null);
//    }
//
//    public ErrorViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        init(context, attrs);
//    }
//
//    protected Context context;
//
//    protected void init(Context context, AttributeSet attrs) {
//        this.context = context;
//        if (layoutId() != 0) {
////            FrameLayout layout= new FrameLayout(this);//定义框架布局器
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
//                    ViewGroup.LayoutParams.FILL_PARENT);//定义框架布局器参数
////            FrameLayout.LayoutParams tparams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
////                    ViewGroup.LayoutParams.WRAP_CONTENT);//定义显示组件参数
////            TextView txtview = new TextView(this);//定义组件
////            txtview.setText("欢迎使用框架布局");
////            EditText et = new EditText(this);
////            et.setText("请选择你喜欢的布局");
////            Button but=new Button(this);
////            but.setText("按我");
////            layout.addView(txtview, tparams);//添加组件
////            layout.addView(et, tparams);
////            layout.addView(but, tparams);
////
////
////            setContentView(layout,params);//向屏幕上添加布局显示器
//
//
//            View rootView = LayoutInflater.from(context).inflate(layoutId(), this, true);
//            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//            this.addView(rootView, params);
////            if (rootView != null)
////                x.view().inject(this, rootView);
//
//        }
//    }
//
//    protected int layoutId() {
//        return R.layout.dialog_identity_authentivation;
//    }
//
//
//}
