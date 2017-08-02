package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sll on 2017/7/7.
 */

public class WithDrawCashHistoryBean implements Parcelable {

    /**
     * amount : 0.1
     * bank : 招商银行
     * branchBank :
     * cardNo : 6214835712766847
     * charge : 0
     * comment :
     * handleTime : 2017-07-07 09:58:22
     * id : 145
     * name : 于海波
     * status : 0
     * wid : 2986220228418061422
     * withdrawTime : 2017-07-07 09:58:22
     */

    private double amount;
    private String bank;
    private String branchBank;
    private String cardNo;
    private int charge;
    private String comment;
    private String handleTime;
    private int id;
    private String name;
    private int status;
    private long wid;
    private String withdrawTime;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranchBank() {
        return branchBank;
    }

    public void setBranchBank(String branchBank) {
        this.branchBank = branchBank;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getWid() {
        return wid;
    }

    public void setWid(long wid) {
        this.wid = wid;
    }

    public String getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(String withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.amount);
        dest.writeString(this.bank);
        dest.writeString(this.branchBank);
        dest.writeString(this.cardNo);
        dest.writeInt(this.charge);
        dest.writeString(this.comment);
        dest.writeString(this.handleTime);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.status);
        dest.writeLong(this.wid);
        dest.writeString(this.withdrawTime);
    }

    public WithDrawCashHistoryBean() {
    }

    protected WithDrawCashHistoryBean(Parcel in) {
        this.amount = in.readDouble();
        this.bank = in.readString();
        this.branchBank = in.readString();
        this.cardNo = in.readString();
        this.charge = in.readInt();
        this.comment = in.readString();
        this.handleTime = in.readString();
        this.id = in.readInt();
        this.name = in.readString();
        this.status = in.readInt();
        this.wid = in.readLong();
        this.withdrawTime = in.readString();
    }

    public static final Creator<WithDrawCashHistoryBean> CREATOR = new Creator<WithDrawCashHistoryBean>() {
        @Override
        public WithDrawCashHistoryBean createFromParcel(Parcel source) {
            return new WithDrawCashHistoryBean(source);
        }

        @Override
        public WithDrawCashHistoryBean[] newArray(int size) {
            return new WithDrawCashHistoryBean[size];
        }
    };
}
