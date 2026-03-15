package com.blog.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论展示 VO
 */
@Data
public class CommentVO {

    private Long id;
    private Long parentId;
    private String nickname;
    private String email;
    private String content;
    private LocalDateTime createTime;

    /** 子评论列表（回复） */
    private java.util.List<CommentVO> children;
}
