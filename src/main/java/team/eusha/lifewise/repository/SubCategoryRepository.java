package team.eusha.lifewise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.eusha.lifewise.domain.SubCategory;

import java.util.List;


public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    List<SubCategory> findByMainCategoryId(Long mainCategoryId);

}
