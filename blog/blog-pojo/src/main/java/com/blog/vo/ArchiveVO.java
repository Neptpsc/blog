package com.blog.vo;

import lombok.Data;

import java.util.List;

/**
 * 文章归档 VO
 */
@Data
public class ArchiveVO {

    /** 年月，格式：2024年01月 */
    private String yearMonth;

    /** 该月文章数量 */
    private Integer articleCount;

    /** 该月文章简要列表 */
    private List<ArticleListVO> articles;
}
