package com.xiyou.core.registry;


/**
 * @author xiyou
 * 注册并获取service
 */
public interface ServiceRegistry {
    <T> void register(T service);

    Object getService(String serviceName);
}
