package swm.spring.springdatajpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    private String comment;

    // primitive 타입으로하면 not null 로 들어감
    // Reference 타입으로하면 nullable 로 들어감
    private Integer likeCount;

    // ManyToOne 기본 fetch는 EAGER
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
}
