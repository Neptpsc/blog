package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.domain.Article;
import com.blog.vo.ArchiveVO;
import com.blog.vo.ArticleListVO;
import com.blog.vo.ArticleNavVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章 Mapper
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询文章列表（含分类名称和标签）
     */
    List<ArticleListVO> selectArticleListVO(
            @Param("categoryId") Long categoryId,
            @Param("tagId") Long tagId,
            @Param("keyword") String keyword,
            @Param("status") Integer status,
            @Param("offset") long offset,
            @Param("limit") int limit);

    /**
     * 统计文章总数（含筛选条件）
     */
    long countArticles(
            @Param("categoryId") Long categoryId,
            @Param("tagId") Long tagId,
            @Param("keyword") String keyword,
            @Param("status") Integer status);

    /**
     * 查询文章详情 VO
     */
    com.blog.vo.ArticleDetailVO selectArticleDetailVO(@Param("id") Long id);

    /**
     * 查询归档列表（按年月分组）
     */
    List<ArchiveVO> selectArchives();

    /**
     * 批量增加浏览量
     */
    void incrementViewCount(@Param("id") Long id, @Param("count") int count);

    /**
     * 插入或更新文章（ON DUPLICATE KEY UPDATE）
     */
    void insertOrUpdate(Article article);

    /**
     * 删除文章的所有标签关联
     */
    void deleteArticleTags(@Param("articleId") Long articleId);

    /**
     * 批量插入文章-标签关联
     */
    void insertArticleTags(@Param("articleId") Long articleId,
                           @Param("tagIds") List<Long> tagIds);

    /**
     * 查询上一篇（id 比当前小且已发布的最新一篇）
     */
    ArticleNavVO selectPrevArticle(@Param("id") Long id);

    /**
     * 查询下一篇（id 比当前大且已发布的最旧一篇）
     */
    ArticleNavVO selectNextArticle(@Param("id") Long id);
}
