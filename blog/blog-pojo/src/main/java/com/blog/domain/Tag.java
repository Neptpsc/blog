package com.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 标签实体类
 */
@Data
@TableName("blog_tag")
public class Tag implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 标签名称 */
    private String name;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 关联文章数（非数据库字段，查询聚合使用） */
    @TableField(exist = false)
    private Integer articleCount;
}
