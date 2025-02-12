package team.eusha.lifewise.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LaundryDetailResponse extends DetailResponse{
    @Builder
    public LaundryDetailResponse(Long id, String title, String imageUrl, String description) {
        super(id, title, imageUrl, description);
    }

}
