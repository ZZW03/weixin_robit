package com.zzw.domain.QQMessage.model.websockekResp;

import lombok.Data;

@Data
public class ImageMessageElement extends MessageBaseElement {
    private final String type = "image";
    private ImageData data;

    @Data
    public static class ImageData {
        private String file;       // 图片文件名
        private String url;        // 图片 URL
        private String file_size;  // 文件大小（字符串）
        private String summary;    // 简要信息（通常为空）
        private int sub_type;      // 子类型（如有）
    }
}
