package com.zhu.facepay.dao;

import com.zhu.facepay.domain.PayBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName PayBillRepository.java
 * @Description TODO
 * @createTime 2022年11月22日 11:41:00
 */
@Repository
public interface  PayBillRepository extends JpaRepository<PayBill, Long> {
    PayBill getPayBillByOrderNum(String orderNum);


    /**
     * https://blog.csdn.net/qq_34359363/article/details/103727228
     * @param payBill
     */
    @Modifying
    @Transactional
    @Query("update PayBill p set p.isPay =:#{#payBill.isPay},p.sellerId =:#{#payBill.sellerId},p.payerId =:#{#payBill.payerId}")
    void updatePayBillByOrderNum(@Param("payBill") PayBill payBill);

    PayBill getPayBillByNotifyId(String notifyId);

}
