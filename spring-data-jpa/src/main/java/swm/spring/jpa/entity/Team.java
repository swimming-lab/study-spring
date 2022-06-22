package swm.spring.jpa.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Team(String name) {
        this.name = name;
    }
}
