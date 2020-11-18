package com.tiger.lib;

import com.tiger.lib.base.Retrofit;
import com.tiger.lib.call.RxJavaCallAdapter;
import com.tiger.lib.request.GsonRequestAdapter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;

public class main {
    public static void main(String[] args) {
        Retrofit build = new Retrofit.Builder()
                .setOkHttpClient(new OkHttpClient())
                .setCallAdapter(new RxJavaCallAdapter())
                .setRequestAdapter(new GsonRequestAdapter())
                .build();
        S service = build.createService(S.class);
//        service.getBeansa(new re()).subscribe(new Observer<beads>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(beads beads) {
//                System.out.println("接受到的数据" + beads.getCount());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("错误" + e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
     //   service.getBeans("鲍佳辉","20").subscribe();
//        service.getClas().subscribe(new Observer<kdadad>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(kdadad re) {
//                System.out.println("接受请求");
//                Gson gson=new Gson();
//                String s = gson.toJson(re);
//                System.out.println(s);
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("请求失败");
//                System.out.println(e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

    }

}
