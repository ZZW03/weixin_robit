package com.zzw.domain.QQMessage.adapter;

import com.zzw.api.model.response.Response;
import com.zzw.domain.QQMessage.model.req.QQSimpleSendMessage;
import com.zzw.domain.QQMessage.model.resp.QQSendResponse;

public interface QQMessageOperation {

    Response<QQSendResponse> sendMessage(QQSimpleSendMessage request);


}
