package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;


public class BankCardBean implements Parcelable {
//{"bank":"temp","bankUsername":"*****","bid":4,"cardName":"test","cardNo":"62122612020356924646467"}
    private long bid;
    private int id;
    private String bank;
    private String branchBank;
    private String cardNo;
    private String name;
    private String backGround;
    private String icon;
    private String cardName;
    private int isDefault;
    private String account; //银行卡号
    private String bankUsername; //开户名

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
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
        dest.writeLong(this.bid);
        dest.writeInt(this.id);
        dest.writeString(this.bank);
        dest.writeString(this.branchBank);
        dest.writeString(this.cardNo);
        dest.writeString(this.name);
        dest.writeString(this.backGround);
        dest.writeString(this.icon);
        dest.writeString(this.cardName);
        dest.writeInt(this.isDefault);
        dest.writeString(this.account);
        dest.writeString(this.bankUsername);
    }

    public BankCardBean() {
    }

    protected BankCardBean(Parcel in) {
        this.bid = in.readLong();
        this.id = in.readInt();
        this.bank = in.readString();
        this.branchBank = in.readString();
        this.cardNo = in.readString();
        this.name = in.readString();
        this.backGround = in.readString();
        this.icon = in.readString();
        this.cardName = in.readString();
        this.isDefault = in.readInt();
        this.account = in.readString();
        this.bankUsername = in.readString();
    }

    public static final Creator<BankCardBean> CREATOR = new Creator<BankCardBean>() {
        @Override
        public BankCardBean createFromParcel(Parcel source) {
            return new BankCardBean(source);
        }

        @Override
        public BankCardBean[] newArray(int size) {
            return new BankCardBean[size];
        }
    };
}
