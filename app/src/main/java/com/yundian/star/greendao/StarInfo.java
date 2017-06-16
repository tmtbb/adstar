package com.yundian.star.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sll on 2017/6/16.
 */
@Entity
public class StarInfo {
    @Id
    private long id;
    private String name;
    @Generated(hash = 360704664)
    public StarInfo(long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 859816840)
    public StarInfo() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
