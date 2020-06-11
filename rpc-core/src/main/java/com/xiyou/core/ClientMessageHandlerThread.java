package com.xiyou.core;


import com.xiyou.common.dto.RpcRequest;
import com.xiyou.common.dto.RpcResponse;
import com.xiyou.common.enumres.RpcResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ResponseCache;
import java.net.Socket;

/**
 * @author shuang.kou
 * @createTime 2020年05月10日 09:18:00
 */
public class ClientMessageHandlerThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientMessageHandlerThread.class);
    private Socket socket;
    private Object service;

    public ClientMessageHandlerThread(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        // 注意使用 try-with-resources ,因为这样更加优雅
        // 并且,try-with-resources 语句在编写必须关闭资源的代码时会更容易，也不会出错
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = invokeTargetMethod(rpcRequest);
            RpcResponse res = (RpcResponse) result;
            objectOutputStream.writeObject(res);
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error("occur exception:", e);
        }
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
        Class<?> cls = Class.forName(rpcRequest.getInterfaceName());
        // 判断类是否实现了对应的接口
        if (!cls.isAssignableFrom(service.getClass())) {
            logger.error(RpcResponseCode.NOT_FOUND_CLASS.getMessage(), service.getClass());
            RpcResponse response = RpcResponse.fail(RpcResponseCode.NOT_FOUND_CLASS);
            return response;
        }
        Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        if (null == method) {
            logger.error(RpcResponseCode.NOT_FOUND_METHOD.getMessage(), service.getClass());
            return RpcResponse.fail(RpcResponseCode.NOT_FOUND_METHOD);
        }
        Object res = method.invoke(service, rpcRequest.getParameters());
        return RpcResponse.success(res);
    }
}
