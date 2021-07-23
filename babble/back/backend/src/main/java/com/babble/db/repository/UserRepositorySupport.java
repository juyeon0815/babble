package com.babble.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.babble.api.request.UserUpdatePatchReq;
import com.babble.db.entity.QUser;
import com.babble.db.entity.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class UserRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;

    public Optional<User> findUserByUserEmail(String email) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.email.eq(email)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }

    public boolean checkEmail(String email) {
    	User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.email.eq(email)).fetchOne();

    	if(user==null) return false;
    	return true;
    }
//
//    public void deleteUser(String userId) {
//    	//jpaQueryFactory.delete(qUser).where(qUser.userId.eq(userId)).execute();
//    }
//
//    public void updateUser(UserUpdatePatchReq userUpdateInfo) {
//    	jpaQueryFactory.update(qUser).set(qUser.department, userUpdateInfo.getPosition())
//    	.set(qUser.position, userUpdateInfo.getPosition())
//    	.set(qUser.name, userUpdateInfo.getName())
//    	.where(qUser.userId.eq(userUpdateInfo.getId())).execute();
//    }
}
