package swm.spring.jpa.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import swm.spring.jpa.entity.Member;
import swm.spring.jpa.entity.Team;
import swm.spring.jpa.repository.MemberRepository;
import swm.spring.jpa.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class JpaTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;


    /**
     * N+1 테스트
     */
    @Test
    void test() {
        List<Team> teams = new ArrayList<>();
        for (int i=0; i<2; i++) {
            teams.add(new Team("Team_" + i));
        }
        teamRepository.saveAll(teams);

        List<Member> members = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Member member = new Member("Member_" + i);
            member.setTeam(teams.get(i % 2 == 0 ? 0 : 1));
            members.add(member);
        }
        memberRepository.saveAll(members);

        System.out.println("-----------------");
        List<Member> memberList = memberRepository.findAll();
        assertFalse(memberList.isEmpty());
    }
}
