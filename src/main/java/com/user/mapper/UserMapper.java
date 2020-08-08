package com.user.mapper;

import org.apache.ibatis.annotations.Param;
import com.user.entity.User;

import java.util.List;


public interface UserMapper {
	//查询用户名
	 String findUserName(String userName);
	//查询邮箱
	 String findMail(String mailAddress);
    //根据用户名和密码查找。mybatis中有多个参数时，需要使用@Param注解
     User findByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);
     //增加用户
     void addUser(User user);
     //根据Id查询User
    User findByUserId(Integer id);
     //根据用户名查询
     User findByUserName(String userName);
     //根据用户名模糊查询一组
     List<User> findByUsersName(String userName);
     //根据激活码查询
     User findByUserCode(String code);
     //根据激活码修改激活状态
     void updateStateByCode(String code);
    //根据用户名修改密码
     void updatePassword(@Param("userName") String userName, @Param("password") String password);
     //查询用户信息
     User findUserInfo();   
}