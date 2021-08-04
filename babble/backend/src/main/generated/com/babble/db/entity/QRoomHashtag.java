package com.babble.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomHashtag is a Querydsl query type for RoomHashtag
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoomHashtag extends EntityPathBase<RoomHashtag> {

    private static final long serialVersionUID = 1675864717L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomHashtag roomHashtag = new QRoomHashtag("roomHashtag");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QHashtag hashtag;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QRoom room;

    public QRoomHashtag(String variable) {
        this(RoomHashtag.class, forVariable(variable), INITS);
    }

    public QRoomHashtag(Path<? extends RoomHashtag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomHashtag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomHashtag(PathMetadata metadata, PathInits inits) {
        this(RoomHashtag.class, metadata, inits);
    }

    public QRoomHashtag(Class<? extends RoomHashtag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hashtag = inits.isInitialized("hashtag") ? new QHashtag(forProperty("hashtag")) : null;
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
    }

}

