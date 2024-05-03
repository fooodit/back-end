package hack.foodit.domain.member.entity.dto;

import hack.foodit.domain.member.entity.Member;
import lombok.Data;

@Data
public class SignUpDto {

  private String email;

  private String name;

  private String password;

  public Member toEntity() {
    return Member.builder()
        .email(email)
        .name(name)
        .password(password)
        .build();
  }
}
