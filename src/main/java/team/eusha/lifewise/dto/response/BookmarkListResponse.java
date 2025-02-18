package team.eusha.lifewise.dto.response;

import lombok.Builder;
import lombok.Getter;
import team.eusha.lifewise.domain.Bookmark;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class BookmarkListResponse {
    private List<BookmarkResponse> bookmarks;
    @Getter
    @Builder
    public static class BookmarkResponse {
        private Long bookmarkId;
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

        public static BookmarkResponse from(Bookmark bookmark) {
            String[] descriptions = bookmark.getDetail().getDescription().split("\n");
            List<String> descriptionList = List.of(descriptions);

            return BookmarkResponse.builder()
                    .bookmarkId(bookmark.getId())
                    .detail(DetailInfo.builder()
                            .id(bookmark.getDetail().getId())
                            .imageUrl(bookmark.getDetail().getImageUrl())
                            .title(bookmark.getDetail().getTitle())
                            .description(descriptionList)
                            .build())
                    .createdAt(bookmark.getCreatedAt())
                    .build();
        }
    }

    public static BookmarkListResponse from(List<Bookmark> bookmarks) {
        List<BookmarkResponse> responses = bookmarks.stream()
                .map(BookmarkResponse::from)
                .collect(Collectors.toList());

        return BookmarkListResponse.builder()
                .bookmarks(responses)
                .build();
    }
}