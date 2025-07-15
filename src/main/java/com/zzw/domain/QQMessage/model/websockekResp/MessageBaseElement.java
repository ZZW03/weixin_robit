package com.zzw.domain.QQMessage.model.websockekResp;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextMessageElement.class, name = "text"),
        @JsonSubTypes.Type(value = FaceMessageElement.class, name = "face"),
        @JsonSubTypes.Type(value = ImageMessageElement.class, name = "image"),
        @JsonSubTypes.Type(value = ReplyMessageElement.class, name = "reply"),
        @JsonSubTypes.Type(value = AtMessageElement.class, name = "at"),
})
public abstract class MessageBaseElement {
    public abstract String getType();
}
