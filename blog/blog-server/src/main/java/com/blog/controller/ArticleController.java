package com.blog.controller;

import com.blog.query.ArticleQuery;
import com.blog.result.PageResult;
import com.blog.result.Result;
import com.blog.service.ArticleService;
import com.blog.vo.ArchiveVO;
import com.blog.vo.ArticleDetailVO;
import com.blog.vo.ArticleListVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章前台接口
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 文章列表（分页，支持分类/标签/关键词筛选）
     */
    @GetMapping("/articles")
    public Result<PageResult<ArticleListVO>> listArticles(ArticleQuery query) {
        return Result.success(articleService.listArticles(query));
    }

    /**
     * 文章详情
     */
    @GetMapping("/articles/{id}")
    public Result<ArticleDetailVO> getArticleDetail(@PathVariable Long id) {
        articleService.incrementViewCount(id);
        return Result.success(articleService.getArticleDetail(id));
    }

    /**
     * 文章归档（按年月分组）
     */
    @GetMapping("/archives")
    public Result<List<ArchiveVO>> listArchives() {
        return Result.success(articleService.listArchives());
    }

    /**
     * 站内搜索
     */
    @GetMapping("/search")
    public Result<PageResult<ArticleListVO>> search(ArticleQuery query) {
        return Result.success(articleService.listArticles(query));
    }
}
