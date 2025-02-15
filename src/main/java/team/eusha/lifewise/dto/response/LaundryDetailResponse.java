package team.eusha.lifewise.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class LaundryDetailResponse extends DetailResponse{
    @Builder
    public LaundryDetailResponse(Long id, String imageUrl, String title, List<String> description) {
        super(id, imageUrl, title, description);
    }

}
