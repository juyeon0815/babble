package com.babble.api.controller;

import com.babble.api.request.room.MessageReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageController {
    private final SimpMessagingTemplate template;

    @MessageMapping("/message")
    public void sendMessage(@Payload MessageReq message) {
        log.info("전달 메세지 : " + message);
        // 내가 보내주고자 한 사람이 subscribe한 링크로 보내준다
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> " + message);
        template.convertAndSend("/sub/" + message.getChatroomId(), message);
    }
}