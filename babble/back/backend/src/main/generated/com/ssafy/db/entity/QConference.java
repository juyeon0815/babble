package com.babble.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConference is a Querydsl query type for Conference
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConference extends EntityPathBase<Conference> {

    private static final long serialVersionUID = -625543586L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConference conference = new QConference("conference");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final DatePath<java.sql.Date> callEndTime = createDate("callEndTime", java.sql.Date.class);

    public final DatePath<java.sql.Date> callStartTime = createDate("callStartTime", java.sql.Date.class);

    public final QConferenceCategory conferenceCategory;

    public final ListPath<UserConference, QUserConference> conferenceHistory = this.<UserConference, QUserConference>createList("conferenceHistory", UserConference.class, QUserConference.class, PathInits.DIRECT2);

    public final StringPath description = createString("description");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isActive = createBoolean("isActive");

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public final QUser user;

    public final ListPath<UserConference, QUserConference> userConference = this.<UserConference, QUserConference>createList("userConference", UserConference.class, QUserConference.class, PathInits.DIRECT2);

    public QConference(String variable) {
        this(Conference.class, forVariable(variable), INITS);
    }

    public QConference(Path<? extends Conference> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConference(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConference(PathMetadata metadata, PathInits inits) {
        this(Conference.class, metadata, inits);
    }

    public QConference(Class<? extends Conference> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.conferenceCategory = inits.isInitialized("conferenceCategory") ? new QConferenceCategory(forProperty("conferenceCategory")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

