package com.blog.controller;

import com.blog.dto.CategoryDTO;
import com.blog.result.Result;
import com.blog.service.CategoryService;
import com.blog.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类接口（前台 + 后台）
 */
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 前台：获取所有分类及文章数
     */
    @GetMapping("/api/categories")
    public Result<List<CategoryVO>> listCategories() {
        return Result.success(categoryService.listCategoriesWithCount());
    }

    /**
     * 后台：新增分类
     */
    @PostMapping("/api/admin/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> addCategory(@RequestBody @Validated CategoryDTO dto) {
        categoryService.addCategory(dto);
        return Result.success();
    }

    /**
     * 后台：编辑分类
     */
    @PutMapping("/api/admin/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> updateCategory(@RequestBody @Validated CategoryDTO dto) {
        categoryService.updateCategory(dto);
        return Result.success();
    }

    /**
     * 后台：删除分类
     */
    @DeleteMapping("/api/admin/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }
}
