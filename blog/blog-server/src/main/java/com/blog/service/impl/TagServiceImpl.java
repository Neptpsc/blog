package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.domain.Tag;
import com.blog.dto.TagDTO;
import com.blog.exception.BusinessException;
import com.blog.mapper.TagMapper;
import com.blog.service.TagService;
import com.blog.vo.TagVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 标签 Service 实现
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;

    @Override
    public List<TagVO> listTagsWithCount() {
        return tagMapper.selectTagWithCount();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTag(TagDTO dto) {
        checkDuplicate(dto.getName(), null);
        Tag tag = new Tag();
        BeanUtils.copyProperties(dto, tag);
        tagMapper.insert(tag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTag(TagDTO dto) {
        if (dto.getId() == null) {
            throw new BusinessException(400, "标签ID不能为空");
        }
        checkDuplicate(dto.getName(), dto.getId());
        Tag tag = new Tag();
        BeanUtils.copyProperties(dto, tag);
        tagMapper.updateById(tag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(Long id) {
        tagMapper.deleteById(id);
    }

    private void checkDuplicate(String name, Long excludeId) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<Tag>()
                .eq(Tag::getName, name);
        if (excludeId != null) {
            wrapper.ne(Tag::getId, excludeId);
        }
        if (tagMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(400, "标签名称已存在");
        }
    }
}
