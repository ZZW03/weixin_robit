package com.zzw.domain.Message.adapter;

import com.zzw.api.model.response.Response;
import com.zzw.domain.Message.model.req.MessageRequest;
import com.zzw.domain.Message.model.res.DataItem;

import java.util.List;

public interface MessageOperation {

    Response<List<DataItem>> sendMessage(MessageRequest request);


}
