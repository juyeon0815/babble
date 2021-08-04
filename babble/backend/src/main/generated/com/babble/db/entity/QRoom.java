package com.babble.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoom is a Querydsl query type for Room
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoom extends EntityPathBase<Room> {

    private static final long serialVersionUID = 410006591L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoom room = new QRoom("room");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCategory category;

    public final StringPath content = createString("content");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Long> hostId = createNumber("hostId", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isActivate = createBoolean("isActivate");

    public final NumberPath<Long> maxView = createNumber("maxView", Long.class);

    public final ListPath<RoomHashtag, QRoomHashtag> roomHashtag = this.<RoomHashtag, QRoomHashtag>createList("roomHashtag", RoomHashtag.class, QRoomHashtag.class, PathInits.DIRECT2);

    public final ListPath<RoomHistory, QRoomHistory> roomHistories = this.<RoomHistory, QRoomHistory>createList("roomHistories", RoomHistory.class, QRoomHistory.class, PathInits.DIRECT2);

    public final BooleanPath speak = createBoolean("speak");

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public QRoom(String variable) {
        this(Room.class, forVariable(variable), INITS);
    }

    public QRoom(Path<? extends Room> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoom(PathMetadata metadata, PathInits inits) {
        this(Room.class, metadata, inits);
    }

    public QRoom(Class<? extends Room> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
    }

}

