package team.eusha.lifewise.security.jwt.util;

import lombok.Data;

@Data
public class LoginInfoDto {

    private Long memberId;
    private String email;
    private String memberName;
}
