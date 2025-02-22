package team.eusha.lifewise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.eusha.lifewise.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByMemberName(String memberName);
}
