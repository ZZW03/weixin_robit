package com.zzw.domain.QQMessage.model.websockekResp;

import lombok.Data;

@Data
public class TextMessageElement extends MessageBaseElement {
    private final String type = "text";
    private TextData data;

    @Data
    public static class TextData {
        private String text;
    }
}

