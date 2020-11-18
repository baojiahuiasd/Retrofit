package com.tiger.lib.call;

import com.tiger.lib.base.ProxyMethod;

import io.reactivex.Observable;

public class ObservableCall implements CallAdapter.OkHttpCall {
    @Override
    public Object create(ProxyMethod proxyMethod) {
        return Observable.create(new ObservableMethod<>(proxyMethod));
    }
}
