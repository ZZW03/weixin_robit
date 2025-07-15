package com.zzw.domain.QQMessage.adapter;

import com.zzw.api.model.response.Response;
import com.zzw.domain.QQMessage.model.req.QQSimpleSendMessage;
import com.zzw.domain.QQMessage.model.resp.QQSendResponse;
import com.zzw.domain.QQMessage.model.websockekResp.QQPrivateMessage;

public interface QQMessageOperation {

    Response<QQSendResponse> sendPrivateMessage(QQPrivateMessage request) throws Exception;

    Response<QQSendResponse> sendGroupMessage(QQPrivateMessage request);


}
