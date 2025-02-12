package team.eusha.lifewise.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "main_category")
@Getter
public class MainCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    @Column(nullable = false)
    private Class<? extends Detail> detailType;

    @OneToMany(mappedBy = "mainCategory")
    private List<SubCategory> subCategories = new ArrayList<>();
}
