package com.blog.service;

import com.blog.dto.ArticleDTO;
import com.blog.query.ArticleQuery;
import com.blog.result.PageResult;
import com.blog.vo.ArchiveVO;
import com.blog.vo.ArticleDetailVO;
import com.blog.vo.ArticleListVO;

import java.util.List;

/**
 * 文章 Service 接口
 */
public interface ArticleService {

    /**
     * 分页查询文章列表（前台，仅已发布）
     */
    PageResult<ArticleListVO> listArticles(ArticleQuery query);

    /**
     * 查询文章详情
     */
    ArticleDetailVO getArticleDetail(Long id);

    /**
     * 异步增加浏览量（Redis incr）
     */
    void incrementViewCount(Long articleId);

    /**
     * 查询归档列表
     */
    List<ArchiveVO> listArchives();

    /**
     * 后台：保存或更新文章
     */
    void saveOrUpdateArticle(ArticleDTO dto);

    /**
     * 后台：删除文章（软删除）
     */
    void deleteArticle(Long id);

    /**
     * 后台：更新文章状态
     */
    void updateArticleStatus(Long id, Integer status);

    /**
     * 后台：分页查询所有文章（含草稿）
     */
    PageResult<ArticleListVO> listAdminArticles(ArticleQuery query);
}
