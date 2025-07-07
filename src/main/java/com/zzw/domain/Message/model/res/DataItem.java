package com.zzw.domain.Message.model.res;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;
@Data
public class DataItem {
    @JSONField(name = "isSendSuccess")
    private boolean sendSuccess;

    private Resp resp;

    private String textContent;

    @JSONField(name = "toUSerName")
    private String toUserName;

    @Data
    public static class Resp {
        @JSONField(name = "base_response")
        private BaseResponse baseResponse;

        private int count;

        @JSONField(name = "chat_send_ret_list")
        private List<ChatSendRet> chatSendRetList;

        @Data
        public static class BaseResponse {
            private int ret;
            private Object errMsg;
        }

        @Data
        public static class ChatSendRet {
            private long ret;

            @JSONField(name = "toUserName")
            private ToUserName toUserName;

            private long msgId;
            private long clientMsgId;
            private long createTime;
            private long serverTime;
            private int type;
            private long newMsgId;

            @Data
            public static class ToUserName {
                private String str;
            }
        }
    }
}
