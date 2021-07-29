package com.babble.api.service;

import com.babble.db.entity.Hashtag;
import com.babble.db.entity.User;
import com.babble.db.entity.UserHashtag;
import com.babble.db.repository.UserHashtagRepository;
import com.babble.db.repository.UserHashtagRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *	유저해시태그 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userHashtagService")
public class UserHashtagServiceImpl implements UserHashtagService {

    @Autowired
    UserHashtagRepository userHashtagRepository;

    @Autowired
    UserHashtagRepositorySupport userHashtagRepositorySupport;

    @Override
    public UserHashtag createUserHashtag(User user, Hashtag hashtag) {
        UserHashtag userHashtag = new UserHashtag();
        userHashtag.setUser(user);
        userHashtag.setHashtag(hashtag);
        userHashtagRepository.save(userHashtag);

        return userHashtag;
    }

    @Override
    public void deleteHashtag(User user, Hashtag hashtag) {
        userHashtagRepositorySupport.deleteUserHashtag(user,hashtag);
    }

    @Override
    public List<String> getUserHashtag(Long userId) {
        List<String> list = userHashtagRepositorySupport.getUserHashtag(userId);
        return list;
    }
}
