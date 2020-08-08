package com.comm.controller;

import com.comm.entity.Message;
import com.comm.entity.Session;
import com.comm.services.MessageService;
import com.comm.services.SessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.entity.User;
import com.user.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequestMapping("/comm")
public class ChatController {
    private UserService userService;
    private SessionService sessionService;
    private MessageService messageService;

    //构造器自动注入userService
    @Autowired
    ChatController(UserService userService, SessionService sessionService,MessageService messageService){
        this.userService = userService;
        this.sessionService = sessionService;
        this.messageService = messageService;
    }

    /**
     * 查询当前用户最近会话，以及会话的未读消息，配合chat.jsp生成用户的聊天界面
     * @param session 当前用户的 HttpSession
     * @param request 当前请求的 HttpServletRequest
     * @return chat.jsp 聊天页面
     */
    @RequestMapping("/chat")
    public String chat(HttpSession session, HttpServletRequest request){
        if (selectSessions(request, session)) return "user/login";
        return "comm/chat";
    }

    /**
     * 查询当前用户最近会话,会话的未读消息,打开与指定用户聊天的窗口,配合chat.jsp生成用户的聊天界面
     * @param session 当前用户的 HttpSession
     * @param request 当前请求的 HttpServletRequest
     * @return chat.jsp 聊天页面
     */
    @RequestMapping("/userChat")
    public String userChat(HttpServletRequest request, HttpSession session,  @Param("id") int id){
        if (selectSessions(request, session)) return "user/login";
        request.setAttribute("chatUser",userService.findByUserId(id));
        return "comm/chat";
    }

    /**
     * 查询用户最近会话，以及会话未读消息存入 HttpServletRequest
     * @param request 当前请求的 HttpServletRequest
     * @param session 当前用户的 HttpSession
     * @return 用户是否登陆
     */
    private boolean selectSessions(HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null)
            return true;
        else{
            List<Session> sessions = sessionService.selectUserSession(user.getId());
            LinkedHashMap<User,Integer> userMsg = new LinkedHashMap<>();
            //未读消息数
            int unReadNum;
            for (Session ses : sessions){
                if(ses.getUserId1() != user.getId()){
                    unReadNum = messageService.selectUnreadMsgEx(ses.getUserId1(), user.getId()).size();
                    User user1 = userService.findByUserId(ses.getUserId1());
                    userMsg.put(user1, unReadNum);
                }else {
                    unReadNum = messageService.selectUnreadMsgEx(ses.getUserId2(), user.getId()).size();
                    User user1 = userService.findByUserId(ses.getUserId2());
                    userMsg.put(user1, unReadNum);
                }
            }
            request.setAttribute("sessions", sessions);
            request.setAttribute("userMsg", userMsg);
        }
        return false;
    }

    /**
     * 打开一个会话，清空未读消息
     */
    @RequestMapping(value = "/openSession", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String openSession(HttpSession session, @Param("id") int id) throws JsonProcessingException {
        User user = (User) session.getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        //查询未读消息
        List<Message> messages = messageService.selectUnreadMsgEx(id, user.getId());
        //更新消息状态
        messageService.updateReadFlag(id, user.getId());
        //转化成json串返回
        return mapper.writeValueAsString(messages);
    }

    /**
     * 查询两个用户的历史聊天记录
     */
    @RequestMapping(value = "/queryMsgEx", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryMsgEx(HttpSession session, @Param("id") int id) throws JsonProcessingException {
        User user = (User) session.getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        //查询未读消息
        List<Message> messages = messageService.selectMsgEx(id, user.getId());
        //更新消息状态
        messageService.updateReadFlag(id, user.getId());
        //转化成json串返回
        return mapper.writeValueAsString(messages);
    }

    /**
     * 更新消息状态
     * @param userId 发送消息的用户的id
     * @param chatUserId 接收消息的用户的id
     */
    @RequestMapping(value = "/updateMessageFlag", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String updateMessageFlag(@Param("userId") int userId, @Param("chatUserId") int chatUserId){
        //更新消息状态
        messageService.updateReadFlag(userId, chatUserId);
        return "";
    }

    /**
     * 查询一个用户
     * @param userId 用户id
     * @return 用户json对象
     * @throws JsonProcessingException json对象转换错误
     */
    @RequestMapping(value = "/queryOneUser", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String queryOneUser(@Param("userId") int userId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //更新消息状态
        User user = userService.findByUserId(userId);
        return mapper.writeValueAsString(user);
    }


    /**
     * 保存用户聊天中的图片
     * @param file 图片文件
     * @param userId 发送图片的用户的id
     * @param time 发送时刻的时间戳字符串
     */
    @RequestMapping("/upLoadImage")
    public void upLoadImage(@RequestParam("file") CommonsMultipartFile file, @Param("userId") int userId, @Param("time")String time) throws IOException {
        String path = "D:/images/chat/MsgEx/";
        //生成文件名
        String filename = path + userId +"/"+ time + "." +file.getOriginalFilename().split("\\.")[1];

        File imageFile = new File(filename);
        //如果父目录不存在，就马上创建一个父目录
        if (!imageFile.exists()) {
            imageFile.getParentFile().mkdir();
        }
        file.transferTo(imageFile);
    }
}
