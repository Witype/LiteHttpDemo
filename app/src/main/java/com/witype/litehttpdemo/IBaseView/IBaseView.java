package com.witype.litehttpdemo.IBaseView;

import android.content.Context;

/**
 * Created by WiType on 2016/4/5 0005.
 *
 */
public interface IBaseView {

    Context getContext();

    void onSuccess();

    void onNetWorkError();
}
