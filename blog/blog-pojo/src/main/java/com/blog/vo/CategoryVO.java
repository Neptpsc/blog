package com.blog.vo;

import lombok.Data;

/**
 * 分类 VO
 */
@Data
public class CategoryVO {
    private Long id;
    private String name;
    private String description;
    /** 该分类下的文章数 */
    private Integer articleCount;
}
