package team.eusha.lifewise.dto.response;

import lombok.Builder;
import lombok.Getter;
import team.eusha.lifewise.domain.Member;

import java.time.LocalDateTime;

@Getter
@Builder
public class MypageResponse {
    private Long memberId;
    private String email;
    private String memberName;
    private LocalDateTime createdAt;

    public static MypageResponse from(Member member) {
        return MypageResponse.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .memberName(member.getMemberName())
                .createdAt(member.getCreatedAt())
                .build();
    }
}