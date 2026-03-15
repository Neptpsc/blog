package com.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 文章新增/编辑 DTO
 */
@Data
public class ArticleDTO {

    /** 文章 ID（编辑时必传） */
    private Long id;

    @NotBlank(message = "文章标题不能为空")
    private String title;

    @NotBlank(message = "文章内容不能为空")
    private String content;

    /** 摘要（为空时自动截取） */
    private String summary;

    /** 封面图 URL */
    private String coverImg;

    @NotNull(message = "分类不能为空")
    private Long categoryId;

    /** 标签 ID 列表 */
    private List<Long> tagIds;

    /** 状态：0草稿 / 1发布 */
    private Integer status;

    /** 是否置顶：0否 / 1是 */
    private Integer isTop;
}
