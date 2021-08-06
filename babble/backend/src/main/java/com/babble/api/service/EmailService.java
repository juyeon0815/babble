package com.babble.api.service;

public interface EmailService {
    String sendSimpleMessage(String to)throws Exception;
    String sendHashtagMessage(String to, String hashtag, Long roomId) throws Exception;
}
