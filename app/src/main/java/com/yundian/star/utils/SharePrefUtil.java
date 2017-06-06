package com.yundian.star.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yundian.star.app.AppApplication;
import com.yundian.star.been.AssetDetailsBean;
import com.yundian.star.been.LoginReturnInfo;
import com.yundian.star.been.UserinfoBean;
import com.yundian.star.ui.wangyi.DemoCache;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ysl .
 */
public class SharePrefUtil {
    private SharedPreferences sp;
    private Context context;
    private volatile static SharePrefUtil spu;

    private SharePrefUtil(Context context) {
        this.context = context;
    }

    public static SharePrefUtil getInstance() {
        if (spu == null) {
            synchronized (SharePrefUtil.class) {
                if (spu == null) {
                    spu = new SharePrefUtil(AppApplication.getApplication());
                }
            }
        }
        return spu;
    }

    private static String UserInfo = "AdstarUser";
    private static String UserLoginInfo = "UserLoginInfo";

    public void saveLoginUserInfo(LoginReturnInfo user) {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().putString("phoneNum", user.getUserinfo().getPhone()).apply();
        sp.edit().putString("token", user.getToken()).apply();
        sp.edit().putInt("userId", user.getUserinfo().getId()).apply();
        sp.edit().putString("balance", user.getUserinfo().getBalance() + "").apply();
    }

