package swm.spring.jpa.study.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "home_street"))
    })
    private Address address;    // Composite Value 타입 맵핑

    @Transient
    private String noColumn;    // @Transient하면 컬럼을 추가하지 않는다.

    @OneToMany(mappedBy = "owner")
    Set<Study> studies = new HashSet<>();

    // Convenient method
    public void addStudy(Study study) {
        this.getStudies().add(study);
        study.setOwner(this);
    }

    public void removeStudy(Study study) {
        this.getStudies().remove(study);
        study.setOwner(null);
    }
}
