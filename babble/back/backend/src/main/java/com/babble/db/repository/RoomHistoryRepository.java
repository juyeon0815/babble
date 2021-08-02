package com.babble.db.repository;

import com.babble.db.entity.RoomHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 시청내역  관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
public interface RoomHistoryRepository extends JpaRepository<RoomHistory, Long> {
}
