package com.comm.controller;

import com.user.entity.User;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Scope("prototype")
public class SearchController {

    private UserService userService;

    //构造器自动注入userService
    @Autowired
    public SearchController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping("search")
    public String Search(HttpSession session, HttpServletRequest request, String username){
        if(session.getAttribute("user") == null)
            return "user/login";
        else{
            List<User> users = userService.findByUsersName(username);
            request.setAttribute("users", users);
        }
        return "index";
    }
}
