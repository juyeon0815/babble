package com.babble.api.service;

import com.babble.db.entity.Hashtag;

/**
 *	해시태그 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface HashtagService {
    Hashtag createHashtag(String name);
    Hashtag getHashtagByHashtagName(String name);
}
