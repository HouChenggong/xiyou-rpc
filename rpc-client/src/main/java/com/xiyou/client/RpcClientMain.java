package com.xiyou.client;

import com.xiyou.Hello;
import com.xiyou.HelloService;
import com.xiyou.ISayHello;
import com.xiyou.core.transport.socket.SocketRpcClientProxy;

/**
 * @author xiyou
 * @version 1.0
 * @date 2020/6/11 17:50
 */
public class RpcClientMain {
    public static void main(String[] args) {
        SocketRpcClientProxy socketRpcClientProxy = new SocketRpcClientProxy("127.0.0.1", 9999);
        HelloService helloService = socketRpcClientProxy.getProxy(HelloService.class);
        String hello = helloService.hello(new Hello("111", "222"));
        System.out.println(hello);
        ISayHello sayHello = socketRpcClientProxy.getProxy(ISayHello.class);
        String sayHelloRes = sayHello.sayHello( "I am xiyou");
        System.out.println(sayHelloRes);
    }
}
