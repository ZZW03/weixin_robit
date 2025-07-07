package com.zzw.domain.Message.model.req;

import java.util.List;

public class MsgItem {
    private List<String> AtWxIDList;
    private String ImageContent;
    private int MsgType;
    private String TextContent;
    private String ToUserName;

    public List<String> getAtWxIDList() {
        return AtWxIDList;
    }

    public void setAtWxIDList(List<String> atWxIDList) {
        AtWxIDList = atWxIDList;
    }

    public String getImageContent() {
        return ImageContent;
    }

    public void setImageContent(String imageContent) {
        ImageContent = imageContent;
    }

    public int getMsgType() {
        return MsgType;
    }

    public void setMsgType(int msgType) {
        MsgType = msgType;
    }

    public String getTextContent() {
        return TextContent;
    }

    public void setTextContent(String textContent) {
        TextContent = textContent;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }
}
