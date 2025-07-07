package com.zzw.domain.Message.model.req;

import java.util.List;

public class MessageRequest {
    private List<MsgItem> MsgItem;

    public List<MsgItem> getMsgItem() {
        return MsgItem;
    }

    public void setMsgItem(List<MsgItem> msgItem) {
        this.MsgItem = msgItem;
    }
}
