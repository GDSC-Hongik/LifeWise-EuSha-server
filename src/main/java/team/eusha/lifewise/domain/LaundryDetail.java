package team.eusha.lifewise.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "laundry_detail")
@DiscriminatorValue("LAUNDRY")
public class LaundryDetail extends Detail{
    // 상위 Detail 클래스의 description 필드가 세탁방법 담당
}
