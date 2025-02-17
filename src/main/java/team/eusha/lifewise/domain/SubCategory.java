package team.eusha.lifewise.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sub_category")
@Getter
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_category_id")
    private MainCategory mainCategory;

    @OneToMany(mappedBy = "subCategory")
    private List<Detail> details = new ArrayList<>();
}
