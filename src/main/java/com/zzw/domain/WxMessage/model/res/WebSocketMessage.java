package com.zzw.domain.WxMessage.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zzw.domain.WxMessage.model.req.MessageRequest;
import com.zzw.domain.WxMessage.model.req.MsgItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WebSocketMessage {

    @JsonProperty("msg_id")
    private long msgId;

    @JsonProperty("from_user_name")
    private UserName fromUserName;

    @JsonProperty("to_user_name")
    private UserName toUserName;

    @JsonProperty("msg_type")
    private int msgType;

    private Content content;

    private int status;

    @JsonProperty("img_status")
    private int imgStatus;

    @JsonProperty("img_buf")
    private ImgBuf imgBuf;

    @JsonProperty("create_time")
    private long createTime;

    @JsonProperty("msg_source")
    private String msgSource;

    @JsonProperty("new_msg_id")
    private long newMsgId;


    @Data
    public static class UserName {
        private String str;
    }

    @Data
    public static class Content {
        private String str;
    }

    @Data
    public static class ImgBuf {
        private int len;
    }

    public MessageRequest toMessageRequest(){
        MessageRequest messageRequest = new MessageRequest();
        List<MsgItem> msgItems = new ArrayList<>();
        MsgItem msgItem = new MsgItem();
        messageRequest.setMsgItem(msgItems);
        msgItems.add(msgItem);
        msgItem.setMsgType(0);
        msgItem.setToUserName(this.getFromUserName().getStr());
        msgItem.setTextContent(this.getContent().getStr());
        return messageRequest;
    }

}

