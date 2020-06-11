package com.xiyou.core;

import com.xiyou.common.dto.RpcRequest;
import com.xiyou.common.dto.RpcResponse;
import com.xiyou.common.enumres.RpcErrorMessageEnum;
import com.xiyou.common.enumres.RpcResponseCode;
import com.xiyou.common.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * @author xiyou
 * RPC客户端发送请求并接受请求
 * 对异常做特殊处理
 */
public class RpcClient {
    public static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    public Object sendRpcRequest(RpcRequest rpcRequest, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            RpcResponse rpcResponse = (RpcResponse) objectInputStream.readObject();
            if (rpcResponse == null) {
                logger.error("调用服务失败,serviceName:{} error is [{}]", rpcRequest.getInterfaceName(), RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE.getMessage());
                throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, "interfaceName:" + rpcRequest.getInterfaceName());
            }
            if (rpcResponse.getCode() == null || rpcResponse.getCode() != (RpcResponseCode.SUCCESS.getCode())) {
                logger.error("调用服务失败,serviceName :" + rpcRequest.getInterfaceName() + "RpcResponse:" + rpcResponse.getMessage());
                throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, "interfaceName:" + rpcRequest.getInterfaceName());
            }
            return rpcResponse.getData();
        } catch (IOException | ClassNotFoundException e) {
            throw new RpcException("调用服务失败:", e);
        }
    }
}
