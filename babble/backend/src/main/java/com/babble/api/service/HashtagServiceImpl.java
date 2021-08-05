package com.babble.api.service;

import com.babble.db.entity.Hashtag;
import com.babble.db.repository.HashtagRepository;
import com.babble.db.repository.HashtagRepositorySupport;
import com.babble.db.repository.UserHashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *	해시태그 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("hashtagService")
public class HashtagServiceImpl implements HashtagService {

    @Autowired
    HashtagRepository hashtagRepository;

    @Autowired
    HashtagRepositorySupport hashtagRepositorySupport;

    @Override
    public Hashtag createHashtag(String name) {
        Hashtag hashtag = new Hashtag();
        hashtag.setName(name);
        hashtagRepository.save(hashtag);

        return hashtag;
    }

    @Override
    public Hashtag getHashtagByHashtagName(String name) {
        Hashtag hashtag = hashtagRepositorySupport.findHashtagByHashtagName(name);
        return hashtag;
    }
}
