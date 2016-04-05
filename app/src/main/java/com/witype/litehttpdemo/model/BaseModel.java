package com.witype.litehttpdemo.model;

import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.param.HttpRichParamModel;

/**
 * Created by WiType on 2016/4/5 0005.
 *
 */
public abstract class BaseModel<T> extends HttpRichParamModel<T> {

    public HttpRichParamModel<T> setMyHttpListener(HttpListener<T> listener) {
        setHttpListener(listener);
        return this;
    }
}
