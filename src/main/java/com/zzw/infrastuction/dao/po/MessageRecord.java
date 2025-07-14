package com.zzw.infrastuction.dao.po;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
public class MessageRecord {

    private Long id;

    private String messageType;

    private String userId;

    private Long messageId;

    private Long messageSeq;

    private String realSeq;

    // 直接用 String 存 JSON 字符串
    private String sender;

    private String message;

    private String messageFormat;

    private String subType;

    private Long targetId;

    private OffsetDateTime createTime;

    private OffsetDateTime updateTime;
}



