package team.eusha.lifewise.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "recipe_detail")
@DiscriminatorValue("RECIPE")
public class RecipeDetail extends Detail{
    // 상위 Detail 클래스의 description 필드가 필요한 재료 설명
    private Integer cookingTime;
    private String recipeUrl;
}
