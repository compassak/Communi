package com.comm.mapper;

import com.comm.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {
    /**
     * 插入一条消息
     * @param message 消息
     */
    void insertMessage(Message message);

    /**
     * 查询用户间的历史聊天记录
     * @param userId 用户1
     * @param chatUserId 用户2
     * @return 历史消息记录
     */
    List<Message> selectMsgEx(@Param("userId") Integer userId, @Param("chatUserId") Integer chatUserId);

    /**
     * 查询用户间的未读聊天记录
     * @param userId 发送消息的用户
     * @param chatUserId 接收消息的用户
     * @return 历史消息记录
     */
    List<Message> selectUnreadMsgEx(@Param("userId") Integer userId, @Param("chatUserId") Integer chatUserId);

    /**
     * 更新两位用户间未读消息为读过的消息
     * @param userId 发送消息的用户
     * @param chatUserId 接收消息的用户
     */
    void updateReadFlag(@Param("userId") Integer userId, @Param("chatUserId") Integer chatUserId);

    /**
     * 删除一条消息
     * @param message 消息
     */
    void deleteMessage(Message message);
}
