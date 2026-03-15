package com.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 分类新增/编辑 DTO
 */
@Data
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "分类名称不能为空")
    @Size(max = 50, message = "分类名称不能超过50个字符")
    private String name;

    @Size(max = 200, message = "描述不能超过200个字符")
    private String description;

    private Integer sort;
}
