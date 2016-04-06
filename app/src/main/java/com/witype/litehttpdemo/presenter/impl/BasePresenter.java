package com.witype.litehttpdemo.presenter.impl;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.litesuits.http.LiteHttp;
import com.litesuits.http.request.AbstractRequest;
import com.litesuits.http.request.param.HttpRichParamModel;
import com.litesuits.http.response.Response;
import com.witype.litehttpdemo.IBaseView.IBaseView;
import com.witype.litehttpdemo.custom.WaitCancelListener;
import com.witype.litehttpdemo.presenter.IBasePresenter;

import java.util.ArrayList;

/**
 * Created by WiType on 2016/4/5 0005.
 *
 */
public class BasePresenter implements IBasePresenter {

    private LiteHttp mLiteHttp;

    private IBaseView mIBaseView;

    private ProgressDialog mDialog;

    private boolean isOnCreate;

    private ArrayList<AbstractRequest> mTask = new ArrayList<>();

    public BasePresenter(IBaseView mIBaseView) {
        this.mIBaseView = mIBaseView;
        mLiteHttp = LiteHttp.newApacheHttpClient(null);
    }

    @Override
    public void onCreate() {
        this.isOnCreate = true;
    }

    @Override
    public void onDestroy() {
        this.isOnCreate = false;
        for (AbstractRequest item : mTask) {
            item.cancel();
        }
    }

    @Override
    public boolean isDestroy() {
        return isOnCreate;
    }

    @Override
    public void showWaitDialog(String message, final WaitCancelListener listener) {
        if (mIBaseView == null) return;
        if (mDialog == null) {
            mDialog = new ProgressDialog(mIBaseView.getActivity());
        }
        if (mDialog.isShowing()){
            mDialog.setTitle(message);
        } else {
            mDialog.setTitle(message);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (listener != null) listener.onDialogCancel();
                }
            });
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.show();
        }
    }

    @Override
    public void dismiss() {
        if (mDialog != null) mDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(mIBaseView.getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Response response) {
        if (mIBaseView != null) {
            mIBaseView.onSuccess();
        }
        mTask.remove(response.getRequest());
    }

    @Override
    public void onNetWorkError() {
        if (mIBaseView != null) {
            mIBaseView.onNetWorkError();
        }
    }

    /**
     * 获取网络数据
     *
     * @param model 请求
     */
    public <T> void executeSync(HttpRichParamModel<T> model) {
        mTask.add(mLiteHttp.executeAsync(model));
    }
}
