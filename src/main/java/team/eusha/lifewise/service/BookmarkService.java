package team.eusha.lifewise.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.eusha.lifewise.domain.Bookmark;
import team.eusha.lifewise.domain.Detail;
import team.eusha.lifewise.domain.Member;
import team.eusha.lifewise.dto.request.BookmarkRequest;
import team.eusha.lifewise.dto.response.BookmarkCreateResponse;
import team.eusha.lifewise.dto.response.BookmarkListResponse;
import team.eusha.lifewise.repository.BookmarkRepository;
import team.eusha.lifewise.repository.DetailRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {
    private final MemberService memberService;
    @Qualifier("detailRepository")
    private final DetailRepository<Detail> detailRepository;
    private final BookmarkRepository bookmarkRepository;

    public BookmarkCreateResponse createBookmark(String email, BookmarkRequest request) {
        Member member = memberService.findByEmail(email);

        Detail detail = detailRepository.findById(request.getDetailId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상세정보입니다."));

        if (bookmarkRepository.existsByMemberAndDetail(member, detail)) {
            throw new RuntimeException("이미 북마크된 컨텐츠입니다.");
        }

        Bookmark bookmark = Bookmark.create(member, detail);
        bookmark = bookmarkRepository.save(bookmark);

        return BookmarkCreateResponse.builder()
                .bookmarkId(bookmark.getId())
                .createdAt(bookmark.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public BookmarkListResponse getBookmarks(String email) {
        Member member = memberService.findByEmail(email);
        List<Bookmark> bookmarks = bookmarkRepository.findByMember(member);
        return BookmarkListResponse.from(bookmarks);
    }

    @Transactional
    public void deleteBookmark(String email, Long bookmarkId) {
        Member member = memberService.findByEmail(email);
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 북마크입니다."));

        if (!bookmark.getMember().equals(member)) {
            throw new RuntimeException("해당 북마크를 삭제할 권한이 없습니다.");
        }

        bookmarkRepository.delete(bookmark);
    }
}