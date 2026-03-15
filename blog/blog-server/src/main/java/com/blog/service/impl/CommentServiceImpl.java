package com.blog.service.impl;

import cn.hutool.core.util.HtmlUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.constant.SystemConstants;
import com.blog.domain.Comment;
import com.blog.dto.CommentDTO;
import com.blog.exception.BusinessException;
import com.blog.mapper.CommentMapper;
import com.blog.result.PageResult;
import com.blog.service.CommentService;
import com.blog.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评论 Service 实现
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitComment(CommentDTO dto) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(dto, comment);
        // XSS 内容过滤
        comment.setContent(HtmlUtil.filter(dto.getContent()));
        comment.setNickname(HtmlUtil.filter(dto.getNickname()));
        // 默认待审核
        comment.setStatus(SystemConstants.COMMENT_STATUS_PENDING);
        commentMapper.insert(comment);
    }

    @Override
    public List<CommentVO> listApprovedComments(Long articleId) {
        List<CommentVO> rootComments = commentMapper.selectRootComments(articleId);
        // 为每条根评论填充子评论
        rootComments.forEach(root -> {
            List<CommentVO> children = commentMapper.selectChildComments(root.getId());
            root.setChildren(children);
        });
        return rootComments;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reviewComment(Long id, Integer status) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            throw new BusinessException(404, "评论不存在");
        }
        comment.setStatus(status);
        commentMapper.updateById(comment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long id) {
        commentMapper.deleteById(id);
    }

    @Override
    public PageResult<Comment> listAdminComments(Integer status, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<Comment>()
                .eq(status != null, Comment::getStatus, status)
                .orderByDesc(Comment::getCreateTime);
        Page<Comment> page = commentMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return new PageResult<>(page.getTotal(), page.getRecords());
    }
}
