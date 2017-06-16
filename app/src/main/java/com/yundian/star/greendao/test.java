//package com.yundian.star.greendao;
//
//import com.qamaster.android.common.User;
//
//import java.util.List;
//
///**
// * Created by sll on 2017/6/16.
// */
//
//public class test {
//    //插入数据：
//    private voidinsertdata(String name) {
//        User insertData = newUser(null, name, 24, false);
//        getUserDao().insert(insertData);
//    }
//
//    //删除数据
//    getUserDao().
//
//    deleteByKey(id);
//
//    //更改数据
//    private voidupdatadata(Long id) {
//　　User user = newUser(id, "更改后的数据用户", 22, true);
//　　getUserDao().update(user);
//    }
//
//    //查询数据
//    private void querydata() {
//　　List users = getUserDao().loadAll();
//　　StringBuffer sb = newStringBuffer();
//　　Log.i("tag", "当前数量：" + users.size());
//　　for (inti = 0; i < users.size(); i++) {
//　　　　Log.i("tag", "结果：" + users.get(i).getId() + "," + users.get(i).getName() + "," + users.get(i).getAge() + "," + 　　　　users.get(i).getIsBoy() + ";");
//　　　　
//            sb.append(users.get(i).getId() + "," + users.get(i).getName() + "," + users.get(i).getAge() + "," + users.get(i).getIsBoy() + ";\n");
//　　}
//　　show_msg.setText(sb.toString());
//    }
//}
