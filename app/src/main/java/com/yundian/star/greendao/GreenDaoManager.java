package com.yundian.star.greendao;

import android.text.TextUtils;

import com.yundian.star.app.AppApplication;
import com.yundian.star.greendao.gen.StarInfoDao;

import java.util.List;

/**
 * Created by sll on 2017/6/16.
 */

public class GreenDaoManager {
    private static GreenDaoManager instance;
    private static StarInfoDao starInfoDao;

    private GreenDaoManager() {
    }

    public static GreenDaoManager getInstance() {
        if (instance == null) {
            synchronized (GreenDaoManager.class) {
                if (instance == null) {
                    instance = new GreenDaoManager();
                }
            }
        }
        starInfoDao = AppApplication.getDaoInstant().getStarInfoDao();
        return instance;
    }


    /**
     * 根据用户id,取出用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    public StarInfo loadNote(long id) {
        if (!TextUtils.isEmpty(id + "")) {
            return starInfoDao.load(id);
        }
        return null;
    }
    /**
     * 根据明星code,取出用户信息
     * @return 用户信息
     */
    public static List<StarInfo> queryLove(String codes) {
        return starInfoDao.queryBuilder().where(StarInfoDao.Properties.Code.eq(codes)).list();
    }




    /**
     * 取出所有数据
     *
     * @return 所有数据信息
     */
    public List<StarInfo> loadAllNote() {
        return starInfoDao.loadAll();
    }

    /**
     * 生成按id倒排序的列表
     *
     * @return 倒排数据
     */
    public List<StarInfo> loadAllNoteByOrder() {
        return starInfoDao.queryBuilder().orderDesc(StarInfoDao.Properties.Id).list();
    }

    /**
     * 根据查询条件,返回数据列表
     *
     * @param where  条件
     * @param params 参数
     * @return 数据列表
     */
    public List<StarInfo> queryNote(String where, String... params) {
        return starInfoDao.queryRaw(where, params);
    }


    /**
     * 根据用户信息,插件或修改信息
     *
     * @param user 用户信息
     * @return 插件或修改的用户id
     */
    public long saveNote(StarInfo user) {
        return starInfoDao.insertOrReplace(user);
    }


    /**
     * 批量插入或修改用户信息
     *
     * @param list 用户信息列表
     */
    public void saveNoteLists(final List<StarInfo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        starInfoDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    StarInfo user = list.get(i);
                    cacheUserInfo(user);  //根据code判断数据库中是否存在老数据,如果存在,把老数据的ID赋值给新数据
                }
            }
        });
    }

    /**
     * 判断缓存用户信息
     *
     * @param starInfo
     */
    public void cacheUserInfo(StarInfo starInfo) {
        List<StarInfo> oldInfos = queryLove(starInfo.getCode());
        if (oldInfos != null && oldInfos.size() > 0) {
            starInfo.setId(oldInfos.get(0).getId());
        }
        starInfoDao.insertOrReplace(starInfo);
    }

    /**
     * 删除所有数据
     */
    public void deleteAllNote() {
        starInfoDao.deleteAll();
    }

    /**
     * 根据id,删除数据
     *
     * @param id 用户id
     */
    public void deleteNote(long id) {
        starInfoDao.deleteByKey(id);
    }

    /**
     * 根据用户类,删除信息
     *
     * @param user 用户信息类
     */
    public void deleteNote(StarInfo user) {
        starInfoDao.delete(user);
    }

//    /**
//     * 根据明星code,取出用户信息
//     *
//     * @return 用户信息
//     */
//    public  List<StarInfo> queryLove(String codes) {
//        return starInfoDao.queryBuilder().where(StarInfoDao.Properties.Code.eq(codes)).list();
//    }

}
