package swm.spring.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private Member member;

    public Article(String title) {
        this.title = title;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
