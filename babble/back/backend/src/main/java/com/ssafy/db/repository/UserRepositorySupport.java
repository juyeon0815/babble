package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.request.UserUpdatePatchReq;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class UserRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;

    public Optional<User> findUserByUserId(String userId) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.userId.eq(userId)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }

    public boolean checkId(String userId) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.userId.eq(userId)).fetchOne();
        if (user==null) return false;
        return true;
    }

    @Transactional
    public void deleteUser(String userId) {
        jpaQueryFactory.delete(qUser).where(qUser.userId.eq(userId)).execute();
    }

    @Transactional
    public void updateUser(String userId, UserUpdatePatchReq userUpdateInfo) {
        jpaQueryFactory.update(qUser).set(qUser.department, userUpdateInfo.getPosition())
                .set(qUser.position, userUpdateInfo.getPosition())
                .set(qUser.name, userUpdateInfo.getName())
                .where(qUser.userId.eq(userId)).execute();
    }
}
