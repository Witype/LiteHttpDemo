package com.witype.litehttpdemo;

import android.os.Bundle;
import android.view.View;

import com.witype.litehttpdemo.IBaseView.ILoginView;
import com.witype.litehttpdemo.presenter.ILoginPresenter;
import com.witype.litehttpdemo.presenter.impl.LoginPresenter;

public class MainActivity extends BaseActivity implements ILoginView {

    ILoginPresenter mPresenter = getPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doLogin(View v) {
        mPresenter.doLogin("","");
    }

    @Override
    public void onLoginSuccess() {
        //TODO login success
    }

    @Override
    public LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }
}
