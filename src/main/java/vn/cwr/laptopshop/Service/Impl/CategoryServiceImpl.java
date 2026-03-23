package vn.cwr.laptopshop.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.cwr.laptopshop.domain.Category;
import vn.cwr.laptopshop.dto.CategoryCreateDTO;
import vn.cwr.laptopshop.dto.CategoryUpdateDTO;
import vn.cwr.laptopshop.repository.CategoryRepository;
import vn.cwr.laptopshop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category addToCategory(CategoryCreateDTO categoryCreateDTO) {
        if (categoryRepository.existsByNameIgnoreCase(categoryCreateDTO.getName())) {
            throw new RuntimeException("Đã tồn tại danh mục này rồi!");
        }
        Category category = new Category();
        category.setName(categoryCreateDTO.getName());
        category.setDescription(categoryCreateDTO.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateToCategory(CategoryUpdateDTO categoryUpdateDTO, Long id) {
        Category existsCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tồn tại Danh mục này!"));
        if (categoryUpdateDTO.getName() != null && !categoryUpdateDTO.getName().trim().isEmpty()) {
            existsCategory.setName(categoryUpdateDTO.getName());
        }
        if (categoryUpdateDTO.getDescription() != null && !categoryUpdateDTO.getDescription().trim().isEmpty()) {
            existsCategory.setDescription(categoryUpdateDTO.getDescription());
        }
        return categoryRepository.save(existsCategory);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tồn tại Danh mục này!"));
        return category;
    }

    @Override
    public void deleteCategoryById(Long id) {
        this.getCategoryById(id);
        categoryRepository.deleteById(id);
    }
}
