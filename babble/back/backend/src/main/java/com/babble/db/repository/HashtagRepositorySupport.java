package com.babble.db.repository;

import com.babble.db.entity.Hashtag;
import com.babble.db.entity.QHashtag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class HashtagRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QHashtag qHashtag = QHashtag.hashtag;

    public Hashtag findHashtagByHashtagName(String hashtag){
        Hashtag tag = jpaQueryFactory.select(qHashtag).from(qHashtag)
                .where(qHashtag.name.eq(hashtag)).fetchOne();

        if(tag==null) return null;
        return tag;

    }
}
