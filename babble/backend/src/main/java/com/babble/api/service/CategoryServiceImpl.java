package com.babble.api.service;

import com.babble.db.entity.Category;
import com.babble.db.repository.CategoryRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *	화상회의 방 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepositorySupport categoryRepositorySupport;

    @Override
    public Category getCategoryByCategoryName(String name) {
        Category category = categoryRepositorySupport.findCategoryByCategoryName(name);
        return category;
    }
}
