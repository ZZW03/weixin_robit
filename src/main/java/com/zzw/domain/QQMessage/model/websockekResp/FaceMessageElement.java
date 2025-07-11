package com.zzw.domain.QQMessage.model.websockekResp;

import lombok.Data;

@Data
public class FaceMessageElement extends MessageBaseElement {
    private final String type = "face";
    private FaceData data;

    @Data
    public static class FaceData {
        private String id;
        private int chainCount;
        private Raw raw;
    }

    @Data
    public static class Raw{
        private String faceText;
    }
}
