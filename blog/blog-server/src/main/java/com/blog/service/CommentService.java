package com.blog.service;

import com.blog.domain.Comment;
import com.blog.dto.CommentDTO;
import com.blog.result.PageResult;
import com.blog.vo.CommentVO;

import java.util.List;

/**
 * 评论 Service 接口
 */
public interface CommentService {

    /**
     * 提交评论（游客/用户均可，需审核）
     */
    void submitComment(CommentDTO dto);

    /**
     * 查询文章下已通过审核的评论（树形结构）
     */
    List<CommentVO> listApprovedComments(Long articleId);

    /**
     * 后台：审核评论
     */
    void reviewComment(Long id, Integer status);

    /**
     * 后台：删除评论
     */
    void deleteComment(Long id);

    /**
     * 后台：分页查询评论（按状态筛选）
     */
    PageResult<Comment> listAdminComments(Integer status, Integer pageNum, Integer pageSize);
}
