package com.zzw.domain.QQMessage.model.websockekResp;

import lombok.Data;

@Data
public abstract class QQBaseMessage {
    private Long time;
    private Long self_id;
    private String post_type;
}
