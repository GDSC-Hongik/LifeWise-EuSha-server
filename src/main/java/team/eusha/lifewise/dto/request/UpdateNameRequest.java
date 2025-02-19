package team.eusha.lifewise.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateNameRequest {
    @NotEmpty(message = "새 이름을 입력하세요.")
    @Pattern(regexp = "^[a-zA-Z가-힣\\s]{2,15}$",
            message = "이름은 2~15자 사이의 공백 포함 영문자, 한글이어야 합니다.")
    private String newName;
}
