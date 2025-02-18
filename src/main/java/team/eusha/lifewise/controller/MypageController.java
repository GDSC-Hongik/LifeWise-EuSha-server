package team.eusha.lifewise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.eusha.lifewise.domain.Member;
import team.eusha.lifewise.dto.request.UpdateNameRequest;
import team.eusha.lifewise.dto.request.UpdatePasswordRequest;
import team.eusha.lifewise.dto.response.MypageResponse;
import team.eusha.lifewise.security.jwt.util.IfLogin;
import team.eusha.lifewise.security.jwt.util.LoginMemberDto;
import team.eusha.lifewise.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> getMyPage(@IfLogin LoginMemberDto loginMemberDto) {

        Member member = memberService.findByEmail(loginMemberDto.getEmail());
        MypageResponse response = MypageResponse.from(member);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/name")
    public ResponseEntity<Void> updateMemberName(
            @IfLogin LoginMemberDto loginMemberDto,
            @RequestBody UpdateNameRequest request
    ) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @IfLogin LoginMemberDto loginMemberDto,
            @RequestBody UpdatePasswordRequest request
    ) {
        return ResponseEntity.ok().build();
    }

}
