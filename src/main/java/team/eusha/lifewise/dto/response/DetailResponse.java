package team.eusha.lifewise.dto.response;

import lombok.Getter;

@Getter
public abstract class DetailResponse {
    private final Long id;
    private final String imageUrl;
    private final String title;
    private final String description;

    protected DetailResponse(Long id, String imageUrl, String title, String description) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
    }

}