package com.yundian.star.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/5/19.
 */

public class StarBuyActReferralInfo implements Parcelable {

    /**
     * birth : 11
     * colleage : 清华
     * constellaction :
     * gender : 1
     * head_url :
     * introduction : 林志玲，1974年11月
     * name : 林
     * nation : 汉族
     * nationality : 中国
     * owntimes : 1000
     * pic_url : http://www.wanhuajing.com/pic/1605/1815/3129838/7_640_312.jpg
     * result : 1
     * star_vip : 0
     * symbol : 1000
     * weibo_index_id : 1011223178222
     * work : 艺人
     */

    private String birth;
    private String colleage;
    private String constellaction;
    private int gender;
    private String head_url;
    private String introduction;
    private String name;
    private String nation;
    private String nationality;
    private int owntimes;
    private String pic_url;
    private int result;
    private int star_vip;
    private String symbol;
    private String weibo_index_id;
    private String work;

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getColleage() {
        return colleage;
    }

    public void setColleage(String colleage) {
        this.colleage = colleage;
    }

    public String getConstellaction() {
        return constellaction;
    }

    public void setConstellaction(String constellaction) {
        this.constellaction = constellaction;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getOwntimes() {
        return owntimes;
    }

    public void setOwntimes(int owntimes) {
        this.owntimes = owntimes;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getStar_vip() {
        return star_vip;
    }

    public void setStar_vip(int star_vip) {
        this.star_vip = star_vip;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getWeibo_index_id() {
        return weibo_index_id;
    }

    public void setWeibo_index_id(String weibo_index_id) {
        this.weibo_index_id = weibo_index_id;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.birth);
        dest.writeString(this.colleage);
        dest.writeString(this.constellaction);
        dest.writeInt(this.gender);
        dest.writeString(this.head_url);
        dest.writeString(this.introduction);
        dest.writeString(this.name);
        dest.writeString(this.nation);
        dest.writeString(this.nationality);
        dest.writeInt(this.owntimes);
        dest.writeString(this.pic_url);
        dest.writeInt(this.result);
        dest.writeInt(this.star_vip);
        dest.writeString(this.symbol);
        dest.writeString(this.weibo_index_id);
        dest.writeString(this.work);
    }

    public StarBuyActReferralInfo() {
    }

    protected StarBuyActReferralInfo(Parcel in) {
        this.birth = in.readString();
        this.colleage = in.readString();
        this.constellaction = in.readString();
        this.gender = in.readInt();
        this.head_url = in.readString();
        this.introduction = in.readString();
        this.name = in.readString();
        this.nation = in.readString();
        this.nationality = in.readString();
        this.owntimes = in.readInt();
        this.pic_url = in.readString();
        this.result = in.readInt();
        this.star_vip = in.readInt();
        this.symbol = in.readString();
        this.weibo_index_id = in.readString();
        this.work = in.readString();
    }

    public static final Parcelable.Creator<StarBuyActReferralInfo> CREATOR = new Parcelable.Creator<StarBuyActReferralInfo>() {
        @Override
        public StarBuyActReferralInfo createFromParcel(Parcel source) {
            return new StarBuyActReferralInfo(source);
        }

        @Override
        public StarBuyActReferralInfo[] newArray(int size) {
            return new StarBuyActReferralInfo[size];
        }
    };
}
