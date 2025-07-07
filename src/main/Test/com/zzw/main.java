package com.zzw;

import com.alibaba.fastjson.JSON;
import com.zzw.api.model.response.Response;
import com.zzw.domain.Message.adapter.MessageOperation;
import com.zzw.domain.Message.model.req.MessageRequest;
import com.zzw.domain.Message.model.req.MsgItem;
import com.zzw.domain.Message.model.res.DataItem;
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
        MsgItem.add(msgItem);
        messageRequest.setMsgItem(MsgItem);
        Response<List<DataItem>> dataItemResponse = messageOperation.sendMessage(messageRequest);
        System.out.println(JSON.toJSONString(dataItemResponse));
    }
}
