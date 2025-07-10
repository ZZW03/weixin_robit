package com.zzw.domain.QQMessage.model.websockekResp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zzw.domain.QQMessage.model.req.QQSimpleSendMessage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class QQPrivateMessage extends QQBaseMessage {
    private String messageType;
    private String userId;
    private Long messageId;
    private Long messageSeq;
    private String realSeq;
    private Sender sender;
    private List<MessageBaseElement> message;
    private String messageFormat;
    private String subType;
    private Long targetId;

    @Data
    public static class Sender {
        @JsonProperty("user_id")
        private String userId;
        private String nickname;
        private String card;
    }

    //调用模型去解决
    public QQSimpleSendMessage toQQSimpleSendMessage(){
        QQSimpleSendMessage qqSimpleSendMessage = new QQSimpleSendMessage();
        qqSimpleSendMessage.setUserId(this.getSender().getUserId());

        List<MessageBaseElement> message = new ArrayList<>();
        MessageBaseElement messageBaseElement = new MessageBaseElement();
        messageBaseElement.setType("text");
        messageBaseElement.setData(new MessageBaseElement.MessageData(""));
        message.add(messageBaseElement);
        qqSimpleSendMessage.setMessage(message);

        return qqSimpleSendMessage;
    }


}
