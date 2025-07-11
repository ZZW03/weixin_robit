package com.zzw.infrastuction.adapter.respository;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zzw.api.Constant;
import com.zzw.api.enums.QQFaceEnum;
import com.zzw.api.enums.QQMessageTypeEnum;
import com.zzw.api.model.response.Response;
import com.zzw.domain.QQMessage.adapter.QQMessageOperation;
import com.zzw.domain.QQMessage.model.req.QQSimpleSendMessage;
import com.zzw.domain.QQMessage.model.resp.QQSendResponse;
import com.zzw.domain.QQMessage.model.websockekResp.*;
import com.zzw.infrastuction.util.QQHttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.zzw.api.Constant.DEEPSEEK_MEMORY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Component
@Slf4j
public class QQMessageRepository implements QQMessageOperation {

    @Value("${spring.qq.baseUrl}")
    private String baseUrl;

    @Autowired
    QQHttpClientUtils qqHttpClientUtils;

    @Autowired
    ChatClient chatClient;

    private final ConcurrentHashMap<String, List<Message>> historyMap = new ConcurrentHashMap<>();

        @Override
        public Response<QQSendResponse> sendMessage(QQPrivateMessage request){
            String sendUserId = request.getSender().getUserId();

            List<Message> userMsgThisRound = null;

            try {
                userMsgThisRound = buildUserMsg(request);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            List<Message> allMsgs = new ArrayList<>(
                    historyMap.getOrDefault(sendUserId, new ArrayList<>()));
            allMsgs.addAll(userMsgThisRound);

            String assistantText = chatClient.prompt("你收到如下的信息\r\n")
                    .messages(allMsgs)
                    .call()
                    .content();

            allMsgs.add(new AssistantMessage(assistantText));
            // 只保留最近 100 条，避免 token 爆掉
            if (allMsgs.size() > 10) {
                allMsgs = allMsgs.subList(allMsgs.size() - 10, allMsgs.size());
            }
            historyMap.put(sendUserId, allMsgs);

            QQSimpleSendMessage qqMsg = JSONObject.parseObject(assistantText, QQSimpleSendMessage.class);
            qqMsg.setUserId(sendUserId);
            QQSendResponse resp = qqHttpClientUtils.post(baseUrl + "send_private_msg", qqMsg, QQSendResponse.class);
            return Response.success(resp);
        }


    public String urlToBase64(String imageUrl) throws Exception {
        // 创建URL对象
        URL url = new URL(imageUrl);
        // 打开连接到此 URL 的输入流
        try (InputStream in = url.openStream()) {
            // 使用ByteArrayOutputStream捕获字节流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int n = 0;
            // 读取输入流中的数据，并写入到ByteArrayOutputStream
            while (-1 != (n = in.read(buffer))) {
                out.write(buffer, 0, n);
            }
            // 将字节数组转换为Base64编码的字符串
            return Base64.getEncoder().encodeToString(out.toByteArray());
        }
    }

    public String urlToBase64DataUrl(String imageUrl, String format) throws Exception {
        String base64 = urlToBase64(imageUrl);
        return "data:image/" + format + ";base64," + base64;
    }

    private List<Message> buildUserMsg(QQPrivateMessage request) throws Exception {
        List<Message> list = new ArrayList<>();
        for (MessageBaseElement v : request.getMessage()) {
            if (QQMessageTypeEnum.TEXT.getDesc().equals(v.getType())) {
                String txt = ((TextMessageElement) v).getData().getText();
                list.add(new UserMessage(txt));

            } else if (QQMessageTypeEnum.FACE.getDesc().equals(v.getType())) {
                FaceMessageElement f = (FaceMessageElement) v;
                String faceText = Optional.ofNullable(f.getData().getRaw().getFaceText())
                        .orElse(QQFaceEnum.getDescByCode(Integer.parseInt(f.getData().getId())));
                list.add(new UserMessage("收到的表情内容是:" + faceText));

            } else if (QQMessageTypeEnum.IMAGE.getDesc().equals(v.getType())) {
//                ImageMessageElement imageMessageElement = (ImageMessageElement) v;
//                String base64 = urlToBase64DataUrl(imageMessageElement.getData().getUrl(), "png");
//                list.add(new UserMessage("收到的图片的 base64 是:" + base64));
            } else if (QQMessageTypeEnum.REPLY.getDesc().equals(v.getType())) {
                ReplyMessageElement replyMessageElement = (ReplyMessageElement) v;
                list.add(new AssistantMessage("用户引用了你这条回答: 我是帅哥"));
            }
        }
        return list;
    }



}
