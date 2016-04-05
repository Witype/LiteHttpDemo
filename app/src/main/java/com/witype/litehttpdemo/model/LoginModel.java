package com.witype.litehttpdemo.model;

import com.witype.litehttpdemo.entity.BaseEntity;

/**
 * Created by WiType on 2016/4/5 0005.
 *
 */
public class LoginModel extends BaseModel<BaseEntity> {

    public String userName;

    public String pwd;

    public LoginModel(String userName, String pwd) {
        this.userName = userName;
        this.pwd = pwd;
    }
}
