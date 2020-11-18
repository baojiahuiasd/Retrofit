package com.tiger.lib.request;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Proxy;

public class GsonRequestAdapter implements RequestAdapter {
    private Gson mGson;

    public GsonRequestAdapter() {
        mGson = new Gson();
    }

    @Override
    public RequestAdapter.RequestBodys getAdapter(Type type) {
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (actualTypeArguments[0] == null) throw new IllegalArgumentException("no find call back type");
        TypeAdapter<?> adapter = mGson.getAdapter(TypeToken.get(actualTypeArguments[0]));
        return new GsonRequest(mGson, adapter);
    }
}
