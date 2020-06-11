package com.xiyou.core.transport;


import com.xiyou.common.dto.RpcRequest;

/**
 * @author xiyou
 * 客户端发送的接口
 */
public interface RpcClient {
    Object sendRpcRequest(RpcRequest rpcRequest);
}
