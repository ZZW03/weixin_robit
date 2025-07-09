package com.zzw;

import com.alibaba.fastjson.JSON;
import com.zzw.api.model.response.Response;
import com.zzw.domain.WxMessage.adapter.MessageOperation;
import com.zzw.domain.WxMessage.model.req.MessageRequest;
import com.zzw.domain.WxMessage.model.req.MsgItem;
import com.zzw.domain.WxMessage.model.res.DataItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class main {

    @Autowired
    MessageOperation messageOperation;

    @Test
    public void test(){
        MessageRequest messageRequest = new MessageRequest();
        List<MsgItem> MsgItem = new ArrayList<>();
        MsgItem msgItem = new MsgItem();
        msgItem.setToUserName("wxid_76ca83wlg92p12");
        msgItem.setTextContent("你是傻逼");
        msgItem.setMsgType(0);
        MsgItem.add(msgItem);
        messageRequest.setMsgItem(MsgItem);
        Response<List<DataItem>> dataItemResponse = messageOperation.sendMessage(messageRequest);
        System.out.println(JSON.toJSONString(dataItemResponse));
    }

}
