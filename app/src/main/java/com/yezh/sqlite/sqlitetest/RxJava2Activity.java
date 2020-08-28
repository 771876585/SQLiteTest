package com.yezh.sqlite.sqlitetest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yezh on 2020/8/28 16:40.
 * Description：
 */
public class RxJava2Activity extends AppCompatActivity {

    public static final String TAG = "RxJava2Activity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        methodRxJava2();
    }

    @SuppressLint("CheckResult")
    private void methodRxJava2() {
        //1.create() start
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
//                observableEmitter.onNext("hello observer");
//                observableEmitter.onComplete();
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable disposable) {
//                Log.e(TAG, "========onSubscribe");
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.e(TAG, "========onNext " + s);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e(TAG, "========onComplete");
//            }
//        });
        //create() end

        //2.just() start
//        Observable.just(1,2,3,4,5,6,7,8,9)
//                .subscribe(new Observer<Integer>(){
//
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//                        Log.e(TAG, "========onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.e(TAG, "========onNext " + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e(TAG, "========onComplete");
//                    }
//                });
        //just() end

        //3.formArray() start
//        Integer array[] = {1,2,3,4};
//        Observable.fromArray(array)
//        //Observable.fromArray(1,2,3,4,5,6,7,8,9,10,11)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//                        Log.e(TAG, "========onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.e(TAG, "========onNext " + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e(TAG, "========onComplete");
//                    }
//                });
        //formArray() end

        //4.fromCallable start
//        Observable.fromCallable(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                return 2;
//            }
//        }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.e(TAG, "========accept " + integer);
//            }
//        });
        //fromCallable end

        //5.fromFuture() start
//        final FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                Log.e(TAG, "========call ");
//                return "测试结果";
//            }
//        });
//        Observable.fromFuture(futureTask)
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        futureTask.run();
//
//                    }
//                }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                //futureTask.get()返回的也是接收的结果数据
//                Log.e(TAG, "========accept " + s +"=" +futureTask.get());
//            }
//        });
        //fromFuture() end

        //6.fromIterable() start
//        List<Integer> list = new ArrayList<>();
//        list.add(0);
//        list.add(1);
//        list.add(2);
//        Observable.fromIterable(list)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//                        Log.e(TAG, "========onSubscribe");
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.e(TAG, "========onNext " + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e(TAG, "========onComplete");
//                    }
//                });
        //fromIterable() end

        //7.defer() start
//        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
//            @Override
//            public ObservableSource<? extends Integer> call() throws Exception {
//                return Observable.just(i);
//            }
//        });
//        i = 200;
//        Observer observer = new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable disposable) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.e(TAG, "========onNext " + integer);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//
//        observable.subscribe(observer);
//
//        i = 300;
//        observable.subscribe(observer);
        //defer() end

        //8.timer() start
//        Observable.timer(2, TimeUnit.SECONDS)
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        Log.e(TAG, "========onNext " + aLong);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
        //time() end

        //9.interval() start
//        Observable.interval(2, TimeUnit.SECONDS)
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        Log.e(TAG, "========onNext " + aLong);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
        //interval() end

        Observable.interval(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, "========onNext " + aLong);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    Integer i = 100;
}
