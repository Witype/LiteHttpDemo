package com.witype.litehttpdemo.custom;

import com.litesuits.http.exception.HttpClientException;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.exception.HttpNetException;
import com.litesuits.http.exception.HttpServerException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.AbstractRequest;
import com.litesuits.http.response.Response;
import com.witype.litehttpdemo.entity.BaseEntity;
import com.witype.litehttpdemo.presenter.IBasePresenter;

/**
 * Created by WiType on 2016/4/5 0005.
 *
 */
public class MyHttpListener<Data> extends HttpListener<Data> implements WaitCancelListener {

    private IBasePresenter presenter;

    private String title = "请稍候";

    private AbstractRequest<Data> request;

    //任务开始时是否显示等待框
    private boolean isShowWait = true;

    //是否自动显示error为非0时的信息提示
    private boolean isApiShowToast = true;

    //任务结束后是否自动关闭等待框
    private boolean isAutoDismiss = true;

    public MyHttpListener(IBasePresenter presenter) {
        this.presenter = presenter;
    }

    public MyHttpListener(IBasePresenter presenter,boolean runOnUiThread, boolean readingNotify, boolean uploadingNotify) {
        super(runOnUiThread, readingNotify, uploadingNotify);
        this.presenter = presenter;
    }

    public MyHttpListener<Data> setTitle(String title) {
        this.title = title;
        return this;
    }

    public MyHttpListener<Data> disableWait() {
        this.isShowWait = false;
        return this;
    }

    public MyHttpListener<Data> disableToast() {
        this.isApiShowToast = false;
        return this;
    }

    public MyHttpListener<Data> disableAutoDismiss() {
        this.isAutoDismiss = false;
        return this;
    }

    public MyHttpListener<Data> setShowWait(boolean showWait) {
        isShowWait = showWait;
        return this;
    }

    public MyHttpListener<Data> setApiShowToast(boolean apiShowToast) {
        isApiShowToast = apiShowToast;
        return this;
    }

    @Override
    public void onStart(AbstractRequest<Data> request) {
        super.onStart(request);
        this.request = request;
        if (isShowWait) presenter.showWaitDialog(title,this);
    }

    @Override
    public void onEnd(Response<Data> response) {
        super.onEnd(response);
        dismissWait();
    }

    public void dismissWait() {
        if (isShowWait && isAutoDismiss ) presenter.dismiss();
    }

    @Override
    public void onDialogCancel() {
        if (request != null) {
            request.cancel();
        }
    }

    /**
     * 成功，但是不保证用户数据正确性。
     * 这里自动处理解析后的数据，不同的code调用不同的方法
     */
    @Override
    public void onSuccess(Data s, Response<Data> response) {
        super.onSuccess(s, response);
        if (presenter.isDestroy()) return;
        if (s instanceof BaseEntity) {
            BaseEntity ss = (BaseEntity) s;
            if (ss.getCode() == 0) {
                onSuccessOk(s, response);
            } else {
                onSuccessNoZero(ss.getMessage(),response);
            }
        }
        presenter.onSuccess(response);
    }
    /**
     * 自己需要实现的类
     */
    public void onSuccessOk(Data s, Response<Data> response){}

    public void onSuccessNoZero(String reason,Response<Data> response){
        showToast(reason);
    }

    public void showToast(String message) {
        if (isApiShowToast) presenter.showToast(message);
    }

    @Override
    public void onFailure(HttpException e, Response<Data> response) {
        super.onFailure(e, response);
        if (e instanceof HttpNetException) {
            showToast(((HttpNetException) e).getExceptionType().chiReason);
            onNetWorkError();
        } else if (e instanceof HttpClientException) {
            showToast(((HttpClientException) e).getExceptionType().chiReason);
            dismissWait();
        } else if (e instanceof HttpServerException) {
            return;
        } else {
            onUnKnowError(e);
        }
        dismissWait();
    }

    public void onUnKnowError(HttpException e) {
        e.printStackTrace();
        presenter.showToast(e.getMessage() == null ? "" : e.getMessage());
    }

    public void onNetWorkError() {
        presenter.onNetWorkError();
    }
}
