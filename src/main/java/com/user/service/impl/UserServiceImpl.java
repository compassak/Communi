package com.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.user.entity.User;
import com.user.mapper.UserMapper;
import com.user.service.UserService;

import java.util.List;

/**
 * UserServiceImpl 实现接口服务 UserService
 * 并实现里面的方法
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{
     //注入UserMapper接口
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 查询用户名
     */
    @Override
    public String findUserName(String userName) {
        return userMapper.findUserName(userName);
    }
    /**
     * 查询邮箱
     */
    @Override
    public String findMail(String mailAddress) {
        return userMapper.findMail(mailAddress);
    }
    /**
     * 登录
     * 根据用户名和密码进行查询
     */
    @Override
    public User login(String userName, String password) {
        return userMapper.findByUserNameAndPassword(userName, password);
    }
    /**
     * 注册
     * 增加用户
     */
    @Override
    public void register(User user) {
        userMapper.addUser(user);    
    }

    /**
     * 根据Id查询User
     */
    @Override
    public User findByUserId(Integer id) {
        return userMapper.findByUserId(id);
    }

    /**
     * 根据用户名查询
     */
    @Override
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    /**
     * 根据用户名模糊查询一组用户
     */
    @Override
    public List<User> findByUsersName(String userName) {
        return userMapper.findByUsersName(userName);
    }
    
    /**
     * 根据激活码查询
     */
    @Override
    public User findByUserCode(String code) {
        return userMapper.findByUserCode(code);
    }

    /**
     * 根据激活码修改激活状态
     */
    @Override
    public void updateStateByCode(String code) {
    	userMapper.updateStateByCode(code);
    }

    /**
     * 根据用户名修改密码
     */
    @Override
    public void updatePassword(String userName,String password) {
    	userMapper.updatePassword(userName,password);
    }
   //查询用户信息
   public User findUserInfo() {
	   return userMapper.findUserInfo();
   }
  
}
    
 
