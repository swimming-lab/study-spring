package swm.spring.jpa.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Team team;

    public Member(String name) {
        this.name = name;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
