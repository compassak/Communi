package com.comm.entity;

import java.util.Date;

/**
 * 保存会话消息
 */
public class Session {
    //用户1的id
    private int userId1;
    //用户2的id
    private int userId2;
    //会话时间
    private Date time;

    public Session(){}
    public Session(int userId1, int userId2, Date time) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.time = time;
    }

    public int getUserId1() {
        return userId1;
    }

    public void setUserId1(int userId1) {
        this.userId1 = userId1;
    }

    public int getUserId2() {
        return userId2;
    }

    public void setUserId2(int userId2) {
        this.userId2 = userId2;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Session{" +
                "userId1=" + userId1 +
                ", userId2=" + userId2 +
                ", time=" + time +
                '}';
    }
}
