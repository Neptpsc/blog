package com.blog.vo;

import lombok.Data;

/**
 * 标签 VO
 */
@Data
public class TagVO {
    private Long id;
    private String name;
    /** 关联文章数 */
    private Integer articleCount;
    /** 根据文章数计算的字体大小（em），用于标签云 */
    private Double fontSize;
}
