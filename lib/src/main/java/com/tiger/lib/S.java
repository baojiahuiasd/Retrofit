package com.tiger.lib;

import com.tiger.lib.operator.Body;
import com.tiger.lib.operator.Field;
import com.tiger.lib.operator.GET;
import com.tiger.lib.operator.POST;

import io.reactivex.Observable;

public interface S {
    @POST("http://127.0.0.1:8080/me/count/post/2")
    Observable<Object> getBeansa(@Body Object name);

    @GET("https://api.apiopen.top/musicRankings")
    Observable<Object> getClas();

    @POST("http://127.0.0.1:8080/me/count/post")
    Observable<Object> getBeans(@Field("name") String name, @Field("age") String age);
}
