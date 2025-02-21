package team.eusha.lifewise.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.eusha.lifewise.domain.*;
import team.eusha.lifewise.dto.request.LikeRequest;
import team.eusha.lifewise.dto.response.LikeCountResponse;
import team.eusha.lifewise.dto.response.LikeCreateResponse;
import team.eusha.lifewise.dto.response.LikeListResponse;
import team.eusha.lifewise.repository.DetailRepository;
import team.eusha.lifewise.repository.LikeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final MemberService memberService;
    private final DetailRepository<Detail> detailRepository;
    private final LikeRepository likeRepository;

    public LikeCreateResponse createLike(String email, LikeRequest request) {
        Member member = memberService.findByEmail(email);

        Detail detail = detailRepository.findById(request.getDetailId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상세정보입니다."));

        if (detail instanceof LaundryDetail) {
            LaundryDetail laundryDetail = (LaundryDetail) detail;
        } else if (detail instanceof RecipeDetail) {
            RecipeDetail recipeDetail = (RecipeDetail) detail;
        } else if (detail instanceof RecyclingDetail) {
            RecyclingDetail recyclingDetail = (RecyclingDetail) detail;
        }

        if (likeRepository.existsByMemberAndDetail(member, detail)) {
            throw new RuntimeException("이미 좋아요한 컨텐츠입니다.");
        }

        Like like = Like.create(member, detail);
        like = likeRepository.save(like);

        return LikeCreateResponse.builder()
                .likeId(like.getId())
                .createAt(like.getCreatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public LikeListResponse getLikes(String email) {
        Member member = memberService.findByEmail(email);
        List<Like> likes = likeRepository.findByMember(member);
        return LikeListResponse.from(likes);
    }

    @Transactional
    public void deleteLike(String email, Long likeId) {
        Member member = memberService.findByEmail(email);
        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 좋아요 목록입니다."));

        if (!like.getMember().equals(member)) {
            throw new RuntimeException("해당 좋아요를 삭제할 권한이 없습니다.");
        }

        likeRepository.delete(like);
    }

    @Transactional(readOnly = true)
    public LikeCountResponse getLikeCount(Long detailId){
        Detail detail=detailRepository.findById(detailId)
                .orElseThrow(()-> new RuntimeException("존재하지 않는 상세정보입니다."));

        long count = likeRepository.countByDetail(detail);

        return LikeCountResponse.builder()
                .detailId(detailId)
                .likeCount(count)
                .build();
    }

}
