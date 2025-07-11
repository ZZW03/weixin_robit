package com.zzw.domain.QQMessage.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zzw.domain.QQMessage.model.websockekResp.MessageBaseElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class QQSimpleSendMessage {
    @JsonProperty("user_id")
    private String userId;
    private List<MessageBaseElement> message;

}
