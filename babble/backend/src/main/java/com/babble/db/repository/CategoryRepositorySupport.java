package com.babble.db.repository;

import com.babble.db.entity.Category;
import com.babble.db.entity.QCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 화상회의 방 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository

public class CategoryRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QCategory qCategory = QCategory.category;

    public Category findCategoryByCategoryName(String name){
        Category category = jpaQueryFactory.select(qCategory).from(qCategory)
                .where(qCategory.name.eq(name)).fetchOne();
        if(category==null) return null;
        return  category;
    }
}
