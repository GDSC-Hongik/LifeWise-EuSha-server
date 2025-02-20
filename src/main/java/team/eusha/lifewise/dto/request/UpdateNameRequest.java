package team.eusha.lifewise.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateNameRequest {
    @NotEmpty(message = "새 이름을 입력하세요.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,15}$",
            message = "이름은 2~15자의 영문자, 숫자, 한글만 허용됩니다.")
    private String newName;
}
