package com.zzw.infrastuction.adapter.respository;

import com.alibaba.fastjson2.JSONObject;
import com.zzw.api.model.response.Response;
import com.zzw.domain.QQMessage.adapter.QQMessageOperation;
import com.zzw.domain.QQMessage.model.req.QQSimpleSendMessage;
import com.zzw.domain.QQMessage.model.resp.QQSendResponse;
import com.zzw.domain.QQMessage.model.websockekResp.QQPrivateMessage;
import com.zzw.infrastuction.util.QQHttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QQMessageRepository implements QQMessageOperation {

    @Value("${spring.qq.baseUrl}")
    private String baseUrl;

    @Autowired
    QQHttpClientUtils qqHttpClientUtils;

    @Autowired
    ChatClient chatClient;

    @Override
    public Response<QQSendResponse> sendMessage(QQPrivateMessage request) {
        QQSimpleSendMessage qqSimpleSendMessage = request.toQQSimpleSendMessage();
        log.info("发送消息,请求参数为:{}", JSONObject.toJSONString(request));
        String content = chatClient.prompt().call().content();
        qqSimpleSendMessage.getMessage().
        QQSendResponse post = qqHttpClientUtils.post(baseUrl + "send_private_msg", qqSimpleSendMessage, QQSendResponse.class);
        return Response.success(post);
    }

}
