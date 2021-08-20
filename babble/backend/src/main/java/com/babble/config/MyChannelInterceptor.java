package com.babble.config;

import com.babble.common.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class MyChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        if (command.compareTo(StompCommand.SUBSCRIBE) == 0) {
            String destination = accessor.getDestination();
            System.out.println("구독 주소 : " + destination);
            System.out.println(message);
        } else if (command.compareTo(StompCommand.CONNECT) == 0) {
            System.out.println("사용자 연결");
            System.out.println(">>> accessor " + accessor);
            String jwt = accessor.getFirstNativeHeader("authorization");
            System.out.println("jwt " + jwt);
        } else if (command.compareTo(StompCommand.DISCONNECT) == 0) {
            System.out.println("사용자 연결 해제");
        }
        return message;
    }
}
