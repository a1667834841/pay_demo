package com.zhu.facepay.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName QueueCore.java
 * @Description 队列功能
 * @createTime 2022年11月23日 20:43:00
 */
@Slf4j
public class QueueCore<T extends Node> {

    private  String jobName;

    private static ConcurrentHashMap<String, PriorityBlockQueue> map = new ConcurrentHashMap<>();

    private static final Integer CAPACITY = 1024;

    public QueueCore (String jobName) {
        this.jobName = jobName;
        map.putIfAbsent(jobName, new PriorityBlockQueue<T>(CAPACITY,comparator));
    }

    public PriorityBlockQueue get(String jobName) {
        return map.get(jobName);
    }


    static Comparator<Node> comparator=new Comparator<Node>() {
        public int compare(Node o1, Node o2) {
            return o1.getSeedTime().compareTo(o2.getSeedTime());
        }

    };

}
