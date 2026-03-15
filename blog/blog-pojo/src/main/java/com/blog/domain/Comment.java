package com.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评论实体类
 */
@Data
@TableName("blog_comment")
public class Comment implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 所属文章 ID */
    private Long articleId;

    /** 评论用户 ID（游客为 null） */
    private Long userId;

    /** 父评论 ID（回复时使用，根评论为 null） */
    private Long parentId;

    /** 评论者昵称 */
    private String nickname;

    /** 评论者邮箱 */
    private String email;

    /** 评论内容（已经过 XSS 过滤） */
    private String content;

    /** 状态：0待审核 / 1已通过 / 2已拒绝 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
