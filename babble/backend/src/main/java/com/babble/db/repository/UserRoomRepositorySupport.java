package com.babble.db.repository;


import com.babble.db.entity.QUserRoom;
import com.babble.db.entity.User;
import com.babble.db.entity.UserRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 유저_룸 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class UserRoomRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUserRoom qUserRoom =QUserRoom.userRoom;

    public UserRoom findUserRoomByUserId(User user){
        UserRoom userRoom = jpaQueryFactory.select(qUserRoom).from(qUserRoom)
                .where(qUserRoom.user.id.eq(user.getId())).fetchOne();

        return userRoom;
    }

    public List<UserRoom> findUserRoomByRoomId(Long roomId){
        List<UserRoom> userRoom = jpaQueryFactory.select(qUserRoom).from(qUserRoom)
                .where(qUserRoom.room.id.eq(roomId)).fetch();

        return userRoom;
    }
}
