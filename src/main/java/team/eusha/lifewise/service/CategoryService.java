package team.eusha.lifewise.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.eusha.lifewise.domain.Detail;
import team.eusha.lifewise.domain.LaundryDetail;
import team.eusha.lifewise.domain.MainCategory;
import team.eusha.lifewise.dto.response.DetailResponse;
import team.eusha.lifewise.dto.response.LaundryDetailResponse;
import team.eusha.lifewise.dto.response.MainCategoryListResponse;
import team.eusha.lifewise.dto.response.SubCategoryListResponse;
import team.eusha.lifewise.repository.LaundryDetailRepository;
import team.eusha.lifewise.repository.MainCategoryRepository;
import team.eusha.lifewise.repository.SubCategoryRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final LaundryDetailRepository laundryDetailRepository;

    public List<MainCategoryListResponse> getAllCategories() {
        return mainCategoryRepository.findAll().stream()
                .map(category -> MainCategoryListResponse.builder()
                        .categoryId(category.getId())
                        .name(category.getName())
                        .description(category.getDescription())
                        .imageUrl(category.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }

    public SubCategoryListResponse getSubCategories(Long categoryId) {
        MainCategory mainCategory = mainCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 내용을 찾을 수 없습니다"));

        List<SubCategoryListResponse.SubCategory> subCategories =
                subCategoryRepository.findByMainCategoryId(categoryId).stream()
                        .map(sub -> SubCategoryListResponse.SubCategory.builder()
                                .subCategoryId(sub.getId())
                                .name(sub.getName())
                                .imageUrl(sub.getImageUrl())
                                .build())
                        .collect(Collectors.toList());

        return SubCategoryListResponse.builder()
                .categoryId(mainCategory.getId())
                .categoryName(mainCategory.getName())
                .subCategories(subCategories)
                .build();
    }

    public List<? extends DetailResponse> getAllDetails(Long categoryId) {
        MainCategory mainCategory = mainCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 내용을 찾을 수 없습니다"));

        Class<? extends Detail> detailType = mainCategory.getDetailType();

        if (detailType == LaundryDetail.class) {
            return laundryDetailRepository.findByCategoryId(categoryId).stream()
                    .map(this::convertToLaundryDetailResponse)
                    .collect(Collectors.toList());
        }

        throw new IllegalArgumentException("해당하는 카테고리 내용을 찾을 수 없습니다");
    }


    public List<? extends DetailResponse> getSubCategoryDetails(Long categoryId, Long subCategoryId) {
        MainCategory mainCategory = mainCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 내용을 찾을 수 없습니다"));

        Class<? extends Detail> detailType = mainCategory.getDetailType();

        if (detailType == LaundryDetail.class) {
            return laundryDetailRepository.findBySubCategoryId(subCategoryId).stream()
                    .map(this::convertToLaundryDetailResponse)
                    .collect(Collectors.toList());
        }

        throw new IllegalArgumentException("지원하지 않는 카테고리 타입입니다");
    }


    private LaundryDetailResponse convertToLaundryDetailResponse(LaundryDetail laundryDetail) {
        List<String> descriptions = Arrays.asList(laundryDetail.getDescription().split("\n"));

        return LaundryDetailResponse.builder()
                .id(laundryDetail.getId())
                .imageUrl(laundryDetail.getImageUrl())
                .title(laundryDetail.getTitle())
                .description(descriptions)
                .build();
    }

}