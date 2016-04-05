package com.witype.litehttpdemo.presenter;

import com.litesuits.http.response.Response;
import com.witype.litehttpdemo.custom.WaitCancelListener;

/**
 * Created by WiType on 2016/4/5 0005.
 *
 */
public interface IBasePresenter {

    /**
     * 当Activity onCreate时调用
     */
    void onCreate();

    /**
     * 当Activity onDestory时调用
     */
    void onDestroy();

    /**
     * 当前Activity是否destroy
     */
    boolean isDestroy();

    void showWaitDialog(String messageg,WaitCancelListener listener);

    void dismiss();

    void showToast(String msg);

    /**
     * 当请求成功时候调用
     */
    void onSuccess(Response response);

    void onNetWorkError();
}
