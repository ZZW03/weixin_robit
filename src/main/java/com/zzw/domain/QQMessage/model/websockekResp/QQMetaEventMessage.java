package com.zzw.domain.QQMessage.model.websockekResp;

import lombok.Data;

@Data
public class QQMetaEventMessage extends QQBaseMessage {
    private String meta_event_type;
    private Status status;
    private Integer interval;

    @Data
    public static class Status {
        private boolean online;
        private boolean good;
    }
}