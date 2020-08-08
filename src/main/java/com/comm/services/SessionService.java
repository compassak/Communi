package com.comm.services;

import com.comm.entity.Session;
import com.comm.mapper.SessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {
    private SessionMapper sessionMapper;

    /**
     * 构造函数注入
     */
    @Autowired
    public SessionService(SessionMapper sessionMapper){
        this.sessionMapper = sessionMapper;
    }

    /**
     * 保存一个会话
     * @param session 会话对象
     */
    public void insertSession(Session session){
        sessionMapper.insertSession(session);
    }

    /**
     * 查询一个用户的所有会话
     * @param userId 用户id
     */
    public List<Session> selectUserSession(Integer userId){
        return sessionMapper.selectUserSession(userId);
    }

    /**
     * 更新一个会话
     * @param session 新会话对象
     */
    public void updateSession(Session session){
        sessionMapper.updateSession(session);
    }

    /**
     * 删除一个会话
     * @param session 会话对象
     */
    public void deleteSession(Session session){
        sessionMapper.deleteSession(session);
    }

    /**
     * 更新或插入一个会话
     * @param session 新会话对象
     */
    public void updateOrInsertSession(Session session){
        sessionMapper.deleteSession(session);
        sessionMapper.insertSession(session);
    }
}
