package team.eusha.lifewise.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.eusha.lifewise.domain.Detail;

import java.util.List;
@Primary
public interface DetailRepository<T extends Detail> extends JpaRepository<T, Long> {
    List<T> findBySubCategoryId(Long subCategoryId);

    @Query("SELECT d FROM #{#entityName} d " +
            "JOIN d.subCategory sc " +
            "WHERE sc.mainCategory.id = :categoryId")
    List<T> findByCategoryId(@Param("categoryId") Long categoryId);
}