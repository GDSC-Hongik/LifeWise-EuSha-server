package team.eusha.lifewise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.eusha.lifewise.domain.Detail;
import team.eusha.lifewise.domain.Like;
import team.eusha.lifewise.domain.Member;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByMember(Member member);

    boolean existsByMemberAndDetail(Member member, Detail detail);
}
