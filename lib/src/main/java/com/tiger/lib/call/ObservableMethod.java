package com.tiger.lib.call;


import com.tiger.lib.base.ProxyMethod;

import java.io.IOException;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ObservableMethod<T> implements ObservableOnSubscribe<T> {
    private OkHttpClient mOkHttpClient;
    private Request mOkHttpBuild;
    private ProxyMethod mProxyMethod;

    public ObservableMethod(ProxyMethod proxyMethod) {
        mOkHttpClient = proxyMethod.okHttpClient;
        mOkHttpBuild = proxyMethod.mRequestBuilder.build();
        mProxyMethod = proxyMethod;
    }

    @Override
    public void subscribe(ObservableEmitter<T> emitter) throws Exception {
        Response execute = null;
        try {
            execute = mOkHttpClient.newCall(mOkHttpBuild).execute();
            int code = execute.code();
            if (code < 200 || code >= 300) {
                emitter.onError(new Throwable(execute.message()+"code="+code));
                return;
            }
            if (code == 204 || code == 205) {
                emitter.onNext((T) mProxyMethod.getRequestBody(execute.body()));
                return;
            }
            T body = mProxyMethod.getRequestBody(execute.body());
            emitter.onNext(body);
        } catch (IOException e) {
            emitter.onError(e);
        }

    }
}
