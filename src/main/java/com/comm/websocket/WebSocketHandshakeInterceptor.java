package com.comm.websocket;

import com.user.entity.User;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;


public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    /**
     * 将HttpSession 中 user 的 id 存入 WebSocketSession 的 Attributes中。
     * @param map WebSocketSession 的 Attributes
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if(serverHttpRequest instanceof ServletServerHttpRequest){
            ServletServerHttpRequest request = (ServletServerHttpRequest)serverHttpRequest;
            HttpSession session = request.getServletRequest().getSession(false);
            if(session != null){
                User user = (User) session.getAttribute("user");
                map.put("userId", user.getId());
            }
        }
        return super.beforeHandshake(serverHttpRequest, serverHttpResponse, webSocketHandler, map);
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        super.afterHandshake(serverHttpRequest, serverHttpResponse, webSocketHandler, e);
    }
}
