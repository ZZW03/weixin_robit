package com.zzw.api.model.response;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class Response<T> {

    @JSONField(name = "Code")
    private Integer code;

    @JSONField(name = "Data")
    private T data;

    @JSONField(name = "Text")
    private String text;

    public static <T> Response<T> success(T data) {
        return new Response<>(200, data, "访问成功");
    }

    public static <T> Response<T> error(String text) {
        return new Response<>(500, null, text);
    }

    public Response(Integer code, T data, String text) {
        this.code = code;
        this.data = data;
        this.text = text;
    }

    public Response() {}
}

