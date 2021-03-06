package com.ourlife.base.jdk.referencedemo;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangchao
 * @createdOn 2020/6/11
 */
public class ReferenceDemo {

    public static void main(String[] args) {
        myReferenceQueue();

//        myHashMap();
//        myWeakHashMap();

//        strongReference();
//        softReference();
//        weakReference();
    }

    private static void myReferenceQueue() {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        WeakReference<Object> weakReference = new WeakReference<>(o1, referenceQueue);

        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("========GC=======");

        o1 = null;
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());
    }

    /**
     * hashMap 的引用举例
     */
    private static void myHashMap() {
        HashMap<Integer, String> hashMap = new HashMap<>();
        Integer key = new Integer(2);
        String value = "hashMap";

        hashMap.put(key, value);
        System.out.println(hashMap);

        key = null;
        System.out.println(hashMap);

        System.gc();
        System.out.println(hashMap + "\t" + hashMap.size());
    }

    /**
     * 弱引用的举例。只要GC就会回收
     */
    public static void myWeakHashMap() {
        WeakHashMap<Integer, String> hashMap = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "weakHashMap";

        hashMap.put(key, value);
        System.out.println(hashMap);

        key = null;
        System.out.println(hashMap);

        System.gc();
        System.out.println(hashMap + "\t" + hashMap.size());
    }

    /**
     * 强引用：必须的对象，即使要发生oom垃圾收集器也不会回收此类对象
     */
    private static void strongReference() {
        Object o1 = new Object();
        Object o2 = o1;

        o1 = null;
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(o2);
    }

    /**
     * 软引用：有用但非必须的对象，在OOM前，会回收此类对象
     * 测试条件
     * 1.不配置堆大小，调用GC，在不产生OOM的情况下观察软引用是否被回收    -- 不会被回收
     * 2.设置堆比较小(10M)，触发GC，在OOM前的GC观察软引用是否被回收     --被回收
     */
    private static void softReference() {
        Object object = new Object();
        SoftReference<Object> softReference = new SoftReference<>(new Object());

        System.out.println("=====GC前(不发生OOM)======");
        System.out.println(object);
        System.out.println(softReference.get());

        System.gc();
        try {
            //让GC执行完
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=====GC后(不发生OOM)======");
        System.out.println(object);
        System.out.println(softReference.get());


        Object object1 = new Object();
        SoftReference<Object> softReference1 = new SoftReference<>(new Object());

        System.out.println("=====GC前(发生OOM)======");
        System.out.println(object1);
        System.out.println(softReference1.get());

        try {
            //创建大对象，触发OOM
            byte[] bytes = new byte[1024 * 1024 * 10];
        } finally {
            System.out.println("=====GC后(发生OOM)======");
            System.out.println(object1);
            System.out.println(softReference1.get());
        }
    }

    /**
     * 弱引用：非必须对象，在下次垃圾回收时，就会被回收
     */
    private static void weakReference() {
        Object object = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(new Object());

        System.out.println("=====GC前======");
        System.out.println(object);
        System.out.println(weakReference.get());

        System.gc();
        try {
            //让GC执行完
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=====GC后======");
        System.out.println(object);
        System.out.println(weakReference.get());
    }
}
