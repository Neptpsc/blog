package com.blog.vo;

import lombok.Data;

/**
 * 文章上一篇/下一篇导航 VO
 */
@Data
public class ArticleNavVO {
    private Long id;
    private String title;
    private String coverImg;
}
