package com.zzw.trigger.http;

import com.alibaba.fastjson.JSON;
import com.zzw.api.model.response.Response;
import com.zzw.domain.QQMessage.adapter.IAiAgentRagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/rag/")
public class RagController {

    @Autowired
    IAiAgentRagService aiAgentRagService;

    @RequestMapping(value = "file/upload", method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    public Response<Boolean> uploadRagFile(@RequestParam("name") String name, @RequestParam("tag") String tag, @RequestParam("files") List<MultipartFile> files) {
        try {
            log.info("上传知识库，请求 {}", name);
            aiAgentRagService.storeRagFile(name, tag, files);
            Response<Boolean> response = Response.success(Boolean.TRUE);
            log.info("上传知识库，结果 {} {}", name, JSON.toJSONString(response));
            return response;
        } catch (Exception e) {
            log.error("上传知识库，异常 {}", name, e);
            return Response.error("上传错误");
        }
    }
}
