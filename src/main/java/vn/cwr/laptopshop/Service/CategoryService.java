package vn.cwr.laptopshop.service;

import java.util.List;

import vn.cwr.laptopshop.domain.Category;
import vn.cwr.laptopshop.dto.CategoryCreateDTO;
import vn.cwr.laptopshop.dto.CategoryUpdateDTO;

public interface CategoryService {
    Category addToCategory(CategoryCreateDTO categoryCreateDTO);

    Category updateToCategory(CategoryUpdateDTO categoryUpdateDTO, Long id);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    void deleteCategoryById(Long id);
}
