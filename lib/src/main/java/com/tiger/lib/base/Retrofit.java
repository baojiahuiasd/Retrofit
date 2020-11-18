package com.tiger.lib.base;

import com.tiger.lib.call.CallAdapter;
import com.tiger.lib.request.RequestAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class Retrofit {
    protected Builder mBuilder;
    private final Map<Method, ProxyMethod> mServiceMethodCacheHashMap = new LinkedHashMap<>();

    Retrofit(Builder builder) {
        mBuilder = builder;
    }

    public <T> T createService(Class<T> tClass) {
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[]{tClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                ProxyMethod methodProxy = createMethodProxy(method, objects);
                return methodProxy.createRequest();
            }
        });
    }

    private ProxyMethod createMethodProxy(Method method, Object[] objects) {
        ProxyMethod proxyMethod = mServiceMethodCacheHashMap.get(method);
        if (proxyMethod == null) {
            proxyMethod = new ProxyMethod(this, method, objects);
            proxyMethod.createMethod();
            mServiceMethodCacheHashMap.put(method, proxyMethod);
        }
        return proxyMethod;

    }

    protected CallAdapter.OkHttpCall createCallAdapter(Type type) {
        return mBuilder.mCallAdapter.createAdapter(type);
    }

    protected RequestAdapter.RequestBodys createRequestAdapter(Type actualTypeArguments) {
        return mBuilder.mRequestAdapter.getAdapter(actualTypeArguments);
    }

    protected OkHttpClient getHttpClient() {
        if (mBuilder.mOkHttpClient == null) {
            mBuilder.mOkHttpClient = new OkHttpClient();
        }
        return mBuilder.mOkHttpClient;
    }

    public static class Builder {
        protected OkHttpClient mOkHttpClient;
        protected CallAdapter mCallAdapter;
        protected RequestAdapter mRequestAdapter;

        public Builder setCallAdapter(CallAdapter adapter) {
            mCallAdapter = adapter;
            return this;
        }

        public Builder setRequestAdapter(RequestAdapter requestAdapter) {
            mRequestAdapter = requestAdapter;
            return this;
        }

        public Builder setOkHttpClient(OkHttpClient client) {
            mOkHttpClient = client;
            return this;
        }

        public Retrofit build() {
            return new Retrofit(this);
        }

    }
}