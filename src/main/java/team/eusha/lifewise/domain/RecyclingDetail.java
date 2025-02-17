package team.eusha.lifewise.domain;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class RecyclingDetail extends Detail{
    // 상위 Detail 클래스의 description 필드가 버리는 방법 담당
    private String wasteType;
}
