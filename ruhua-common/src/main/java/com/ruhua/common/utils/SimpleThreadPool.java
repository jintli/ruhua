package com.ruhua.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: yinkui1
 * Date: 14-7-17
 * Time: 下午5:21
 * To change this template use File | Settings | File Templates.
 */
public class SimpleThreadPool {

    // 固定线程池 线程数量为cpu核心数加5
    private static final ExecutorService eservice = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 5);

    public static void execute(Runnable r){
        eservice.execute(r);
    }

}
