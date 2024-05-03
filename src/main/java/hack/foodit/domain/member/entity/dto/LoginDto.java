package hack.foodit.domain.member.entity.dto;

import lombok.Data;

@Data
public class LoginDto {

  private String email;
  private String password;
}
