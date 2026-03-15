package com.blog.controller;

import com.blog.domain.Comment;
import com.blog.dto.CommentDTO;
import com.blog.result.PageResult;
import com.blog.result.Result;
import com.blog.service.CommentService;
import com.blog.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论接口
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 前台：查询文章评论列表（树形结构）
     */
    @GetMapping("/articles/{articleId}/comments")
    public Result<List<CommentVO>> listComments(@PathVariable Long articleId) {
        return Result.success(commentService.listApprovedComments(articleId));
    }

    /**
     * 提交评论（游客/用户均可）
     */
    @PostMapping("/comments")
    public Result<Void> submitComment(@RequestBody @Validated CommentDTO dto) {
        commentService.submitComment(dto);
        return Result.success();
    }

    /**
     * 后台：分页查询评论（按状态筛选）
     */
    @GetMapping("/admin/comments")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<PageResult<Comment>> listAdminComments(
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "20") Integer pageSize) {
        return Result.success(commentService.listAdminComments(status, pageNum, pageSize));
    }

    /**
     * 后台：审核评论
     */
    @PutMapping("/admin/comments/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> reviewComment(@PathVariable Long id,
                                       @RequestParam Integer status) {
        commentService.reviewComment(id, status);
        return Result.success();
    }

    /**
     * 后台：删除评论
     */
    @DeleteMapping("/admin/comments/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return Result.success();
    }
}
