package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.domain.Article;
import com.blog.vo.ArchiveVO;
import com.blog.vo.ArticleListVO;
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
}
