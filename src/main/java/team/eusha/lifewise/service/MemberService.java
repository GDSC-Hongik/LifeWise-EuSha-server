package team.eusha.lifewise.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.eusha.lifewise.domain.Member;
import team.eusha.lifewise.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) // 읽기 작업에는 readOnly = true 설정
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class MemberService {

    private final MemberRepository memberRepository;

    //전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //특정 회원 조회
    public Member findOne(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        return member.orElse(null);
    }

    //회원 삭제
    @Transactional // 쓰기 작업에는 @Transactional 필요 (readOnly = false 가 default)
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

}
