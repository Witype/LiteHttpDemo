package com.witype.litehttpdemo.IBaseView;

import android.content.Context;
import android.view.View;

/**
 * Created by WiType on 2016/4/5 0005.
 *
 */
public interface IBaseView extends View.OnClickListener {

    Context getActivity();

    void onSuccess();

    void onNetWorkError();
}
