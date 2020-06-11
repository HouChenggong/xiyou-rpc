package com.xiyou.common.dto;

import com.xiyou.common.enumres.RpcResponseCode;
import lombok.Data;

import java.io.Serializable;


/**
 * @author xiyou
 * 对RPC结果调用的封装
 */
@Data
public class RpcResponse<T> implements Serializable {

    private static final long serialVersionUID = 715745410605631233L;
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(RpcResponseCode.SUCCESS.getCode());
        if (null != data) {
            response.setData(data);
        }
        return response;
    }

    public static <T> RpcResponse<T> fail(RpcResponseCode rpcResponseCode) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(rpcResponseCode.getCode());
        response.setMessage(rpcResponseCode.getMessage());
        return response;
    }

    public static <T> RpcResponse<T> fail(int code, String msg) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setCode(code);
        response.setMessage(msg);
        return response;
    }
}
