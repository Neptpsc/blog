package com.blog.service;

import com.blog.dto.TagDTO;
import com.blog.vo.TagVO;

import java.util.List;

/**
 * 标签 Service 接口
 */
public interface TagService {

    List<TagVO> listTagsWithCount();

    void addTag(TagDTO dto);

    void updateTag(TagDTO dto);

    void deleteTag(Long id);
}
