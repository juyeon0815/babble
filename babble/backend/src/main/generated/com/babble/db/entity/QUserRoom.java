package com.babble.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserRoom is a Querydsl query type for UserRoom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserRoom extends EntityPathBase<UserRoom> {

    private static final long serialVersionUID = 994534570L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserRoom userRoom = new QUserRoom("userRoom");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QRoom room;

    public final QUser user;

    public QUserRoom(String variable) {
        this(UserRoom.class, forVariable(variable), INITS);
    }

    public QUserRoom(Path<? extends UserRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserRoom(PathMetadata metadata, PathInits inits) {
        this(UserRoom.class, metadata, inits);
    }

    public QUserRoom(Class<? extends UserRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

