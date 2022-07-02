package swm.spring.jpa.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import swm.spring.jpa.entity.CachedMember;
import swm.spring.jpa.repository.CachedMemberRepository;

import java.util.Optional;

@SpringBootTest
@Transactional
public class CacheJpaTest {

    @Autowired
    private CachedMemberRepository cachedMemberRepository;

    @Test
    void persistentContextCachedTest() {
        System.out.println("영속성 컨텍스트 : 쿼리 실행");
        Optional<CachedMember> cachedMember1 = cachedMemberRepository.findById(1L);

        System.out.println("영속성 컨텍스트 : 위에서 쿼리 실행하여 이번엔 쿼리 실행 안함");
        Optional<CachedMember> cachedMember2 = cachedMemberRepository.findById(1L);
    }

    @Test
    void entityCachedTest() {
        System.out.println("영속성 컨텍스트 테스트에서 실행 이후 어플리케이션 캐시로 저장되어 쿼리 실행 안함1");
        Optional<CachedMember> cachedMember1 = cachedMemberRepository.findById(1L);

        System.out.println("영속성 컨텍스트 테스트에서 실행 이후 어플리케이션 캐시로 저장되어 쿼리 실행 안함2");
        Optional<CachedMember> cachedMember2 = cachedMemberRepository.findById(1L);
    }
}
