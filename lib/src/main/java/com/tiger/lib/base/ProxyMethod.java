package com.tiger.lib.base;


import com.google.gson.Gson;
import com.tiger.lib.call.CallAdapter;
import com.tiger.lib.operator.Field;
import com.tiger.lib.operator.GET;
import com.tiger.lib.operator.HEAD;
import com.tiger.lib.operator.POST;
import com.tiger.lib.operator.Query;
import com.tiger.lib.request.RequestAdapter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ProxyMethod {
    private Object[] mObject;
    private RequestBody mRequestBody;
    public Request.Builder mRequestBuilder;
    private FormBody.Builder mFormBody;
    private final Annotation[] mAnnotations;//方法上的注解 列如：PSOT,GET
    private final Annotation[][] mParameterAnnotations;//参数的注解 列如：Body
    private CallAdapter.OkHttpCall callAdapter;
    private RequestAdapter.RequestBodys requestAdapter;
    public OkHttpClient okHttpClient;
    public Retrofit mRetrofit;

    public ProxyMethod(Retrofit retrofit, Method method, Object[] objects) {
        mObject = objects;
        mRetrofit = retrofit;
        okHttpClient = mRetrofit.getHttpClient();
        mAnnotations = method.getAnnotations();
        mParameterAnnotations = method.getParameterAnnotations();
        mRequestBuilder = new Request.Builder();
        createCallAdapter(method);
        createRequestAdapter(method);
    }

    private void createRequestAdapter(Method method) {
        Type genericReturnType = method.getGenericReturnType();
        requestAdapter = mRetrofit.createRequestAdapter(genericReturnType);
    }

    private void createCallAdapter(Method method) {
        callAdapter = mRetrofit.createCallAdapter(method.getGenericReturnType());
    }

    public void createMethod() {
        for (Annotation annotation : mAnnotations) {
            addHeard(annotation);
        }
    }


    public void addHeard(Annotation annotation) {
        String simpleAnnotationName = annotation.annotationType().getSimpleName();
        switch (simpleAnnotationName) {
            case "POST":
                int length = mParameterAnnotations.length;
                for (int i = 0; i < length; i++) {
                    Annotation[] annotationFileArray = mParameterAnnotations[i];
                    if (annotationFileArray.length < 1)
                        throw new IllegalArgumentException("this field not use Annotation");
                    addRequestBody(annotationFileArray, i);
                }
                if (mFormBody != null) mRequestBody = mFormBody.build();
                if (mRequestBody == null)
                    throw new IllegalArgumentException("not create request body");
                mRequestBuilder.post(mRequestBody);
                mRequestBuilder.url(((POST) annotation).value());
                break;
            case "GET":
                mRequestBuilder.get();
                String url = ((GET) annotation).value();
                int urlQuery = mParameterAnnotations.length;
                for (int i = 0; i < urlQuery; i++) {
                    Annotation[] mParameterAnnotation = mParameterAnnotations[i];
                    if (mParameterAnnotation.length < 1)
                        throw new IllegalArgumentException("this field not use Annotation");

                    url += replaceUrl(mParameterAnnotation, i);
                }
                mRequestBuilder.url(url);
                break;
            case "HEAD":
                HEAD head = ((HEAD) annotation);
                mRequestBuilder.addHeader(head.key(), head.value());
                break;
        }
    }

    private String replaceUrl(Annotation[] mParameterAnnotation, int index) {
        String url = "";
        if (index == 0) {
            url += "?";
        } else {
            url += "&";
        }
        for (Annotation annotation : mParameterAnnotation) {
            if (annotation.annotationType().getSimpleName().equals("Query")) {
                url += ((Query) annotation).value() + "=" + mObject[index];
                break;
            }
        }
        return url;
    }

    private void addRequestBody(Annotation[] annotationFileArray, int filedNumber) {
        for (Annotation annotation : annotationFileArray) {
            String simpleFileName = annotation.annotationType().getSimpleName();
            switch (simpleFileName) {
                case "Body":
                    if (mRequestBody == null) {
                        Gson gson = new Gson();
                        String jsonBDID = gson.toJson(mObject[filedNumber]);
                        mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonBDID);
                    } else {
                        throw new IllegalArgumentException("this is has Body");
                    }
                    break;
                case "Field":
                    if (mFormBody == null) {
                        mFormBody = new FormBody.Builder();
                    }
                    mFormBody.add(((Field) annotation).value(), (String) mObject[filedNumber]);
                    break;
            }
        }
    }

    public Object createRequest() {
        return callAdapter.create(this);
    }

    public <T> T getRequestBody(ResponseBody responseBody) {
        try {
            return (T) requestAdapter.toBody(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
