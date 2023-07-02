package com.ecommercemicro.productservice.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    private Long categoryId;
    private String categoryName;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
