package com.xiyou;

import com.xiyou.core.registry.DefaultServiceRegistry;
import com.xiyou.core.remote.socket.RpcServer;

/**
 * @author xiyou
 * @version 1.0
 * @date 2020/6/11 17:48
 * RPC服务端注册
 */
public class RpcServerMain {
    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        DefaultServiceRegistry defaultServiceRegistry = new DefaultServiceRegistry();
        // 手动注册
        defaultServiceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(defaultServiceRegistry);
        rpcServer.start(9999);
    }
}
