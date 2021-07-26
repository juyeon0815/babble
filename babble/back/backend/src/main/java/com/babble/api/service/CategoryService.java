package com.babble.api.service;

import com.babble.db.entity.Category;

/**
 *	화상회의 방 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */

public interface CategoryService {
    Category getCategoryByCategoryName(String name);
}
