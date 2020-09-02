package com.yezh.sqlite.sqlitetest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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

        //10.intervalRange() start
//        Observable.intervalRange(2, 7, 5,2, TimeUnit.SECONDS) //第三个参数是一开始延迟的时间，第四个参数是间隔延迟时间
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//                        Log.e(TAG, "========onSubscribe ");
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
        //intervalRange end

        //11.range() start
        //rangeLong()
        //Observable.rangeLong(3, 4)
//        Observable.range(3, 4)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//                        Log.e(TAG, "========onSubscribe ");
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
//
//                    }
//                });
        //range() end

        //12.empty() & never() & error() start
        //Observable.error(new Error())
        //Observable.never()
//        Observable.empty()
//                .subscribe(new Observer<Object>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//                        Log.e(TAG, "========onSubscribe ");
//                    }
//
//                    @Override
//                    public void onNext(Object o) {
//                        Log.e(TAG, "========onNext ");
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        Log.e(TAG, "========onError ");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e(TAG, "========onComplete ");
//                    }
//                });
        //empty() & never() & error()

        //13.map() start 转换操作符
//        Observable.just(1,2,3)
//                .map(new Function<Integer, String>() {
//                    @Override
//                    public String apply(Integer integer) throws Exception {
//                        return "It is " + integer;
//                    }
//                })
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//                        Log.e(TAG, "========onSubscribe ");
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.e(TAG, "========onNext " + s);
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
        //map() end

        //14.flatMap() start
        //concatMap() 是有序的，flatMap()是无序的
//        List<Person> personList = new ArrayList<>();
//        Plan plan = new Plan("1", "2");
//        List<String> actionList = new ArrayList<>();
//        actionList.add("12");
//        actionList.add("34");
//        actionList.add("56");
//        actionList.add("67");
//        plan.setActionList(actionList);
//        List<Plan> planList = new ArrayList<>();
//        planList.add(plan);
//        Person person = new Person("1", planList);
//        personList.add(person);
//        Observable.fromIterable(personList)
//                .flatMap(new Function<Person, ObservableSource<Plan>>() {
//                    @Override
//                    public ObservableSource<Plan> apply(Person person) throws Exception {
//                        return Observable.fromIterable(person.getPlanList());
//                    }
//                })
//                .flatMap(new Function<Plan, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(Plan plan) throws Exception {
//                        return Observable.fromIterable(plan.getActionList());
//                    }
//                }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable disposable) {
//
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
//
//            }
//        });
        //flatMap()

        //15.buffer() start
//        Observable.just(1,2,3,4,5)
//                .buffer(2, 2)
//                .subscribe(new Observer<List<Integer>>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<Integer> integers) {
//                        Log.e(TAG, "========onNext " + integers.size());
//                        for(Integer i : integers){
//                            Log.e(TAG, "========元素 " + i);
//                        }
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
        //buffer() end

        //16.groupBy() start
//        Observable.just(5,2,3,4,1,6,8,7,9,10)
//                .groupBy(new Function<Integer, Integer>() {
//                    @Override
//                    public Integer apply(Integer integer) throws Exception {
//                        return integer % 3;
//                    }
//                })
//                .subscribe(new Observer<GroupedObservable<Integer, Integer>>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//
//                    }
//
//                    @Override
//                    public void onNext(GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
//                        Log.e(TAG, "========onNext key()" + integerIntegerGroupedObservable.getKey());
//                        integerIntegerGroupedObservable.subscribe(new Observer<Integer>() {
//                            @Override
//                            public void onSubscribe(Disposable disposable) {
//
//                            }
//
//                            @Override
//                            public void onNext(Integer integer) {
//                                Log.e(TAG, "========onNext GroupedObservable " + integer);
//                            }
//
//                            @Override
//                            public void onError(Throwable throwable) {
//
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });
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
        //groupBy() end

        //17.scan() start
        //reduce()会聚集在一起才会发送事件，scan()每次处理一次数据都会发送
//        Observable.just(1,2,3,4,5,6)
//                .scan(new BiFunction<Integer, Integer, Integer>() {
//                    @Override
//                    public Integer apply(Integer integer, Integer integer2) throws Exception {
//                        Log.e(TAG, "========integer " + integer);
//                        Log.e(TAG, "========integer2 " + integer2);
//                        return integer + integer2;
//                    }
//                })
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "========accept " + integer);
//                    }
//                });
        //scan() end

        //18.window() start
//        Observable.just(1,2,3,4,5)
//                .window(2)
//                .subscribe(new Observer<Observable<Integer>>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//
//                    }
//
//                    @Override
//                    public void onNext(Observable<Integer> integerObservable) {
//                        integerObservable.subscribe(new Observer<Integer>() {
//                            @Override
//                            public void onSubscribe(Disposable disposable) {
//                                Log.e(TAG, "========onSubscribe ");
//                            }
//
//                            @Override
//                            public void onNext(Integer integer) {
//                                Log.e(TAG, "========onNext " + integer);
//                            }
//
//                            @Override
//                            public void onError(Throwable throwable) {
//
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });
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
        //window() end

        //19.concat() start 组合操作符
        //concatArray() 可以发送多于4个被观察者，concat()最多发送4个
