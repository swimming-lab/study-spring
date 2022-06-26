package swm.spring.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @BatchSize(size = 10)
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Set<Article> articles = Collections.emptySet();

    public Member(String name) {
        this.name = name;
    }
}
