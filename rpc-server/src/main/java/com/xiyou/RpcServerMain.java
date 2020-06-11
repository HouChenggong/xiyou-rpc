package com.xiyou;

import com.xiyou.core.RpcServer;

/**
 * @author xiyou
 * @version 1.0
 * @date 2020/6/11 17:48
 * RPC服务端注册
 */
public class RpcServerMain {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9999);
    }
}
