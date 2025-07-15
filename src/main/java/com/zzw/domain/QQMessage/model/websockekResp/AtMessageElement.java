package com.zzw.domain.QQMessage.model.websockekResp;

import lombok.Data;

@Data
public class AtMessageElement extends MessageBaseElement{

    private final String type = "at";

    private AtData data;

    @Data
    public  class AtData {
        Long qq;
    }


}
