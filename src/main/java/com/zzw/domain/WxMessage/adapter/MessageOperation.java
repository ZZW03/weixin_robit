package com.zzw.domain.WxMessage.adapter;

import com.zzw.api.model.response.Response;
import com.zzw.domain.WxMessage.model.req.MessageRequest;
import com.zzw.domain.WxMessage.model.res.DataItem;

import java.util.List;

public interface MessageOperation {

    Response<List<DataItem>> sendMessage(MessageRequest request);


}
