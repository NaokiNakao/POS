package com.nakao.pos.service;

import com.nakao.pos.dao.CategoryDAO;
import com.nakao.pos.exception.CategoryDeletionException;
import com.nakao.pos.exception.CategoryNotFoundException;
import com.nakao.pos.exception.UniqueIdentifierGenerationException;
import com.nakao.pos.model.Category;
import com.nakao.pos.repository.CategoryRepository;
import com.nakao.pos.repository.ProductRepository;
import com.nakao.pos.util.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Naoki Nakao on 7/18/2023
 * @project POS
 */

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDAO categoryDAO;
    private final ProductRepository productRepository;

    public List<Category> getCategories(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Category> page = categoryRepository.findAll(pageable);
        return page.getContent();
    }

    public Category getCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
    }

    public Category createCategory(Category category) {
        category.setId(IdentifierGenerator.generateIdentifier(Category.ID_PATTERN));

        if (!categoryRepository.existsById(category.getId())) {
            categoryDAO.insert(category);
            return category;
        }
        else {
            throw new UniqueIdentifierGenerationException("Error generating unique identifier for Category");
        }
    }

    public Category updateCategory(String id, Category category) {
        Category updatedCategory = getCategoryById(id);
        BeanUtils.copyProperties(category, updatedCategory);
        updatedCategory.setId(id);
        return categoryRepository.save(updatedCategory);
    }

    public void deleteCategory(String id) {
        if (isValidCategoryDeletion(getCategoryById(id).getId())) {
            categoryRepository.deleteById(id);
        }
        else {
            throw new CategoryDeletionException("Unable to delete Category with ID: " + id);
        }
    }

    private Boolean isValidCategoryDeletion(String id) {
        return productRepository.countByCategoryId(id) == 0;
    }

}
