package team.eusha.lifewise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.eusha.lifewise.domain.Bookmark;
import team.eusha.lifewise.domain.Detail;
import team.eusha.lifewise.domain.Member;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByMember(Member member);
    boolean existsByMemberAndDetail(Member member, Detail detail);
}
