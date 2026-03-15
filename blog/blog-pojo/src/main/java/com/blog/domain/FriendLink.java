package com.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 友情链接实体类
 */
@Data
@TableName("blog_friend_link")
public class FriendLink implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 网站名称 */
    private String name;

    /** 网站 URL */
    private String url;

    /** 网站图标 URL */
    private String avatar;

    /** 网站描述 */
    private String description;

    /** 状态：0待审核 / 1已通过 */
    private Integer status;

    /** 排序权重 */
    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
