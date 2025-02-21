package team.eusha.lifewise.dto.response;

import lombok.Builder;
import lombok.Getter;
import team.eusha.lifewise.domain.Like;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class LikeListResponse {
    private List<LikeResponse> likes;

    @Getter
    @Builder
    public static class LikeResponse {
        private Long likeId;
        private DetailInfo detail;
        private LocalDateTime createdAt;

        @Getter
        @Builder
        public static class DetailInfo {
            private Long id;
            private String imageUrl;
            private String title;
            private List<String> description;
        }

        public static LikeResponse from(Like like) {
            String[] descriptions = like.getDetail().getDescription().split("\n");
            List<String> descriptionList = List.of(descriptions);

            return LikeResponse.builder()
                    .likeId(like.getId())
                    .detail(DetailInfo.builder()
                            .id(like.getDetail().getId())
                            .imageUrl(like.getDetail().getImageUrl())
                            .title(like.getDetail().getTitle())
                            .description(descriptionList)
                            .build())
                    .createdAt(like.getCreatedAt())
                    .build();
        }
    }

    public static LikeListResponse from(List<Like> likes) {
        List<LikeResponse> responses = likes.stream()
                .map(LikeResponse::from)
                .collect(Collectors.toList());

        return LikeListResponse.builder()
                .likes(responses)
                .build();
    }

}
