package team.eusha.lifewise.controller;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import team.eusha.lifewise.domain.Member;
import team.eusha.lifewise.domain.RefreshToken;
import team.eusha.lifewise.domain.Role;
import team.eusha.lifewise.dto.request.MemberLoginRequest;
import team.eusha.lifewise.dto.request.MemberSignupRequest;
import team.eusha.lifewise.dto.request.RefreshTokenRequest;
import team.eusha.lifewise.dto.response.MemberLoginResponse;
import team.eusha.lifewise.dto.response.MemberSignupResponse;
import team.eusha.lifewise.security.jwt.util.IfLogin;
import team.eusha.lifewise.security.jwt.util.JwtTokenizer;
import team.eusha.lifewise.security.jwt.util.LoginMemberDto;
import team.eusha.lifewise.service.MemberService;
import team.eusha.lifewise.service.RefreshTokenService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid MemberSignupRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Member member = new Member();
        member.setMemberName(request.getMemberName());
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword()));

        Member saveMember = memberService.addMember(member);

        MemberSignupResponse response = new MemberSignupResponse();
        response.setMemberId(saveMember.getMemberId());
        response.setMemberName(saveMember.getMemberName());
        response.setCreatedAt(saveMember.getCreatedAt());
        response.setEmail(saveMember.getEmail());

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid MemberLoginRequest login, BindingResult bindingResult) {

       if(bindingResult.hasErrors()) {
           return new ResponseEntity(HttpStatus.BAD_REQUEST);
       }

       Member member = memberService.findByEmail(login.getEmail());
       if(!passwordEncoder.matches(login.getPassword(), member.getPassword())) {
           return new ResponseEntity(HttpStatus.UNAUTHORIZED);
       }

       List<String> roles = member.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        // JWT 토큰 생성
        String accessToken = jwtTokenizer.createAccessToken(member.getMemberId(),member.getEmail(),member.getMemberName(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getMemberId(),member.getEmail(),member.getMemberName(), roles);

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setMemberId(member.getMemberId());
        refreshTokenService.addRefreshToken(refreshTokenEntity);

        MemberLoginResponse loginResponse = MemberLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(member.getMemberId())
                .memberName(member.getMemberName())
                .build();
        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/refreshToken")
    public ResponseEntity requestRefresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.findRefreshToken(refreshTokenRequest.getRefreshToken()).orElseThrow(() -> new IllegalArgumentException("Refresh token 값을 찾을 수 없습니다"));
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getValue());

        Long memberId = Long.valueOf((Integer)claims.get("memberId"));

        Member member = memberService.getMember(memberId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다"));


        List roles = (List) claims.get("roles");
        String email = claims.getSubject();

        String accessToken = jwtTokenizer.createAccessToken(memberId, email, member.getMemberName(), roles);

        MemberLoginResponse loginResponse = MemberLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .memberId(member.getMemberId())
                .memberName(member.getMemberName())
                .build();
        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/mypage")
    public ResponseEntity getMyPage(@IfLogin LoginMemberDto loginMemberDto) {
        Member member = memberService.findByEmail(loginMemberDto.getEmail());
        return new ResponseEntity(member, HttpStatus.OK);
    }
}
