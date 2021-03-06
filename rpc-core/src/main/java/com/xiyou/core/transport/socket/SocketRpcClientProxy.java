package com.xiyou.core.transport.socket;


import com.xiyou.common.dto.RpcRequest;
import com.xiyou.core.transport.socket.SocketRpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * @author xiyou
 * 客户端JDK动态代理对象
 * 主要目的是生成每一个IService接口的Proxy对象
 */
public class SocketRpcClientProxy implements InvocationHandler {
    private String host;
    private int port;

    public SocketRpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RpcRequest rpcRequest = RpcRequest.builder().methodName(method.getName())
                .parameters(args)
                .interfaceName(method.getDeclaringClass().getName())
                .paramTypes(method.getParameterTypes())
                .build();
        SocketRpcClient socketRpcClient = new SocketRpcClient();
        return socketRpcClient.sendRpcRequest(rpcRequest, host, port);
    }
}
