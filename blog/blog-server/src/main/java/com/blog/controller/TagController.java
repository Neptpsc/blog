package com.blog.controller;

import com.blog.dto.TagDTO;
import com.blog.result.Result;
import com.blog.service.TagService;
import com.blog.vo.TagVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签接口（前台 + 后台）
 */
@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * 前台：获取所有标签及文章数
     */
    @GetMapping("/api/tags")
    public Result<List<TagVO>> listTags() {
        return Result.success(tagService.listTagsWithCount());
    }

    /**
     * 后台：新增标签
     */
    @PostMapping("/api/admin/tags")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> addTag(@RequestBody @Validated TagDTO dto) {
        tagService.addTag(dto);
        return Result.success();
    }

    /**
     * 后台：编辑标签
     */
    @PutMapping("/api/admin/tags")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> updateTag(@RequestBody @Validated TagDTO dto) {
        tagService.updateTag(dto);
        return Result.success();
    }

    /**
     * 后台：删除标签
     */
    @DeleteMapping("/api/admin/tags/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return Result.success();
    }
}
