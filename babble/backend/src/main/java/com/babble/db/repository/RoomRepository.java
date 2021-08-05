package com.babble.db.repository;


import com.babble.db.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 화상회의 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
public interface RoomRepository extends JpaRepository<Room, Long> {
}
