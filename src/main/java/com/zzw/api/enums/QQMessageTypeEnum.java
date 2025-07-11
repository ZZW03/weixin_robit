package com.zzw.api.enums;

public enum QQMessageTypeEnum implements BaseEnum{
    TEXT(1,"text"),
    FACE(2,"face"),
    IMAGE(3,"image"),
    REPLY(4,"reply")
    ;
    private Integer code;
    private String desc;

    QQMessageTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
