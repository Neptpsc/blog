package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.domain.Tag;
import com.blog.vo.TagVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签 Mapper
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 查询所有标签及对应的文章数量
     */
    List<TagVO> selectTagWithCount();

    /**
     * 查询某篇文章的标签列表
     */
    List<TagVO> selectTagsByArticleId(@Param("articleId") Long articleId);
}
