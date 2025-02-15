package team.eusha.lifewise.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class DetailResponse {
    private final Long id;
    private final String imageUrl;
    private final String title;
    private final List<String> description;

    protected DetailResponse(Long id, String imageUrl, String title, List<String> description) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
    }

}