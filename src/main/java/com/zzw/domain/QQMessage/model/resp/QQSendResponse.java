package com.zzw.domain.QQMessage.model.resp;

import lombok.Data;

@Data
public class QQSendResponse<T> {
    private Status status;     // ok / error
    private int retcode;       // 数值型状态码
    private T data;            // 泛型数据体
    private String errMsg;     // 错误消息
    private String message;    // 错误消息（备用）
    private String wording;    // 用户友好的提示信息
    private String echo;       // 回显字段

    public enum Status {
        ok, error,failed
    }



}
