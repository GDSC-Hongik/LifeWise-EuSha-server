package team.eusha.lifewise.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeCountResponse {
    private Long detailId;
    private long likeCount;
}
