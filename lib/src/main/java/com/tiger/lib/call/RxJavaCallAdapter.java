package com.tiger.lib.call;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observable;

public class RxJavaCallAdapter implements CallAdapter {

    @Override
    public OkHttpCall createAdapter(Type type) {
        OkHttpCall okHttpCall=null;
        if (((ParameterizedType)type).getRawType()== Observable.class) {
            okHttpCall= new ObservableCall();
        }
        return okHttpCall;
    }
}
