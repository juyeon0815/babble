package com.babble.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoomHistory is a Querydsl query type for RoomHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoomHistory extends EntityPathBase<RoomHistory> {

    private static final long serialVersionUID = 1905251157L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomHistory roomHistory = new QRoomHistory("roomHistory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final DateTimePath<java.util.Date> endTime = createDateTime("endTime", java.util.Date.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QRoom room;

    public final DateTimePath<java.util.Date> startTime = createDateTime("startTime", java.util.Date.class);

    public final QUser user;

    public QRoomHistory(String variable) {
        this(RoomHistory.class, forVariable(variable), INITS);
    }

    public QRoomHistory(Path<? extends RoomHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomHistory(PathMetadata metadata, PathInits inits) {
        this(RoomHistory.class, metadata, inits);
    }

    public QRoomHistory(Class<? extends RoomHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

