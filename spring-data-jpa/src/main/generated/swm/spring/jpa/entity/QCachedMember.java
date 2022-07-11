package swm.spring.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCachedMember is a Querydsl query type for CachedMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCachedMember extends EntityPathBase<CachedMember> {

    private static final long serialVersionUID = 1622868792L;

    public static final QCachedMember cachedMember = new QCachedMember("cachedMember");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QCachedMember(String variable) {
        super(CachedMember.class, forVariable(variable));
    }

    public QCachedMember(Path<? extends CachedMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCachedMember(PathMetadata metadata) {
        super(CachedMember.class, metadata);
    }

}

