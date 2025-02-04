package team.eusha.lifewise.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginResponse {
    private String accessToken;
    private String refreshToken;

    private Long memberId;
    private String memberName;
}
