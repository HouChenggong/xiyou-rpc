package com.xiyou.client;


import com.xiyou.Hello;
import com.xiyou.HelloService;
import com.xiyou.core.transport.RpcClient;
import com.xiyou.core.transport.RpcClientProxy;
import com.xiyou.core.transport.netty.NettyRpcClient;

/**
 * @author shuang.kou
 * @createTime 2020年05月10日 07:25:00
 */
public class NettyClientMain {
    public static void main(String[] args) {
        RpcClient rpcClient=new NettyRpcClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        String hello = helloService.hello(new Hello("111", "222"));
        System.out.println(hello);
    }
}
