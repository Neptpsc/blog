package com.blog.constant;

/**
 * Redis Key 常量
 */
public class RedisConstants {

    /** 文章浏览量 Key 前缀，格式：article:view_count:{articleId} */
    public static final String ARTICLE_VIEW_COUNT = "article:view_count:";

    /** 文章列表缓存 Key 前缀 */
    public static final String ARTICLE_LIST = "article:list:";

    /** 分类列表缓存 Key */
    public static final String CATEGORY_LIST = "category:list";

    /** 标签列表缓存 Key */
    public static final String TAG_LIST = "tag:list";

    /** 归档列表缓存 Key */
    public static final String ARCHIVE_LIST = "archive:list";

    /** 博客配置缓存 Key 前缀 */
    public static final String BLOG_CONFIG = "blog:config:";

    /** JWT 黑名单前缀（已登出的 Token） */
    public static final String JWT_BLACKLIST = "jwt:blacklist:";

    private RedisConstants() {
    }
}
