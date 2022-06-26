package swm.spring.jpa.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import swm.spring.jpa.entity.Article;
import swm.spring.jpa.entity.Member;
import swm.spring.jpa.repository.ArticleRepository;
import swm.spring.jpa.repository.MemberRepository;

import java.util.List;

@SpringBootTest
@Transactional
public class JpaTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ArticleRepository articleRepository;

    /**
     * N+1 테스트
     */
    @Test
    @DisplayName("N+1 확인")
    void test() {
        System.out.println("---------------------------------------------------");
        List<Member> memberList = memberRepository.findAll();
        for (Member member : memberList) {
            member.getArticles().size();
        }
//        System.out.println(memberList.get(0).getArticles().size());
        System.out.println("---------------------------------------------------");
//        Member member = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
//        System.out.println(member.getName());
        /**
         * Hibernate:
         *     select
         *         member0_.id as id1_1_,
         *         member0_.name as name2_1_
         *     from
         *         member member0_
         * Hibernate:
         *     select
         *         articles0_.member_id as member_i3_0_0_,
         *         articles0_.id as id1_0_0_,
         *         articles0_.id as id1_0_1_,
         *         articles0_.member_id as member_i3_0_1_,
         *         articles0_.title as title2_0_1_
         *     from
         *         article articles0_
         *     where
         *         articles0_.member_id=?
         * Hibernate:
         *     select
         *         articles0_.member_id as member_i3_0_0_,
         *         articles0_.id as id1_0_0_,
         *         articles0_.id as id1_0_1_,
         *         articles0_.member_id as member_i3_0_1_,
         *         articles0_.title as title2_0_1_
         *     from
         *         article articles0_
         *     where
         *         articles0_.member_id=?
         */
    }

    @Test
    @DisplayName("Fetch join으로 N+1 해결")
    void fetchJoinTest() {
        System.out.println("---------------------------------------------------");
        List<Member> memberList = memberRepository.findAllJPQLFetch();
        for (Member member : memberList) {
            member.getArticles().size();
        }
        System.out.println("---------------------------------------------------");
        /**
         *     select
         *         distinct member0_.id as id1_1_0_,
         *         articles1_.id as id1_0_1_,
         *         member0_.name as name2_1_0_,
         *         articles1_.member_id as member_i3_0_1_,
         *         articles1_.title as title2_0_1_,
         *         articles1_.member_id as member_i3_0_0__,
         *         articles1_.id as id1_0_0__
         *     from
         *         member member0_
         *     left outer join
         *         article articles1_
         *             on member0_.id=articles1_.member_id
         */
    }

    @Test
    @DisplayName("Entity Graph로 N+1 해결")
    void entityGraphTest() {
        System.out.println("---------------------------------------------------");
        List<Member> memberList = memberRepository.findAllEntityGraph();
        for (Member member : memberList) {
            member.getArticles().size();
        }
        System.out.println("---------------------------------------------------");
        /**
         *     select
         *         distinct member0_.id as id1_1_0_,
         *         articles1_.id as id1_0_1_,
         *         member0_.name as name2_1_0_,
         *         articles1_.member_id as member_i3_0_1_,
         *         articles1_.title as title2_0_1_,
         *         articles1_.member_id as member_i3_0_0__,
         *         articles1_.id as id1_0_0__
         *     from
         *         member member0_
         *     left outer join
         *         article articles1_
         *             on member0_.id=articles1_.member_id
         */
    }

    @Test
    @DisplayName("Fetch join Paging 문제: limit, offset 을 쿼리에 추가하지 않음")
    void fetchJoinPagingTest() {
        System.out.println("---------------------------------------------------");
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<Member> memberList = memberRepository.findAllJPQLFetch(pageRequest);
        System.out.println(memberList.size());
        for (Member member : memberList) {
            member.getArticles().size();
        }
        System.out.println("---------------------------------------------------");
        /**
         *     select
         *         distinct member0_.id as id1_1_0_,
         *         articles1_.id as id1_0_1_,
         *         member0_.name as name2_1_0_,
         *         articles1_.member_id as member_i3_0_1_,
         *         articles1_.title as title2_0_1_,
         *         articles1_.member_id as member_i3_0_0__,
         *         articles1_.id as id1_0_0__
         *     from
         *         member member0_
         *     left outer join
         *         article articles1_
         *             on member0_.id=articles1_.member_id
         */
    }

    @Test
    @DisplayName("Fetch join Paging 해결: ManyToOne 관계에서는 페이징처리가 됨")
    void fetchJoinPagingSolutionTest() {
        System.out.println("---------------------------------------------------");
        PageRequest pageRequest = PageRequest.of(0, 3);
        List<Article> articles = articleRepository.findAllPage(pageRequest);
        System.out.println(articles.size());
        for (Article article : articles) {
            System.out.println(article.getMember().getName());
        }
        System.out.println("---------------------------------------------------");
        /**
         *     select
         *         article0_.id as id1_0_0_,
         *         member1_.id as id1_1_1_,
         *         article0_.member_id as member_i3_0_0_,
         *         article0_.title as title2_0_0_,
         *         member1_.name as name2_1_1_
         *     from
         *         article article0_
         *     left outer join
         *         member member1_
         *             on article0_.member_id=member1_.id limit ?
         */
    }

    @Test
    @DisplayName("Fetch join Paging 해결: ManyToOne 관계에서는 페이징처리가 됨")
    void batchSizePagingSolutionTest() {
        System.out.println("---------------------------------------------------");
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Member> memberList = memberRepository.findAll(pageRequest);
        System.out.println(memberList.getSize());
        for (Member member : memberList) {
            member.getArticles().size();
        }
        System.out.println("---------------------------------------------------");
        /**
         * Hibernate:
         *     select
         *         member0_.id as id1_1_,
         *         member0_.name as name2_1_
         *     from
         *         member member0_ limit ?
         * 5
         * Hibernate:
         *     select
         *         articles0_.member_id as member_i3_0_1_,
         *         articles0_.id as id1_0_1_,
         *         articles0_.id as id1_0_0_,
         *         articles0_.member_id as member_i3_0_0_,
         *         articles0_.title as title2_0_0_
         *     from
         *         article articles0_
         *     where
         *         articles0_.member_id in (
         *             ?, ?
         *         )
         */
    }
}
