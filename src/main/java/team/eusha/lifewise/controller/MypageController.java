package team.eusha.lifewise.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.eusha.lifewise.domain.Member;
import team.eusha.lifewise.dto.request.UpdateNameRequest;
import team.eusha.lifewise.dto.request.UpdatePasswordRequest;
import team.eusha.lifewise.dto.response.MypageResponse;
import team.eusha.lifewise.dto.response.NameUpdateResponse;
import team.eusha.lifewise.dto.response.PasswordUpdateResponse;
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
    public ResponseEntity<NameUpdateResponse> updateMemberName(
            @IfLogin LoginMemberDto loginMemberDto,
            @Valid @RequestBody UpdateNameRequest request
    ) {
        NameUpdateResponse response = memberService.updateName(
                loginMemberDto.getEmail(), request.getNewName()
        );
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/password")
    public ResponseEntity<PasswordUpdateResponse> updatePassword(
            @IfLogin LoginMemberDto loginMemberDto,
            @Valid @RequestBody UpdatePasswordRequest request
    ) {
        PasswordUpdateResponse response = memberService.updatePassword(
                loginMemberDto.getEmail(), request.getNewPassword()
        );
        return ResponseEntity.ok(response);
    }

}
