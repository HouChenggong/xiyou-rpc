package com.xiyou.common.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


/**
 * @author xiyou
 * 请求对象的封装
 */
@Data
@Builder
public class RpcRequest implements Serializable {

    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;

}
