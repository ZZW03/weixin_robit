package com.zzw.domain.QQMessage.adapter;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IAiAgentRagService {

    void storeRagFile(String name, String tag, List<MultipartFile> files);

}
