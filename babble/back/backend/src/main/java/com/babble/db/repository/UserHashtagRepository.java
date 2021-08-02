package com.babble.db.repository;

import com.babble.db.entity.UserHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHashtagRepository extends JpaRepository<UserHashtag, Long> {
}
