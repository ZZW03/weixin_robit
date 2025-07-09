package com.zzw.domain.QQMessage.model.websockekResp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MessageBaseElement {

    private String type;
    private MessageData data;

    @Data
    @AllArgsConstructor
    public static class MessageData {
        private String text;
    }
}
