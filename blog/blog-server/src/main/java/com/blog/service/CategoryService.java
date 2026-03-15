package com.blog.service;

import com.blog.dto.CategoryDTO;
import com.blog.vo.CategoryVO;

import java.util.List;

/**
 * 分类 Service 接口
 */
public interface CategoryService {

    List<CategoryVO> listCategoriesWithCount();

    void addCategory(CategoryDTO dto);

    void updateCategory(CategoryDTO dto);

    void deleteCategory(Long id);
}
