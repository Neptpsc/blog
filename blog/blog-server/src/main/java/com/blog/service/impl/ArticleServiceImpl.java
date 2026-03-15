package com.blog.service.impl;

import com.blog.constant.RedisConstants;
import com.blog.constant.SystemConstants;
import com.blog.domain.Article;
import com.blog.dto.ArticleDTO;
import com.blog.exception.BusinessException;
import com.blog.mapper.ArticleMapper;
import com.blog.query.ArticleQuery;
import com.blog.result.PageResult;
import com.blog.service.ArticleService;
import com.blog.utils.StringUtils;
import com.blog.vo.ArchiveVO;
import com.blog.vo.ArticleDetailVO;
import com.blog.vo.ArticleListVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * 文章 Service 实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public PageResult<ArticleListVO> listArticles(ArticleQuery query) {
        long offset = (long) (query.getPageNum() - 1) * query.getPageSize();
        List<ArticleListVO> records = articleMapper.selectArticleListVO(
                query.getCategoryId(), query.getTagId(),
                query.getKeyword(), SystemConstants.ARTICLE_STATUS_PUBLISHED,
                offset, query.getPageSize());
        long total = articleMapper.countArticles(
                query.getCategoryId(), query.getTagId(),
                query.getKeyword(), SystemConstants.ARTICLE_STATUS_PUBLISHED);
        return new PageResult<>(total, records);
    }

    @Override
    public ArticleDetailVO getArticleDetail(Long id) {
        ArticleDetailVO vo = articleMapper.selectArticleDetailVO(id);
        if (vo == null) {
            throw new BusinessException(404, "文章不存在");
        }
        return vo;
    }

    @Override
    public void incrementViewCount(Long articleId) {
        String key = RedisConstants.ARTICLE_VIEW_COUNT + articleId;
        redisTemplate.opsForValue().increment(key, 1);
    }

    @Override
    public List<ArchiveVO> listArchives() {
        return articleMapper.selectArchives();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateArticle(ArticleDTO dto) {
        Article article = new Article();
        BeanUtils.copyProperties(dto, article);
        // 摘要为空时自动截取正文前200字
        if (article.getSummary() == null || article.getSummary().isEmpty()) {
            article.setSummary(StringUtils.buildSummary(dto.getContent(), 200));
        }
        articleMapper.insertOrUpdate(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticle(Long id) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            throw new BusinessException(404, "文章不存在");
        }
        articleMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticleStatus(Long id, Integer status) {
        Article article = new Article();
        article.setId(id);
        article.setStatus(status);
        articleMapper.updateById(article);
    }

    @Override
    public PageResult<ArticleListVO> listAdminArticles(ArticleQuery query) {
        long offset = (long) (query.getPageNum() - 1) * query.getPageSize();
        List<ArticleListVO> records = articleMapper.selectArticleListVO(
                query.getCategoryId(), query.getTagId(),
                query.getKeyword(), query.getStatus(),
                offset, query.getPageSize());
        long total = articleMapper.countArticles(
                query.getCategoryId(), query.getTagId(),
                query.getKeyword(), query.getStatus());
        return new PageResult<>(total, records);
    }

    /**
     * 定时任务：每分钟将 Redis 浏览量同步到 MySQL
     */
    @Scheduled(fixedRate = 60_000)
    public void syncViewCountToDb() {
        Set<String> keys = redisTemplate.keys(
                RedisConstants.ARTICLE_VIEW_COUNT + "*");
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        keys.forEach(key -> {
            Object val = redisTemplate.opsForValue().get(key);
            if (val == null) return;
            int count = Integer.parseInt(val.toString());
            if (count <= 0) return;
            Long articleId = Long.parseLong(
                    key.replace(RedisConstants.ARTICLE_VIEW_COUNT, ""));
            articleMapper.incrementViewCount(articleId, count);
            redisTemplate.delete(key);
            log.debug("文章[{}]浏览量同步到DB: +{}", articleId, count);
        });
    }
}
