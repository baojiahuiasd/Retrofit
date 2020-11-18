package com.tiger.lib.call;

import com.tiger.lib.base.ProxyMethod;

import java.lang.reflect.Type;

public interface CallAdapter {
    OkHttpCall createAdapter(Type type);
     interface OkHttpCall{
        Object create(ProxyMethod proxyMethod);
    }
}
