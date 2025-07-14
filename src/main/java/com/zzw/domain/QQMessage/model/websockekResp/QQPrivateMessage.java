package com.zzw.domain.QQMessage.model.websockekResp;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zzw.domain.QQMessage.model.req.QQSimpleSendMessage;
import com.zzw.infrastuction.dao.po.MessageRecord;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.ai.model.ModelOptionsUtils.OBJECT_MAPPER;


@Slf4j
@Data
public class QQPrivateMessage extends QQBaseMessage {
    @JSONField(name = "message_type")
    private String messageType;

    @JSONField(name = "user_id")
    private String userId;

    @JSONField(name = "message_id")
    private Long messageId;

    @JSONField(name = "message_seq")
    private Long messageSeq;

    @JSONField(name = "real_seq")
    private String realSeq;

    @JSONField(name = "sender")
    private Sender sender;

    @JSONField(name = "message")
    private List<MessageBaseElement> message;

    @JSONField(name = "message_format")
    private String messageFormat;

    @JSONField(name = "sub_type")
    private String subType;

    @JSONField(name = "target_id")
    private Long targetId;

    @JSONField(name = "self_id")
    private Long selfId;

    @JSONField(name = "post_type")
    private String postType;

    @JSONField(name = "time")
    private Long time;

    @Data
    public static class Sender {
        @JsonProperty("user_id")
        private String userId;
        private String nickname;
        private String card;
    }

    public MessageRecord toMessageRecord() {
        log.info(JSONObject.toJSONString(this));
        MessageRecord record = new MessageRecord();
        record.setMessageType(this.messageType);
        record.setUserId(this.userId);
        record.setMessageId(this.messageId);
        record.setMessageSeq(this.messageSeq);
        record.setRealSeq(this.realSeq);

        try {
            // sender 对象转成 JSON 字符串
            String senderJson = this.sender == null ? null : OBJECT_MAPPER.writeValueAsString(this.sender);
            record.setSender(senderJson);

            // message 列表转成 JSON 字符串
            String messageJson = (this.message == null || this.message.isEmpty()) ? null : OBJECT_MAPPER.writeValueAsString(this.message);
            record.setMessage(messageJson);
        } catch (JsonProcessingException e) {
            // 你可以根据项目需要决定如何处理异常，这里简化直接抛运行时异常
            throw new RuntimeException("JSON序列化失败", e);
        }

        record.setMessageFormat(this.messageFormat);
        record.setSubType(this.subType);
        record.setTargetId(this.targetId);

        record.setCreateTime(OffsetDateTime.now());
        record.setUpdateTime(OffsetDateTime.now());

        return record;
    }

}
