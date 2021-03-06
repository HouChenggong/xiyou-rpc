package com.xiyou;


import com.xiyou.core.registry.DefaultServiceRegistry;
import com.xiyou.core.transport.netty.NettyRpcServer;

/**
 * @author shuang.kou
 * @createTime 2020年05月10日 07:25:00
 */
public class NettyServerMain {
    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        DefaultServiceRegistry defaultServiceRegistry = new DefaultServiceRegistry();
        // 手动注册
        defaultServiceRegistry.register(helloService);
        NettyRpcServer socketRpcServer = new NettyRpcServer(9999);
        socketRpcServer.run();
    }
}
