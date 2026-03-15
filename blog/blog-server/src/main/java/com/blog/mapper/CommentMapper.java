package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.domain.Comment;
import com.blog.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论 Mapper
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 查询文章下通过审核的根评论（不含子评论）
     */
    List<CommentVO> selectRootComments(@Param("articleId") Long articleId);

    /**
     * 查询指定父评论下的子评论
     */
    List<CommentVO> selectChildComments(@Param("parentId") Long parentId);
}
