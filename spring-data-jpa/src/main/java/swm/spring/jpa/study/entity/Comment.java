package swm.spring.jpa.study.entity;

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

    @ManyToOne
    private Post post;
}
