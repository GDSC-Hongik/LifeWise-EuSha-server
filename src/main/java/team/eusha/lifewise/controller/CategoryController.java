package team.eusha.lifewise.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.eusha.lifewise.dto.response.DetailResponse;
import team.eusha.lifewise.dto.response.MainCategoryListResponse;
import team.eusha.lifewise.dto.response.SubCategoryListResponse;
import team.eusha.lifewise.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<MainCategoryListResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}/subcategories")
    public ResponseEntity<SubCategoryListResponse> getSubCategories(
            @PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(categoryService.getSubCategories(categoryId));
    }

    @GetMapping("/{categoryId}/subcategories/all")
    public ResponseEntity<List<? extends DetailResponse>> getAllCategoryDetails(
            @PathVariable("categoryId") Long categoryId
    ) {
        return ResponseEntity.ok(categoryService.getAllDetails(categoryId));
    }

    @GetMapping("/{categoryId}/subcategories/{subCategoryId}")
    public ResponseEntity<List<? extends DetailResponse>> getSubCategoryDetails(
            @PathVariable("categoryId") Long categoryId,
            @PathVariable("subCategoryId") Long subCategoryId
    ) {
        return ResponseEntity.ok(categoryService.getSubCategoryDetails(categoryId, subCategoryId));
    }
}
