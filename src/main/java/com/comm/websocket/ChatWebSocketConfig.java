package com.comm.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class ChatWebSocketConfig implements WebSocketConfigurer {

    /**
     * 注册 WebSocketHandler 被访问的 URL。
     * @param registry 该对象可以调用addHandler()来注册信息处理器。
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(),"/websocket/server")
                .addInterceptors(webSocketHandshakeInterceptor());

        registry.addHandler(webSocketHandler(), "/sockjs/server")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Bean
    public ChatWebSocketHandler webSocketHandler(){
        return new ChatWebSocketHandler();
    }

    @Bean
    public WebSocketHandshakeInterceptor webSocketHandshakeInterceptor(){
        return new WebSocketHandshakeInterceptor();
    }
}
