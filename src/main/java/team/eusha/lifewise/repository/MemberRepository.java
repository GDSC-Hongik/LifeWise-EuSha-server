package team.eusha.lifewise.repository;

import team.eusha.lifewise.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // JPA 기본 제공 메서드 외 추가적인 메서드가 필요하다면 여기에 정의
    // 예: 이메일로 회원 찾기
    Optional<Member> findByEmail(String email);

    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);


}
