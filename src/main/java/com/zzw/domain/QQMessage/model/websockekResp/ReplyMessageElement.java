package com.zzw.domain.QQMessage.model.websockekResp;

import lombok.Data;

@Data
public class ReplyMessageElement extends MessageBaseElement{
    private final String type = "reply";

    private  ReplyData data;

    @Data
    public static class ReplyData {
        private String id;
    }

}