//        Observable.concat(Observable.just(1,2),
//                Observable.just(3,4),
//                Observable.just(5,6),
//                Observable.just(7,8))
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//
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
//
//                    }
//                });
        //concat() end

        //20.merge() start
        //merge()并行发送事件，concat()是串行发送事件
        //mergeArray()可以发送4个以上被观察者，merge()最多4个
        //Observable.merge(
//        Observable.concat(
//                Observable.interval(1, TimeUnit.SECONDS)
//                        .map(new Function<Long, String>() {
//            @Override
//            public String apply(Long aLong) throws Exception {
//                return "A" + aLong;
//            }
//        }),
//        Observable.interval(1, TimeUnit.SECONDS)
//                .map(new Function<Long, String>() {
//                    @Override
//                    public String apply(Long aLong) throws Exception {
//                        return "B" + aLong;
//                    }
//                }))
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.e(TAG, "========onNext " + s);
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
        //merge() end

        //21.concatArrayDelayError() & mergeArrayDelayError() start
//        Observable.concatArrayDelayError(
//                Observable.create(new ObservableOnSubscribe<Integer>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
//                        observableEmitter.onNext(1);
//                        observableEmitter.onError(new NumberFormatException());
//                    }
//                }), Observable.just(2,3,4))
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.e(TAG, "========onNext " + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        Log.e(TAG, "========onError ");
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
        //concatArrayDelayError() & mergeArrayDelayError() end

        //22.zip() start
        //combineLatestDelayError(
        //Observable.combineLatest(
//        Observable.zip(
//                Observable.intervalRange(1, 5, 1, 1, TimeUnit.SECONDS)
//                        .map(new Function<Long, String>() {
//                            @Override
//                            public String apply(Long aLong) throws Exception {
//                                String s1 = "A" + aLong;
//                                Log.e(TAG, "========A 发送的事件  " + s1);
//                                return s1;
//                            }
//                        }),
//                Observable.intervalRange(1, 6, 2, 2, TimeUnit.SECONDS)
//                        .map(new Function<Long, String>() {
//                            @Override
//                            public String apply(Long aLong) throws Exception {
//                                String s2 = "B" + aLong;
//                                Log.e(TAG, "========A 发送的事件  " + s2);
//                                return s2;
//                            }
//                        }),
//                new BiFunction<String, String, String>() {
//                    @Override
//                    public String apply(String s, String s2) throws Exception {
//                        return s + s2;
//                    }
//                })
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.e(TAG, "========onNext  " + s);
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
        //zip() end

        //23.collect() start
//        Observable.just(1,2,3,4)
//                .collect(new Callable<ArrayList<Integer>>() {
//                             @Override
//                             public ArrayList<Integer> call() throws Exception {
//                                 return new ArrayList<>();
//                             }
//                         },
//                        new BiConsumer<ArrayList<Integer>, Integer>() {
//                            @Override
//                            public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
//                                integers.add(integer);
//                            }
//                        })
//                .subscribe(new Consumer<ArrayList<Integer>>() {
//                    @Override
//                    public void accept(ArrayList<Integer> integers) throws Exception {
//                        Log.e(TAG, "========accept  " + integers);
//                    }
//                });
        //collect() end

        //24.startWith() & startWithArray() start
//        Observable.just(5,6,7)
//                .startWithArray(2,3,4)
//                .startWith(1)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "========accept  " + integer);
//                    }
//                });
        //startWith() & startWithArray() end

        //25.count() start
//        Observable.just(1,2,3)
//                .count()
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Log.e(TAG, "========accept  " + aLong);
//                    }
//                });
        //count() end

        //26.delay() start
//        Observable.just(1,2,3)
//                .delay(3, TimeUnit.SECONDS)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//                        Log.e(TAG, "========onSubscribe  ");
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.e(TAG, "========onNext  " + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e(TAG, "========onComplete  ");
//                    }
//                });
        //delay() end

        //27.doOnEach() start
        //doOnNext()
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(1);
                observableEmitter.onNext(2);
                observableEmitter.onNext(3);
                observableEmitter.onComplete();
            }
        })
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "========doOnNext  " +integer);
                    }
                })
                .doOnEach(new Consumer<Notification<Integer>>() {
            @Override
            public void accept(Notification<Integer> integerNotification) throws Exception {
                Log.e(TAG, "========doOnEach  " + integerNotification.getValue());
            }
        })
                .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                Log.e(TAG, "========onSubscribe  ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "========onNext  "+integer);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, "========onError  ");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "========onComplete  ");
            }
        });
        //doOnEach() end

    }

    Integer i = 100;
}
