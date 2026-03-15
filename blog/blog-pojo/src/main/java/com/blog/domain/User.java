package com.blog.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("blog_user")
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户名（登录账号） */
    private String username;

    /** BCrypt 加密密码 */
    @TableField(select = false)
    private String password;

    /** 显示昵称 */
    private String nickname;

    /** 邮箱 */
    private String email;

    /** 头像 URL */
    private String avatar;

    /** 角色：ROLE_ADMIN / ROLE_USER */
    private String role;

    /** 状态：1正常 / 0禁用 */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
