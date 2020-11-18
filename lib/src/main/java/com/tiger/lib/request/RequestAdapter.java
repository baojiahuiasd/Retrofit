package com.tiger.lib.request;

import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

public interface RequestAdapter<T> {
    RequestBodys getAdapter(Type type);
    interface RequestBodys<T>{
        T toBody(ResponseBody requestBody) throws IOException;
    }
}
