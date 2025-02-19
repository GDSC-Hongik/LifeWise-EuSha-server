package team.eusha.lifewise.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "recycling_detail")
@DiscriminatorValue("RECYCLING")
public class RecyclingDetail extends Detail{
    // 상위 Detail 클래스의 description 필드가 버리는 방법 담당
    private String wasteType;
}
