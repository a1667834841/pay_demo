package com.zhu.facepay.utils;

import cn.hutool.core.map.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName SseEmitterServer.java
 * @Description TODO
 * @createTime 2022年11月28日 20:54:00
 */
public class SseEmitterServer {
    private static final Logger logger = LoggerFactory.getLogger(SseEmitterServer.class);

    /**
     * 当前连接数
     */
    private static AtomicInteger count = new AtomicInteger(0);

    /**
     * 使用map对象，便于根据userId来获取对应的SseEmitter，或者放redis里面
     */
    private static Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    /**
     * 创建用户连接并返回 SseEmitter
     *
     * @param userId 用户ID
     * @return SseEmitter
     */
    public static SseEmitter connect(String userId) {
        // 设置超时时间，0表示不过期。默认30秒，超过时间未完成会抛出异常：AsyncRequestTimeoutException
        SseEmitter sseEmitter = new SseEmitter(0L);
        // 注册回调
        sseEmitter.onCompletion(completionCallBack(userId));
        sseEmitter.onError(errorCallBack(userId));
        sseEmitter.onTimeout(timeoutCallBack(userId));
        sseEmitterMap.put(userId, sseEmitter);
        // 数量+1
        count.getAndIncrement();
        logger.info("创建新的sse连接，当前用户：{}", userId);
        return sseEmitter;
    }

    /**
     * 给指定用户发送信息  -- 单播
     */
    public static void sendMessage(String userId, String message) {
        if (sseEmitterMap.containsKey(userId)) {
            try {
                // sseEmitterMap.get(userId).send(message, MediaType.APPLICATION_JSON);
                System.out.println(userId + "==" + message);
                sseEmitterMap.get(userId).send(message);
            } catch (IOException e) {
                logger.error("用户[{}]推送异常:{}", userId, e.getMessage());
                removeUser(userId);
            }
        }
    }

    /**
     * 向多人发布消息   -- 组播
     * @param groupId 开头标识
     * @param message 消息内容
     */
    public static void groupSendMessage(String groupId, String message) {
        if (MapUtil.isNotEmpty(sseEmitterMap)) {
            /*Set<String> ids = sseEmitterMap.keySet().stream().filter(m -> m.startsWith(groupId)).collect(Collectors.toSet());
            batchSendMessage(message, ids);*/
            sseEmitterMap.forEach((k, v) -> {
                try {
                    if (k.startsWith(groupId)) {
                        v.send(message, MediaType.APPLICATION_JSON);
                    }
                } catch (IOException e) {
                    logger.error("用户[{}]推送异常:{}", k, e.getMessage());
                    removeUser(k);
                }
            });
        }
    }

    /**
     * 群发所有人   -- 广播
     */
    public static void batchSendMessage(String message) {
        sseEmitterMap.forEach((k, v) -> {
            try {
                v.send(message, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                logger.error("用户[{}]推送异常:{}", k, e.getMessage());
                removeUser(k);
            }
        });
    }

    /**
     * 群发消息
     */
    public static void batchSendMessage(String message, Set<String> ids) {
        ids.forEach(userId -> sendMessage(userId, message));
    }

    /**
     * 移除用户连接
     */
    public static void removeUser(String userId) {
        sseEmitterMap.remove(userId);
        // 数量-1
        count.getAndDecrement();
        logger.info("移除用户：{}", userId);
    }

    /**
     * 获取当前连接信息
     */
    public static List<String> getIds() {
        return new ArrayList<>(sseEmitterMap.keySet());
    }

    /**
     * 获取当前连接数量
     */
    public static int getUserCount() {
        return count.intValue();
    }

    private static Runnable completionCallBack(String userId) {
        return () -> {
            logger.info("结束连接：{}", userId);
            removeUser(userId);
        };
    }

    private static Runnable timeoutCallBack(String userId) {
        return () -> {
            logger.info("连接超时：{}", userId);
            removeUser(userId);
        };
    }

    private static Consumer<Throwable> errorCallBack(String userId) {
        return throwable -> {
            logger.info("连接异常：{}", userId);
            removeUser(userId);
        };
    }
}
