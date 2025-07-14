package com.zzw.infrastuction.dao;


import com.zzw.infrastuction.dao.po.MessageRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageRecordMapper  {

    // 插入一条记录
    int insert(MessageRecord record);

    // 根据主键删除
    int deleteById(Long id);

    // 根据主键更新全部字段（除了主键）
    int updateById(MessageRecord record);

    // 根据主键查询
    MessageRecord selectById(Long id);

    // 查询全部（示例）
    List<MessageRecord> selectAll();

    // 根据 userId 查询（示例）
    List<MessageRecord> selectByUserId(@Param("userId") String userId);

}
