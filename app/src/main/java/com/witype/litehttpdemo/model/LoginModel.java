package com.witype.litehttpdemo.model;

import com.litesuits.http.annotation.HttpMethod;
import com.litesuits.http.annotation.HttpUri;
import com.litesuits.http.request.param.HttpMethods;
import com.witype.litehttpdemo.entity.BaseEntity;

/**
 * Created by WiType on 2016/4/5 0005.
 *
 */
@HttpUri("http://rap.taobao.org/mockjsdata/1685/siginin")
@HttpMethod(HttpMethods.Get)
public class LoginModel extends BaseModel<BaseEntity> {

    public String userName;

    public String pwd;

    public LoginModel(String userName, String pwd) {
        this.userName = userName;
        this.pwd = pwd;
    }
}
