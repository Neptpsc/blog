package com.blog.controller;

import com.blog.dto.ArticleDTO;
import com.blog.query.ArticleQuery;
import com.blog.result.PageResult;
import com.blog.result.Result;
import com.blog.service.ArticleService;
import com.blog.vo.ArticleListVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 文章后台管理接口
 */
@RestController
@RequestMapping("/api/admin/articles")
@RequiredArgsConstructor
public class AdminArticleController {

    private final ArticleService articleService;

    /**
     * 后台文章列表
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<PageResult<ArticleListVO>> listAdminArticles(ArticleQuery query) {
        return Result.success(articleService.listAdminArticles(query));
    }

    /**
     * 新建或更新文章
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> saveOrUpdateArticle(@RequestBody @Validated ArticleDTO dto) {
        articleService.saveOrUpdateArticle(dto);
        return Result.success();
    }

    /**
     * 删除文章（软删除）
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return Result.success();
    }

    /**
     * 更新文章状态（发布/下线）
     */
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> updateArticleStatus(@PathVariable Long id,
                                             @RequestParam Integer status) {
        articleService.updateArticleStatus(id, status);
        return Result.success();
    }
}
