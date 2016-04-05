package com.witype.litehttpdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.witype.litehttpdemo.IBaseView.IBaseView;
import com.witype.litehttpdemo.presenter.IBasePresenter;
import com.witype.litehttpdemo.presenter.impl.BasePresenter;

/**
 * Created by WiType on 2016/4/5 0005.
 *
 */
public class BaseActivity extends AppCompatActivity implements IBaseView {

    public IBasePresenter presenter = getPresenter();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (presenter != null) presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.onDestroy();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onNetWorkError() {

    }

    public BasePresenter getPresenter() {
        return new BasePresenter(this);
    }

    @Override
    public void onClick(View v) {

    }
}
