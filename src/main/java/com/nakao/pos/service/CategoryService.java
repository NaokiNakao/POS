package com.nakao.pos.service;

import com.nakao.pos.model.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */

@Service
@Transactional
public interface CategoryService {

    List<Category> getCategories();

    Category getCategoryById(String id);

    Category createCategory(Category category);

    Category updateCategory(String id, Category updatedCategory);

    void deleteCategory(String id);

}
