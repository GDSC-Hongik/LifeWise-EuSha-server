package team.eusha.lifewise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.eusha.lifewise.dto.request.BookmarkRequest;
import team.eusha.lifewise.dto.response.BookmarkCreateResponse;
import team.eusha.lifewise.dto.response.BookmarkListResponse;
import team.eusha.lifewise.security.jwt.util.IfLogin;
import team.eusha.lifewise.security.jwt.util.LoginMemberDto;
import team.eusha.lifewise.service.BookmarkService;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/bookmarks")
    public ResponseEntity<BookmarkCreateResponse> createBookmark(@IfLogin LoginMemberDto loginMemberDto, @RequestBody BookmarkRequest request) {
        BookmarkCreateResponse response = bookmarkService.createBookmark(loginMemberDto.getEmail(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<BookmarkListResponse> getBookmarks(@IfLogin LoginMemberDto loginMemberDto) {
        BookmarkListResponse response = bookmarkService.getBookmarks(loginMemberDto.getEmail());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/bookmarks/{bookmarkId}")
    public ResponseEntity<Void> deleteBookmark(@IfLogin LoginMemberDto loginMemberDto, @PathVariable("bookmarkId") Long bookmarkId) {
        bookmarkService.deleteBookmark(loginMemberDto.getEmail(), bookmarkId);
        return ResponseEntity.ok().build();
    }
}
