package team.eusha.lifewise.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RefreshTokenRequest {

    @NotEmpty
    String refreshToken;
}
