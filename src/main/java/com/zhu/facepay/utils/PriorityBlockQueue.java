package com.zhu.facepay.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName PriorityBlockjava
 * @Description TODO
 * @createTime 2022年11月24日 18:30:00
 */
@Slf4j
public class PriorityBlockQueue<T extends Node> extends PriorityQueue {

    public PriorityBlockQueue(Integer capacity, Comparator<Node> comparator) {
        super(capacity,comparator);
    }

    public synchronized  void push(T t) throws InterruptedException {
        t.incr();
        if (length() == 0) {
            log.info("notifyAll....");
            add(t);
            notifyAll();
        } else {
            add(t);
        }

    }

    public T remove() {
        return (T)poll();
    }

    public synchronized T removeBlock() throws InterruptedException {

        T t = getBlock();
        while (size() == 0) {
            log.info("queue is null ,thread is blocking...");
            wait();
        }
        return (T)poll();
    }

    public T get() {

        return (T)peek();
    }

    public synchronized  T getBlock() throws InterruptedException {

        T t = (T)peek();
        if (size() == 0) {
            wait();
        }

        return t;
    }

    public int length() {
        return size();
    }
}
