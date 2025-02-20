package team.eusha.lifewise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.eusha.lifewise.dto.request.LikeRequest;
import team.eusha.lifewise.dto.response.LikeCreateResponse;
import team.eusha.lifewise.dto.response.LikeListResponse;
import team.eusha.lifewise.security.jwt.util.IfLogin;
import team.eusha.lifewise.security.jwt.util.LoginMemberDto;
import team.eusha.lifewise.service.LikeService;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/likes")
    public ResponseEntity<LikeCreateResponse> createLike(@IfLogin LoginMemberDto loginMemberDto, @RequestBody LikeRequest request) {
        LikeCreateResponse response = likeService.createLike(loginMemberDto.getEmail(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/likes")
    public ResponseEntity<LikeListResponse> getLikes(@IfLogin LoginMemberDto loginMemberDto) {
        LikeListResponse response = likeService.getLikes(loginMemberDto.getEmail());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/likes/{likeId}")
    public ResponseEntity<Void> deleteBookmark(@IfLogin LoginMemberDto loginMemberDto, @PathVariable("likeId") Long likeId) {
        likeService.deleteLike(loginMemberDto.getEmail(), likeId);
        return ResponseEntity.ok().build();
    }
}
