package com.zzw.domain.QQMessage.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zzw.domain.QQMessage.model.websockekResp.MessageBaseElement;
import lombok.Data;

import java.util.List;

@Data
public class QQGroupMessage {
    @JsonProperty("group_id")
    private String groupId;
    private List<MessageBaseElement> message;
}
