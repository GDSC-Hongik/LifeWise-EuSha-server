package team.eusha.lifewise.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberSignupResponse {
    private String name;
    private Long memberId;
    private String email;
    private LocalDateTime createdAt;
}
