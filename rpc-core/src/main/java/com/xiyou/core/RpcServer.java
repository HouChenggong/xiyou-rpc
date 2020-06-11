package com.xiyou.core;

import com.xiyou.common.enumres.RpcErrorMessageEnum;
import com.xiyou.common.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


/**
 * @author xiyou
 * RPC的客户端
 * 主要目的是接受客户端的情况，打开一个socket
 * 通过反射调用，把结果封装成对象序列化传输给客户端
 */
public class RpcServer {
    private ExecutorService threadPool;
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    public RpcServer() {
        // 线程池参数
        int corePoolSize = 10;
        int maximumPoolSizeSize = 100;
        long keepAliveTime = 1;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        this.threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSizeSize, keepAliveTime, TimeUnit.MINUTES, workQueue, threadFactory);
    }

    /**
     * 服务端主动注册服务
     * TODO 修改为注解然后扫描
     */
    public void register(Object service, int port) {
        if (null == service) {
            logger.error(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_NULL.getMessage(), service.getClass());
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_NULL);
        }
        try (ServerSocket server = new ServerSocket(port);) {
            logger.info("server starts...");
            Socket socket;
            while ((socket = server.accept()) != null) {
                logger.info("client connected");
                threadPool.execute(new ClientMessageHandlerThread(socket, service));
            }
        } catch (IOException e) {
            logger.error("occur IOException:", e);
        }
    }
}
