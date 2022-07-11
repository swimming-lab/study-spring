package swm.spring.jpa.study.repostiroy_custom;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class SimpleMyJpsRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements MyJpaRepository<T, ID> {

    private EntityManager entityManager;

    public SimpleMyJpsRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    /**
     * 커스텀 메소드 추가
     * @param entity
     * @return
     */
    @Override
    public boolean contains(Object entity) {
        return entityManager.contains(entity);
    }

    /**
     * findAll를 기본보다 더 빠르게 구현할 수 있으면 이렇게 오버라이딩하면 된다.
     * @return
     */
    @Override
    public List<T> findAll() {
        return super.findAll();
    }
}
