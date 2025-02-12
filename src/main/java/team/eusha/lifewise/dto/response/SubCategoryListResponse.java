package team.eusha.lifewise.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SubCategoryListResponse {
    private Long categoryId;
    private String categoryName;
    private List<SubCategory> subCategories;

    @Getter
    @Builder
    public static class SubCategory {
        private Long subCategoryId;
        private String name;
        private String imageUrl;
    }
}
