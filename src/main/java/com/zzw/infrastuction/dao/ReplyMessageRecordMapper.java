package com.zzw.infrastuction.dao;


import com.zzw.infrastuction.dao.po.ReplyMessageRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ReplyMessageRecordMapper {
    // 插入一条记录，返回影响行数
    int insert(ReplyMessageRecord record);

    // 根据主键删除
    int deleteById(@Param("id") Long id);

    // 根据主键更新（全部字段）
    int updateById(ReplyMessageRecord record);

    // 根据主键查询
    ReplyMessageRecord selectById(@Param("id") Long id);


    ReplyMessageRecord selectByMessageId(@Param("id") Long id);

    // 查询全部
    List<ReplyMessageRecord> selectAll();
}
