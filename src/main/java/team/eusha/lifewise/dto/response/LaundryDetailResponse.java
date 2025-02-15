package team.eusha.lifewise.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LaundryDetailResponse extends DetailResponse{
    @Builder
    public LaundryDetailResponse(Long id, String imageUrl, String title, String description) {
        super(id, imageUrl, title, description);
    }

}
