package com.comm.websocket;

import com.comm.entity.Message;
import com.comm.entity.Session;
import com.comm.services.MessageService;
import com.comm.services.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    //存放在线用户的id和与其对应的 WebSocketSession
    private static final Map<Integer, WebSocketSession> users =  new HashMap<>();
    //用于类型转换json字符串与json对象
    private ObjectMapper mapper = new ObjectMapper();
    //消息服务类
    @Autowired
    private MessageService messageService;
    //会话服务类
    @Autowired
    private SessionService sessionService;

    /**
     * 成功连接时执行，并且触发 WebSocket 客户端的 onopen()方法
     * @param session 当前连接用户的 WebSocketSession
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Integer userId = (Integer) session.getAttributes().get("userId");
        System.out.println(userId);
        users.put(userId, session);
        System.out.println("在线人数：" + users.size());
    }

    /**
     * 收到消息时(客户端调用 send() 函数)触发，我们将消息转发给其想发送到的用户
     * @param session 当前发送消息的用户的 WebSocketSession
     * @param message 当前发送的消息
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        //获得消息内容
        String msgStr = message.getPayload().toString();
        /*
          为了保持连接活性，有一些客户端可能会定时的发一些空消息，而空消息经 getPayload().toString() 操作后
          结果统一为：java.nio.HeapByteBuffer[pos=0 lim=0 cap=0]，下面的判断就过滤了空消息
         */
        if(msgStr.contains(":")){
            //解析消息对象
            Message msg = mapper.readValue(msgStr, Message.class);
            //发送消息
            sendMessageToUser(msg.getChatUserId(), message, msg);
        }
    }

    /**
     * 关闭连接时执行
     * @param session 当前关闭连接用户的 WebSocketSession
     * @param status 关闭返回状态
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println(status);
        Integer userId = (Integer) session.getAttributes().get("userId");
        users.remove(userId, session);
        System.out.println("用户 " + userId + " 退出");
    }

    /**
     * 给某个用户发送消息
     *  @param userId 用户id
     *  @param message 消息
     */
    private void sendMessageToUser(Integer userId, WebSocketMessage<?> message, Message msg) throws IOException {
        if(users.containsKey(userId)){
            if(users.get(userId).isOpen()) {
                users.get(userId).sendMessage(message);
            }
        }
        //插入消息
        messageService.insertMessage(msg);
        //插入或更新会话
        sessionService.updateOrInsertSession(new Session(msg.getUserId(), msg.getChatUserId(), msg.getTime()));
    }
}
