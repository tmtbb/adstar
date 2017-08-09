package com.cloudTop.starshare.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/4/10.
 */
public class BankInfoBean implements Parcelable {

    /**
     * {"bankId":165,"bankName":"中国农业银行","cardNO":"6228480402564890018","cardName":"农业银行·金穗通宝卡(银联卡)"}
     */
    private int result;
    private long bankId;
    private long bid;  //银行卡id
    private String brachName; //支行
    private String name; //姓名
    private String bankName;
    private String cardNO;
    private String cardName;
    private String account;
    private String bankUsername;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public String getBrachName() {
        return brachName;
    }

    public void setBrachName(String brachName) {
        this.brachName = brachName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNO() {
        return cardNO;
    }

    public void setCardNO(String cardNO) {
        this.cardNO = cardNO;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankUsername() {
        return bankUsername;
    }

    public void setBankUsername(String bankUsername) {
        this.bankUsername = bankUsername;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.result);
        dest.writeLong(this.bankId);
        dest.writeLong(this.bid);
        dest.writeString(this.brachName);
        dest.writeString(this.name);
        dest.writeString(this.bankName);
        dest.writeString(this.cardNO);
        dest.writeString(this.cardName);
        dest.writeString(this.account);
        dest.writeString(this.bankUsername);
    }

    public BankInfoBean() {
    }

    protected BankInfoBean(Parcel in) {
        this.result = in.readInt();
        this.bankId = in.readLong();
        this.bid = in.readLong();
        this.brachName = in.readString();
        this.name = in.readString();
        this.bankName = in.readString();
        this.cardNO = in.readString();
        this.cardName = in.readString();
        this.account = in.readString();
        this.bankUsername = in.readString();
    }

    public static final Creator<BankInfoBean> CREATOR = new Creator<BankInfoBean>() {
        @Override
        public BankInfoBean createFromParcel(Parcel source) {
            return new BankInfoBean(source);
        }

        @Override
        public BankInfoBean[] newArray(int size) {
            return new BankInfoBean[size];
        }
    };
}
