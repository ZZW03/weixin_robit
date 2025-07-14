package com.zzw.infrastuction.dao.po;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ReplyMessageRecord {

    private Long id;

    private Long replyMessageId;

    private String replyMessageContent;

    private OffsetDateTime createTime;

    private OffsetDateTime updateTime;
}