package com.yj.yuaiagent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yj.yuaiagent.domain.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Lenovo
* @description 针对表【chat_message(AI 会话消息表)】的数据库操作Mapper
* @createDate 2026-09-20 15:39:00
* @Entity com.yj.yuaiagent.domain.ChatMessage
*/
@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {

}




