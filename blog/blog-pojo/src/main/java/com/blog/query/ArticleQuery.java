package com.blog.query;

import lombok.Data;

/**
 * 文章查询条件
 */
@Data
public class ArticleQuery {

    /** 当前页码，默认第1页 */
    private Integer pageNum = 1;

    /** 每页大小，默认10条 */
    private Integer pageSize = 10;

    /** 按分类ID筛选 */
    private Long categoryId;

    /** 按标签ID筛选 */
    private Long tagId;

    /** 关键词搜索（标题/内容） */
    private String keyword;

    /** 状态筛选（后台使用） */
    private Integer status;
}
