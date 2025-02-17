package team.eusha.lifewise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.eusha.lifewise.domain.Member;
import team.eusha.lifewise.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //전제 회원 조회
    @GetMapping
    public ResponseEntity<List<Member>> list() {
        List<Member> members = memberService.findMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    //특정 회원 조회
    @GetMapping("/{id}")
    public ResponseEntity<Member> detail(@PathVariable("id") Long memberId) {
        Member member = memberService.findOne(memberId);
        if (member != null) {
            return new ResponseEntity<>(member, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 회원이 없을 경우 404 Not Found 반환
        }
    }

    //회원 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 성공적으로 삭제되었을 경우 204 No Content 반환
    }
}