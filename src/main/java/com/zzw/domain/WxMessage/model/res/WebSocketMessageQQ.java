package com.zzw.domain.WxMessage.model.res;


import com.zzw.domain.QQMessage.model.websockekResp.MessageBaseElement;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WebSocketMessageQQ {

    public long self_id;
    public long user_id;
    public long time;
    public long message_id;
    public long message_seq;
    public long real_id;
    public String real_seq;
    public String message_type;
    public Sender sender;
    public String raw_message;
    public int font;
    public String sub_type;
    public List<MessageBaseElement> message;
    public String message_format;
    public String post_type;
    public long target_id;
    public Raw raw;

    @Data
    public static class Sender {
        public long user_id;
        public String nickname;
        public String card;
    }


    @Data
    public static class Raw {
        public String msgId;
        public String msgRandom;
        public String msgSeq;
        public String cntSeq;
        public int chatType;
        public int msgType;
        public int subMsgType;
        public int sendType;
        public String senderUid;
        public String peerUid;
        public String channelId;
        public String guildId;
        public String guildCode;
        public String fromUid;
        public String fromAppid;
        public String msgTime;
        public Map<String, Object> msgMeta;
        public int sendStatus;
        public String sendRemarkName;
        public String sendMemberName;
        public String sendNickName;
        public String guildName;
        public String channelName;
        public List<Element> elements;
        public List<Object> records;
        public List<Object> emojiLikesList;
        public String commentCnt;
        public int directMsgFlag;
        public List<Object> directMsgMembers;
        public String peerName;
        public Object freqLimitInfo;
        public boolean editable;
        public String avatarMeta;
        public String avatarPendant;
        public String feedId;
        public String roleId;
        public String timeStamp;
        public Object clientIdentityInfo;
        public boolean isImportMsg;
        public int atType;
        public int roleType;
        public RoleInfo fromChannelRoleInfo;
        public RoleInfo fromGuildRoleInfo;
        public RoleInfo levelRoleInfo;
        public String recallTime;
        public boolean isOnlineMsg;
        public Map<String, Object> generalFlags;
        public String clientSeq;
        public Object fileGroupSize;
        public Object foldingInfo;
        public Object multiTransInfo;
        public String senderUin;
        public String peerUin;
        public Map<String, Object> msgAttrs;
        public Object anonymousExtInfo;
        public int nameType;
        public int avatarFlag;
        public Object extInfoForUI;
        public Object personalMedal;
        public int categoryManage;
        public Object msgEventInfo;
        public int sourceType;
        public long id;

        public static class Element {
            public int elementType;
            public String elementId;
            public int elementGroupId;
            public Map<String, Object> extBufForUI;
            public TextElement textElement;

            public static class TextElement {
                public String content;
                public int atType;
                public String atUid;
                public String atTinyId;
                public String atNtUid;
                public int subElementType;
                public String atChannelId;
                public Object linkInfo;
                public String atRoleId;
                public int atRoleColor;
                public String atRoleName;
                public int needNotify;
            }
        }

        public static class RoleInfo {
            public String roleId;
            public String name;
            public int color;
        }
    }
}

