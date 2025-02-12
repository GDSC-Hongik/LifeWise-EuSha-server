package team.eusha.lifewise.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MainCategoryListResponse {
    private Long categoryId;
    private String name;
    private String description;
    private String imageUrl;
}
