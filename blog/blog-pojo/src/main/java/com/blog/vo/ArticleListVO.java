package com.blog.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章列表展示 VO
 */
@Data
public class ArticleListVO {

    private Long id;
    private String title;
    private String summary;
    private String coverImg;
    private Integer isTop;
    private Integer viewCount;
    private Integer commentCount;
    private LocalDateTime createTime;

    /** 分类名称 */
    private String categoryName;
    private Long categoryId;

    /** 标签列表 */
    private List<TagVO> tags;
}
