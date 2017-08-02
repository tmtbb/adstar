package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/3/13.
 */
public class WithDrawCashReturnBean implements Parcelable {
    /**
     * {withdrawCount:"提现金额",
     * withdrawResult:"提现结果",
     * withdrawTime:"提现时间",
     * withdrawBank:"提现银行信息"
     * }
     */
    private double withdrawCount;
    private int withdrawResult;
    private String withdrawTime;
    private String withdrawBank;
    private int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public double getWithdrawCount() {
        return withdrawCount;
    }

    public void setWithdrawCount(double withdrawCount) {
        this.withdrawCount = withdrawCount;
    }

    public int getWithdrawResult() {
        return withdrawResult;
    }

    public void setWithdrawResult(int withdrawResult) {
        this.withdrawResult = withdrawResult;
    }

    public String getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(String withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public String getWithdrawBank() {
        return withdrawBank;
    }

    public void setWithdrawBank(String withdrawBank) {
        this.withdrawBank = withdrawBank;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.withdrawCount);
        dest.writeInt(this.withdrawResult);
        dest.writeString(this.withdrawTime);
        dest.writeString(this.withdrawBank);
        dest.writeInt(this.result);
    }

    public WithDrawCashReturnBean() {
    }

    protected WithDrawCashReturnBean(Parcel in) {
        this.withdrawCount = in.readDouble();
        this.withdrawResult = in.readInt();
        this.withdrawTime = in.readString();
        this.withdrawBank = in.readString();
        this.result = in.readInt();
    }

    public static final Creator<WithDrawCashReturnBean> CREATOR = new Creator<WithDrawCashReturnBean>() {
        @Override
        public WithDrawCashReturnBean createFromParcel(Parcel source) {
            return new WithDrawCashReturnBean(source);
        }

        @Override
        public WithDrawCashReturnBean[] newArray(int size) {
            return new WithDrawCashReturnBean[size];
        }
    };
}
