package com.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章实体类
 */
@Data
@TableName("blog_article")
public class Article implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 作者 ID */
    private Long userId;

    /** 分类 ID */
    private Long categoryId;

    /** 文章标题 */
    private String title;

    /** Markdown 格式文章内容 */
    private String content;

    /** 摘要 */
    private String summary;

    /** 封面图 URL */
    private String coverImg;

    /** 状态：0草稿 / 1已发布 / 2已下线 */
    private Integer status;

    /** 是否置顶：0否 / 1是 */
    private Integer isTop;

    /** 浏览量 */
    private Integer viewCount;

    /** 评论数 */
    private Integer commentCount;

    /** 点赞数 */
    private Integer likeCount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
