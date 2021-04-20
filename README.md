# Retrofit
项目位置 lib文件夹
lib/src/main/java/com.tiger.lib/mian是使用案例

 Retrofit build = new Retrofit.Builder()
                .setOkHttpClient(new OkHttpClient())         传入okhttp
                .setCallAdapter(new RxJavaCallAdapter())     传入请求处理器
                .setRequestAdapter(new GsonRequestAdapter()) 传入数据解析器
                .build();
        S service = build.createService(S.class);    //对请求接口进行代理
        
        //具体的使用案例
        service.getBeansa(new Object()).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object beads) {
                System.out.println("接受到的数据");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("错误" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
     service.getBeans("鲍佳辉","24").subscribe(new Observer<Object>() {
         @Override
         public void onSubscribe(Disposable d) {

         }

         @Override
         public void onNext(Object o) {

         }

         @Override
         public void onError(Throwable e) {

         }

         @Override
         public void onComplete() {

         }
     });
        service.getClas().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object re) {


            }

            @Override
            public void onError(Throwable e) {
                System.out.println("请求失败");
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }
