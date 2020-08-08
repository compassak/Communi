package com.comm.services;

import com.comm.entity.Message;
import com.comm.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MessageService {

    private MessageMapper messageMapper;

    /**
     * 构造函数注入
     */
    @Autowired
    public MessageService(MessageMapper messageMapper){
        this.messageMapper = messageMapper;
    }

    /**
     * 插入一条消息
     * @param message 消息
     */
    public void insertMessage(Message message){
        messageMapper.insertMessage(message);
    }

    /**
     * 查询用户间的历史聊天记录
     * @param userId 用户1
     * @param chatUserId 用户2
     * @return 历史消息记录
     */
    public List<Message> selectMsgEx(Integer userId, Integer chatUserId){
        return messageMapper.selectMsgEx(userId, chatUserId);
    }

    /**
     * 查询用户间的未读聊天记录
     * @param userId 发送消息的用户
     * @param chatUserId 接收消息的用户
     * @return 历史消息记录
     */
    public List<Message> selectUnreadMsgEx(Integer userId, Integer chatUserId){
        return messageMapper.selectUnreadMsgEx(userId, chatUserId);
    }

    /**
     * 更新两位用户间未读消息为读过的消息
     * @param userId 发送消息的用户
     * @param chatUserId 接收消息的用户
     */
    public void updateReadFlag(Integer userId, Integer chatUserId){
        messageMapper.updateReadFlag(userId, chatUserId);
    }

    /**
     * 删除一条消息
     * @param message 消息
     */
    public void deleteMessage(Message message){
        messageMapper.deleteMessage(message);
    }

}
