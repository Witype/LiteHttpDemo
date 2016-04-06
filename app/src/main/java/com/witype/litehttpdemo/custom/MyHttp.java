package com.witype.litehttpdemo.custom;

import android.text.TextUtils;
import android.util.Log;

import com.litesuits.http.HttpConfig;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.exception.ClientException;
import com.litesuits.http.exception.HttpClientException;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.GlobalHttpListener;
import com.litesuits.http.request.AbstractRequest;
import com.litesuits.http.request.JsonRequest;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.request.param.HttpRichParamModel;
import com.litesuits.http.response.Response;
import com.witype.litehttpdemo.entity.TokenEntity;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by WiType on 2016/4/6 0006.
 *
 */
public class MyHttp extends GlobalHttpListener {

    public static final String TAG = "MyHttp";

    public static final String TOKEN_URL = "http://rap.taobao.org/mockjsdata/1685/token";

    private LiteHttp mLiteHttp;

    private Queue<AbstractRequest> mTask;

    public MyHttp() {
        HttpConfig config = new HttpConfig(null);
        //设置litehttp的globalHttpListener，方便监听token过期。
        config.setGlobalHttpListener(this);
        mLiteHttp = LiteHttp.newApacheHttpClient(config);
        mTask = new LinkedList<>();
    }

    @Override
    public void onSuccess(Object o, Response<?> response) {
        try {
            //检查一下链接，如果是更新token的链接接继续之前的任务
            String url = response.getRequest().getFullUri();
            String fullUrl = TOKEN_URL;
            if (TextUtils.equals(url, fullUrl)) {
                //TODO GET TOKEN SUCCESS
                if (o instanceof TokenEntity) {
                    Log.i(TAG,"token获取成功:"+((TokenEntity) o).getToken());
                }
                toContinueTask();
            } else {
                //TODO
            }
        } catch (HttpClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 因为难得搞500错误，就用ClientException代替了，
     * BaseEntity中的code为int，json中随机返回0到131071，让Gson解析异常
     * @param e
     * @param response
     */
    @Override
    public void onFailure(HttpException e, Response<?> response) {
        try {
            //检查一下链接，如果是更新token的链接就不用处理了，直接找后台吧。
            String url = response.getRequest().getFullUri();
            String fullUrl = TOKEN_URL;
            if (TextUtils.equals(url, fullUrl)) return;
        } catch (HttpClientException e1) {
            e.printStackTrace();
        }
        if (e instanceof HttpClientException) {
            if (((HttpClientException) e).getExceptionType() == ClientException.SomeOtherException) {
                doUpdateSession();
                //将失败的任务添加到队列中
                mTask.offer(response.getRequest());
            }
        }
//        if (e instanceof HttpServerException) {
//            if (((HttpServerException) e).getExceptionType() == ServerException.ServerInnerError) {
//                doUpdateSession();
//                mTask.offer(response.getRequest());
//            }
//        }
    }

    public void doUpdateSession() {
        Log.i(TAG,"开始获取token");
        JsonRequest<TokenEntity> request = new JsonRequest<>(TOKEN_URL,TokenEntity.class);
        request.setMethod(HttpMethods.Get);
        executeSync(request);
    }

    /**
     * 继续上次一次任务
     */
    public void toContinueTask() {
        if (mTask.isEmpty()) return;
        AbstractRequest request = mTask.poll();
        executeSync(request);
        Log.i(TAG,"继续任务:" + request.getUri());
    }

    /**
     * 获取网络数据
     *
     * @param request 请求
     */
    public <T extends AbstractRequest> void executeSync(T request) {
        mLiteHttp.executeAsync(request);
    }

    /**
     * 获取网络数据
     *
     * @param model 请求
     */
    public <T> AbstractRequest executeSync(HttpRichParamModel<T> model) {
        return mLiteHttp.executeAsync(model);
    }
}
