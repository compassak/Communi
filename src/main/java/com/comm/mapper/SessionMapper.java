package com.comm.mapper;

import com.comm.entity.Session;

import java.util.List;

public interface SessionMapper {
    /**
     * 保存一个会话
     * @param session 会话对象
     */
    void insertSession(Session session);

    /**
     * 查询一个用户的所有会话
     * @param userId 用户id
     */
    List<Session> selectUserSession(Integer userId);

    /**
     * 更新一个会话
     * @param session 新会话对象
     */
    void updateSession(Session session);

    /**
     * 删除一个会话
     * @param session 会话对象
     */
    void deleteSession(Session session);
}
