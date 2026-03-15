package com.blog.constant;

/**
 * 系统公共常量
 */
public class SystemConstants {

    /** 管理员角色 */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    /** 普通用户角色 */
    public static final String ROLE_USER = "ROLE_USER";

    /** 文章状态：草稿 */
    public static final Integer ARTICLE_STATUS_DRAFT = 0;

    /** 文章状态：已发布 */
    public static final Integer ARTICLE_STATUS_PUBLISHED = 1;

    /** 文章状态：已下线 */
    public static final Integer ARTICLE_STATUS_OFFLINE = 2;

    /** 评论状态：待审核 */
    public static final Integer COMMENT_STATUS_PENDING = 0;

    /** 评论状态：已通过 */
    public static final Integer COMMENT_STATUS_APPROVED = 1;

    /** 评论状态：已拒绝 */
    public static final Integer COMMENT_STATUS_REJECTED = 2;

    /** 用户状态：正常 */
    public static final Integer USER_STATUS_NORMAL = 1;

    /** 用户状态：禁用 */
    public static final Integer USER_STATUS_DISABLED = 0;

    /** 逻辑删除：未删除 */
    public static final Integer NOT_DELETED = 0;

    /** 逻辑删除：已删除 */
    public static final Integer DELETED = 1;

    /** 是否置顶：是 */
    public static final Integer IS_TOP = 1;

    /** 是否置顶：否 */
    public static final Integer NOT_TOP = 0;

    private SystemConstants() {
    }
}
