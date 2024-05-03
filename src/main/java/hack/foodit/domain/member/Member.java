package hack.foodit.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor()
public class Member {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    //    @NonNull
    private String name;


    private String email;

    public Member(String name) {
        this.name = name;

    }

}
