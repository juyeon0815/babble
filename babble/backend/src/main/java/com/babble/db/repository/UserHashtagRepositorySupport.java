package com.babble.db.repository;


import com.babble.db.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserHashtagRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUserHashtag qUserHashtag = QUserHashtag.userHashtag;
    QHashtag qHashtag =QHashtag.hashtag;
    QUser qUser = QUser.user;

    public void deleteUserHashtag(User user, Hashtag hashtag){
        jpaQueryFactory.delete(qUserHashtag).where(qUserHashtag.user.eq(user))
                .where(qUserHashtag.hashtag.eq(hashtag)).execute();
    }

    public List<String> getUserHashtag(Long userId){
        List<String> list = jpaQueryFactory.select(qHashtag.name)
                .from(qUser).leftJoin(qUserHashtag).on(qUser.id.eq(qUserHashtag.user.id))
                .leftJoin(qHashtag).on(qUserHashtag.hashtag.id.eq(qHashtag.id))
                .where(qUser.id.eq(userId))
                .fetch();

        return list;
    }

    public void deleteUserHashtag(User user){
        jpaQueryFactory.delete(qUserHashtag)
                .where(qUserHashtag.user.eq(user)).execute();
    }

    public List<String> getUserByHashtag(String hashtag) {
        List<String> list = jpaQueryFactory.select(qUser.email)
                .from(qHashtag)
                .leftJoin(qUserHashtag)
                .on(qHashtag.id.eq(qUserHashtag.hashtag.id))
                .leftJoin(qUser)
                .on(qUserHashtag.user.id.eq(qUser.id))
                .where(qHashtag.name.eq(hashtag))
                .where(qUser.alarm.eq(true))
                .fetch();
        return list;
    }
}
