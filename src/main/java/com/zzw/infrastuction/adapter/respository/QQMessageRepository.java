package com.zzw.infrastuction.adapter.respository;

import com.zzw.api.model.response.Response;
import com.zzw.domain.QQMessage.adapter.QQMessageOperation;
import com.zzw.domain.QQMessage.model.req.QQSimpleSendMessage;
import com.zzw.domain.QQMessage.model.resp.QQSendResponse;
import com.zzw.infrastuction.util.QQHttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QQMessageRepository implements QQMessageOperation {

    @Value("${spring.qq.baseUrl}")
    private String baseUrl;

    @Autowired
    QQHttpClientUtils qqHttpClientUtils;

    @Override
    public Response<QQSendResponse> sendMessage(QQSimpleSendMessage request) {
        QQSendResponse post = qqHttpClientUtils.post(baseUrl + "send_private_msg", request, QQSendResponse.class);
        return Response.success(post);
    }

}
