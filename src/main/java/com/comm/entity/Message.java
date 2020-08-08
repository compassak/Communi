package com.comm.entity;

import java.util.Date;

/**
 * Message 用于存放聊天消息
 */
public class Message {
    //消息发送的用户的id
    private int userId;
    //接收消息的的用户的id
    private int chatUserId;
    //消息内容
    private String text;
    //消息类型
    private String type;
    //已阅读标记
    private boolean flag;
    //产生时间
    private Date time;

    public Message(){}
    public Message(int userId, int chatUserId, String text, String type, Boolean flag, Date time) {
        this.userId = userId;
        this.chatUserId = chatUserId;
        this.text = text;
        this.type = type;
        this.flag = flag;
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getChatUserId() {
        return chatUserId;
    }

    public void setChatUserId(int chatUserId) {
        this.chatUserId = chatUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "userId=" + userId +
                ", chatUserId=" + chatUserId +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", flag=" + flag +
                ", time=" + time +
                '}';
    }
}
