package com.witype.litehttpdemo.presenter.impl;

import com.litesuits.http.response.Response;
import com.witype.litehttpdemo.IBaseView.ILoginView;
import com.witype.litehttpdemo.custom.MyHttpListener;
import com.witype.litehttpdemo.entity.BaseEntity;
import com.witype.litehttpdemo.model.LoginModel;
import com.witype.litehttpdemo.presenter.ILoginPresenter;

/**
 * Created by WiType on 2016/4/5 0005.
 *
 */
public class LoginPresenter extends BasePresenter implements ILoginPresenter {

    private ILoginView mILoginView;

    public LoginPresenter(ILoginView mIBaseView) {
        super(mIBaseView);
        this.mILoginView = mIBaseView;
    }

    @Override
    public void doLogin(String userName, String pwd) {
        //TODO CHECK PARAMS
        executeSync(new LoginModel(userName,pwd).setMyHttpListener(new MyHttpListener<BaseEntity>(this) {

            @Override
            public void onSuccessOk(BaseEntity s, Response<BaseEntity> response) {
                super.onSuccessOk(s, response);
                mILoginView.onLoginSuccess();
            }
        }));
    }
}
