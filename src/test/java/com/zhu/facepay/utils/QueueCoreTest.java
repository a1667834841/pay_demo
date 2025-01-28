package com.zhu.facepay.utils;

import com.zhu.facepay.domain.PayBill;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

/**
 * 1.@FixMethodOrder(MethodSorters.JVM)
 * 从上到下 执行@Test
 *
 * 2.@FixMethodOrder(MethodSorters.NAME_ASCENDING) （推荐）
 * 按方法名字顺序执行@Test
 *
 * 3.@FixMethodOrder(MethodSorters.DEFAULT)
 * 默认方法，不可预期
 */
@FixMethodOrder(MethodSorters.JVM)
class QueueCoreTest {

    PriorityBlockQueue<PayBill> payBillQueueCore = new QueueCore<PayBill>("test").get("test");


    @Test
    void push() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            PayBill payBill = new PayBill();
            payBill.setNotifyId(i+"");
            payBillQueueCore.push(payBill);
        }
        System.out.println("payBillQueueCore = " + payBillQueueCore);

    }

    @Test
    void removeBlock() throws InterruptedException {

        new Thread(() -> {
            int count = 0;
            while (true) {
                try {
                    PayBill payBill = new PayBill();
                    payBill.setNotifyId(count+"");
                    payBillQueueCore.push(payBill);
                    Thread.sleep(100);
                    count++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();

        Thread.sleep(1000);

        while (true) {
            System.out.println(payBillQueueCore.removeBlock());
        }
    }



    @Test
    void get() {
        while (payBillQueueCore.length() > 0) {
            System.out.println(payBillQueueCore.get());
        }
    }

    @Test
    void remove() {
    }

    @Test
    void length() {
    }
}