    public String getUserRole() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        return sp.getString("role", "0");
    }

    public UserinfoBean getLoginUserInfo() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        String phoneNum = sp.getString("phoneNum", "");
        UserinfoBean userinfoBean = new UserinfoBean();
        userinfoBean.setPhone(phoneNum);
        return userinfoBean;
    }

    public String getToken() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        String token = sp.getString("token", "");
        return token;
    }


    /**
     * 清空UserInfo
     */
    public void clearUserInfo() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    public void putIdStatus(int idStatus) {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().putInt("idStatus", idStatus).apply();
    }

    public long getExitTime() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        long exitTime = sp.getLong("exitTime", 0L);
        return exitTime;
    }

    public void setExitTime(long exitTime) {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().putLong("exitTime", exitTime).apply();
    }

    //保存地图信息
    public void setlatLng(double latitude, double longitude, String province, String city, String area, String streat, String address) {
        sp = context.getSharedPreferences("latLng", MODE_PRIVATE);
        sp.edit().putString("latitude", String.valueOf(latitude)).apply();
        sp.edit().putString("longitude", String.valueOf(longitude)).apply();
        sp.edit().putString("province", province).apply();
        sp.edit().putString("city", city).apply();
        sp.edit().putString("area", area).apply();
        sp.edit().putString("streat", streat).apply();
        sp.edit().putString("address", address).apply();

    }


    /**
     * @return 不显示帮助画面
     */
    public boolean getLiveFirst() {
        return false;
    }

    public void saveLiveFirst(boolean b) {

    }

    public int getUserId() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        int userId = sp.getInt("userId", 0);
        return userId;
    }

    public String getUserNickName() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        String nickName = sp.getString("nickName", null);
        return nickName;
    }

    public void putUserNickName(String nickName) {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().putString("nickName", nickName).apply();
    }

    public int getUserSex() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        int sex = sp.getInt("sex", -1);
        return sex;
    }

    public void putUserSex(int sex) {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().putInt("sex", sex).apply();
    }

    public void setLoginTime(Long loginTime) {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().putLong("loginTime", loginTime).apply();
    }

    public long getLoginTime() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        long loginTime = sp.getLong("loginTime", 0L);
        return loginTime;
    }

    public String getPhoneNum() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        String phoneNum = sp.getString("phoneNum", "");
        return phoneNum;
    }

    public void putUserPhotoUrl(String url) {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().putString("headurl", url).apply();
    }

    public String getUserPhotoUrl() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        String url = sp.getString("headurl", "");
        return url;
    }

    public void putUserBigPhotoUrl(String url) {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().putString("bgheadimg", url).apply();
    }

    public String getUserBigPhotoUrl() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        String url = sp.getString("bgheadimg", "");
        return url;
    }

    public void addFollowers(int num) {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        int oldFollowers = sp.getInt("follows", 0);
        sp.edit().putInt("follows", oldFollowers + num).apply();
    }

    public void delFollowers(int num) {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        int oldFollowers = sp.getInt("follows", 0);
        int finalNum = oldFollowers - num;
        if (finalNum < 0) {
            sp.edit().putInt("follows", 0).apply();
        } else {
            sp.edit().putInt("follows", finalNum).apply();
        }
    }

    public void resetFollowers(int num) {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().putInt("follows", num).apply();
    }

    public void resetFans(int num) {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().putInt("fans", num).apply();
    }

    public void setInLiveRoom(boolean inLive) {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().putBoolean("inLiveRoom", inLive).apply();
    }

    public boolean getInLiveRoom() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        boolean inLive = sp.getBoolean("inLiveRoom", false);
        return inLive;
    }

    // 登录相关信息=========================
    public void putUserLoginInfo(String id, String logincode, String usersig, int firstlogin, String bindMobile) {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        sp.edit().putString("id", id).apply();
        sp.edit().putString("logincode", logincode).apply();
        sp.edit().putString("usersig", usersig).apply();
        sp.edit().putInt("firstlogin", firstlogin).apply();
        sp.edit().putString("bindMobile", bindMobile).apply();
    }

    public void putHXusersig(String hxusersig) {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        sp.edit().putString("hxusersig", hxusersig).apply();
    }

    public String gettHXusersig() {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        return sp.getString("hxusersig", "");
    }

    public String getUserLoginId() {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        return sp.getString("id", "");
    }

    public String getUserLoginCode() {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        return sp.getString("logincode", "");
    }

    public void putUserSig(String usersig) {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        sp.edit().putString("usersig", usersig).apply();
    }

    public String getUserSig() {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        return sp.getString("usersig", "");
    }

    public int getFirstlogin() {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        return sp.getInt("firstlogin", -1);
    }

    public void putFirstlogin(int firstlogin) {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        sp.edit().putInt("firstlogin", firstlogin).apply();
    }

    public String getBindMobile() {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        return sp.getString("bindMobile", "");
    }

    public void setBindMobile(String mobile) {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        sp.edit().putString("bindMobile", mobile).apply();
    }

    public String getRealName() {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        return sp.getString("realName", "");
    }

    public void setRealName(String name) {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        sp.edit().putString("realName", name).apply();
    }

    public String getIdnum() {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        return sp.getString("idnum", "");
    }

    public void setIdnum(String idnum) {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        sp.edit().putString("idnum", idnum).apply();
    }

    /**
     * 清空UserLoginInfo
     */
    public void clearUserLoginInfo() {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    //temp=================================================================
    private static String TEMP = "temp";

    public int getOldVersionCode() {
        sp = context.getSharedPreferences(TEMP, MODE_PRIVATE);
        return sp.getInt("old_version_code", -1);
    }

    public void setOldVersionCode(int versionCode) {
        sp = context.getSharedPreferences(TEMP, MODE_PRIVATE);
        sp.edit().putInt("old_version_code", versionCode).apply();
    }

    private static String CBSTATES = "cbstates";


    private static String PUSHURL = "pushurl";


    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    private SharedPreferences getSharedPreferences() {
        return DemoCache.getContext().getSharedPreferences("Demo", MODE_PRIVATE);
    }

    public void putUserReferee(String referee) {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        sp.edit().putString("referee", referee).apply();
    }

    public String getUserReferee() {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        return sp.getString("referee", "");
    }

    /**
     * 移除年龄信数据
     */
    public void removeUserInfo(String key) {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        sp.edit().remove(key).apply();

    }
    public String getBalance() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        String balance = sp.getString("balance", "0");
        return balance;
    }
    public void putBalance(double balance){
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().putString("balance", balance + "").apply();
    }
    public void putOrderStar(int count) {
        sp = context.getSharedPreferences(UserLoginInfo, MODE_PRIVATE);
        sp.edit().putInt("orderStar", count).apply();
    }
    public int getOrderStar() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        int count = sp.getInt("orderStar", 0);
        return count;
    }

    public int getIsSetpwd() {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        int is_setpwd = sp.getInt("is_setpwd", 0);   //0否  1是
        return is_setpwd;
    }
    public void putIsSetpwd(int is_setpwd){
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().putInt("is_setpwd", is_setpwd ).apply();
    }

    /**
     * 保存资产信息  余额 / 市值  总资产等
     * @param bean
     */
    public void saveAssetInfo(AssetDetailsBean bean) {
        sp = context.getSharedPreferences(UserInfo, MODE_PRIVATE);
        sp.edit().putString("balance", bean.getBalance() + "").apply();
        sp.edit().putString("market_cap", bean.getMarket_cap()+"").apply();
        sp.edit().putString("total_amt", bean.getTotal_amt()+"").apply();
        sp.edit().putInt("is_setpwd", bean.getIs_setpwd() ).apply();
    }
}
