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
//    JwtTokenUtil jwtTokenUtil;

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
//            if(jwt!=null) {
//                // 토큰 유효성 검사를 해서, 토큰이 있으면 Member, 없으면 Guest Role 부여
//                // 전달받은 토큰이 제대로 생성된 것인지 확인
//                boolean valid = JwtTokenUtil.validateToken(jwt);
//                System.out.println(">>> 사용 가능한 토큰??? " + valid);
//                System.out.println(JwtTokenUtil.getUserInfo(jwt));
//                // 토큰이 유효하지 않으면 예외 처리 되어서 메시지 수신 되지 않는다.
//            }
        } else if (command.compareTo(StompCommand.DISCONNECT) == 0) {
            System.out.println("사용자 연결 해제");
        }
        return message;
    }
}
