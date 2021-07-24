package com.babble.db.repository;


import com.babble.db.entity.Hashtag;
import com.babble.db.entity.QUserHashtag;
import com.babble.db.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserHashtagRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUserHashtag qUserHashtag = QUserHashtag.userHashtag;

    public void deleteUserHashtag(User user, Hashtag hashtag){
        jpaQueryFactory.delete(qUserHashtag).where(qUserHashtag.user.eq(user))
                .where(qUserHashtag.hashtag.eq(hashtag)).execute();
    }
}
