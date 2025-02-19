package team.eusha.lifewise.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.eusha.lifewise.domain.Member;
import team.eusha.lifewise.domain.Role;
import team.eusha.lifewise.dto.response.NameUpdateResponse;
import team.eusha.lifewise.dto.response.PasswordUpdateResponse;
import team.eusha.lifewise.repository.MemberRepository;
import team.eusha.lifewise.repository.RoleRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
    }

    @Transactional
    public Member addMember(Member member) {

        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        if (memberRepository.findByMemberName(member.getMemberName()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 사용자 이름입니다.");
        }

        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        member.addRole(userRole.get());
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }


    @Transactional(readOnly = true)
    public Optional<Member> getMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional(readOnly = true)
    public Optional<Member> getMember(String email) {
        return memberRepository.findByEmail(email);
    }

    private final PasswordEncoder passwordEncoder;

    //이름 변경
    @Transactional
    public NameUpdateResponse updateName(String email, String newName) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        member.updateName(newName);

        return new NameUpdateResponse(
                member.getMemberId(),
                member.getMemberName(),
                member.getEmail(),
                LocalDateTime.now()
        );
    }

    //비번 변경
    @Transactional
    public PasswordUpdateResponse updatePassword(String email, String newPassword) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 새 비밀번호 암호화 후 저장
        member.updatePassword(passwordEncoder.encode(newPassword));

        return new PasswordUpdateResponse("비밀번호 변경 성공", LocalDateTime.now());
    }
}
