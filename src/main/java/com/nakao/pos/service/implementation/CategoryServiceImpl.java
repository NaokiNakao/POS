package com.nakao.pos.service.implementation;

import com.nakao.pos.dao.CategoryDAO;
import com.nakao.pos.util.exception.CategoryDeletionException;
import com.nakao.pos.util.exception.CategoryNotFoundException;
import com.nakao.pos.model.Category;
import com.nakao.pos.repository.CategoryRepository;
import com.nakao.pos.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryDAO dao;

    @Override
    public List<Category> getCategories() {
        return (List<Category>) repository.findAll();
    }

    @Override
    public Category getCategoryById(String id) {
        Optional<Category> category = repository.findById(id);
        if (category.isPresent()) {
            return category.get();
        }
        else {
            throw new CategoryNotFoundException("Category not found");
        }
    }

    @Override
    public Category createCategory(Category category) {
        return dao.insert(category);
    }

    @Override
    public Category updateCategory(String id, Category category) {
        Category updatedCategory = getCategoryById(id);
        updatedCategory.setName(category.getName());
        return repository.save(updatedCategory);
    }

    @Override
    public void deleteCategory(String id) {
        Optional<Category> category = repository.findById(id);

        if (category.isPresent()) {
            if (validCategoryDeletion(category.get())) {
                repository.deleteById(id);
            }
            else {
                throw new CategoryDeletionException("Unable to delete category. One or more products are associated with this category.");
            }
        }
        else {
            throw new CategoryNotFoundException("Category not found");
        }
    }

    private Boolean validCategoryDeletion(Category category) {
        boolean valid = false;

        if (dao.getProductCountByCategoryId(category.getId()) == 0) {
            valid = true;
        }

        return valid;
    }

}
