package com.blog.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章详情 VO
 */
@Data
public class ArticleDetailVO {

    private Long id;
    private String title;
    private String content;
    private String coverImg;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    /** 分类 */
    private Long categoryId;
    private String categoryName;

    /** 标签列表 */
    private List<TagVO> tags;

    /** 作者昵称 */
    private String authorNickname;
    private String authorAvatar;

    /** 上一篇 */
    private ArticleNavVO prevArticle;

    /** 下一篇 */
    private ArticleNavVO nextArticle;
}
