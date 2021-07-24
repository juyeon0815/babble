package com.babble.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserHashtag is a Querydsl query type for UserHashtag
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserHashtag extends EntityPathBase<UserHashtag> {

    private static final long serialVersionUID = 812716445L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserHashtag userHashtag = new QUserHashtag("userHashtag");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QHashtag hashtag;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QUser user;

    public QUserHashtag(String variable) {
        this(UserHashtag.class, forVariable(variable), INITS);
    }

    public QUserHashtag(Path<? extends UserHashtag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserHashtag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserHashtag(PathMetadata metadata, PathInits inits) {
        this(UserHashtag.class, metadata, inits);
    }

    public QUserHashtag(Class<? extends UserHashtag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hashtag = inits.isInitialized("hashtag") ? new QHashtag(forProperty("hashtag")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

