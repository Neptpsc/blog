-- ============================================================
-- 博客系统数据库初始化脚本
-- 数据库：blog
-- 字符集：utf8mb4
-- 说明：幂等脚本，可重复执行（DROP TABLE IF EXISTS）
-- ============================================================

CREATE DATABASE IF NOT EXISTS `blog`
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE `blog`;

-- ------------------------------------------------------------
-- 1. 用户表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `blog_user`;
CREATE TABLE `blog_user` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`    VARCHAR(50)  NOT NULL                COMMENT '登录用户名',
    `password`    VARCHAR(100) NOT NULL                COMMENT 'BCrypt 加密密码',
    `nickname`    VARCHAR(50)  NOT NULL                COMMENT '昵称',
    `email`       VARCHAR(100)          DEFAULT NULL   COMMENT '邮箱',
    `avatar`      VARCHAR(255)          DEFAULT NULL   COMMENT '头像 URL',
    `role`        VARCHAR(20)  NOT NULL DEFAULT 'ROLE_USER'
                                                       COMMENT '角色：ROLE_ADMIN / ROLE_USER',
    `status`      TINYINT      NOT NULL DEFAULT 1      COMMENT '状态：1正常 / 0禁用',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
                                                       COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
                                   ON UPDATE CURRENT_TIMESTAMP
                                                       COMMENT '更新时间',
    `deleted`     TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除：0正常 / 1删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 初始管理员（密码：admin123，BCrypt 加密）
INSERT INTO `blog_user` (`username`, `password`, `nickname`, `email`, `role`, `status`)
VALUES ('admin',
        '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2',
        '博主', 'admin@blog.com', 'ROLE_ADMIN', 1);

-- ------------------------------------------------------------
-- 2. 分类表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `blog_category`;
CREATE TABLE `blog_category` (
    `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        VARCHAR(50) NOT NULL                COMMENT '分类名称',
    `description` VARCHAR(200)         DEFAULT NULL   COMMENT '分类描述',
    `sort`        INT         NOT NULL DEFAULT 0      COMMENT '排序权重（越大越靠前）',
    `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP
                                  ON UPDATE CURRENT_TIMESTAMP   COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章分类表';

INSERT INTO `blog_category` (`name`, `description`, `sort`) VALUES
('技术',   'Java / Spring / Vue 等技术文章',   10),
('生活',   '日常生活与随笔',                     5),
('读书',   '读书笔记与书评',                     3);

-- ------------------------------------------------------------
-- 3. 标签表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `blog_tag`;
CREATE TABLE `blog_tag` (
    `id`          BIGINT     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        VARCHAR(30) NOT NULL               COMMENT '标签名称',
    `create_time` DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表';

INSERT INTO `blog_tag` (`name`) VALUES
('Java'), ('SpringBoot'), ('Vue'), ('MySQL'), ('Redis'), ('Docker'), ('随笔');

-- ------------------------------------------------------------
-- 4. 文章表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `blog_article`;
CREATE TABLE `blog_article` (
    `id`            BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`       BIGINT        NOT NULL               COMMENT '作者 ID',
    `category_id`   BIGINT                DEFAULT NULL   COMMENT '分类 ID',
    `title`         VARCHAR(200)  NOT NULL               COMMENT '文章标题',
    `content`       LONGTEXT      NOT NULL               COMMENT 'Markdown 正文',
    `summary`       VARCHAR(500)          DEFAULT NULL   COMMENT '摘要',
    `cover_img`     VARCHAR(255)          DEFAULT NULL   COMMENT '封面图 URL',
    `status`        TINYINT       NOT NULL DEFAULT 0     COMMENT '状态：0草稿 / 1已发布 / 2已下线',
    `is_top`        TINYINT       NOT NULL DEFAULT 0     COMMENT '是否置顶：0否 / 1是',
    `view_count`    INT           NOT NULL DEFAULT 0     COMMENT '浏览量',
    `comment_count` INT           NOT NULL DEFAULT 0     COMMENT '评论数',
    `like_count`    INT           NOT NULL DEFAULT 0     COMMENT '点赞数',
    `create_time`   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP
                                      ON UPDATE CURRENT_TIMESTAMP   COMMENT '更新时间',
    `deleted`       TINYINT       NOT NULL DEFAULT 0     COMMENT '逻辑删除：0正常 / 1删除',
    PRIMARY KEY (`id`),
    KEY `idx_category` (`category_id`),
    KEY `idx_status`   (`status`),
    KEY `idx_user`     (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章表';

INSERT INTO `blog_article`
    (`user_id`, `category_id`, `title`, `content`, `summary`, `status`, `is_top`)
VALUES
(1, 1,
 'SpringBoot + MyBatis-Plus 快速入门',
 '## 简介\n\nSpringBoot 是目前最流行的 Java Web 框架之一，MyBatis-Plus 在 MyBatis 基础上做了增强，极大提升开发效率。\n\n## 依赖配置\n\n```xml\n<dependency>\n    <groupId>com.baomidou</groupId>\n    <artifactId>mybatis-plus-boot-starter</artifactId>\n    <version>3.5.3.1</version>\n</dependency>\n```\n\n## 实体类\n\n```java\n@Data\n@TableName("user")\npublic class User {\n    @TableId(type = IdType.AUTO)\n    private Long id;\n    private String name;\n    private Integer age;\n}\n```\n\n## Service 层\n\n直接继承 `IService<T>` 即可获得基础 CRUD 方法，无需自己编写 SQL。',
 'SpringBoot + MyBatis-Plus 的快速入门教程，包含依赖配置、实体类编写和 Service 层使用。',
 1, 1),
(1, 1,
 'Redis 缓存实战：从入门到进阶',
 '## 为什么使用 Redis\n\nRedis 是一个高性能的内存数据库，读写速度可达 10 万次/秒。在博客系统中，我们使用 Redis 缓存文章浏览量，避免频繁写库。\n\n## SpringBoot 集成 Redis\n\n```yaml\nspring:\n  redis:\n    host: localhost\n    port: 6379\n```\n\n## 实战：浏览量计数\n\n```java\n// 每次访问文章时，将浏览量写入 Redis\nredisTemplate.opsForValue().increment("article:view:" + id, 1);\n\n// 定时任务将 Redis 数据同步到 MySQL\n@Scheduled(cron = "0 * * * * ?")\npublic void syncViewCount() { ... }\n```',
 'Redis 在博客系统中的实战应用，包括 SpringBoot 集成、浏览量缓存和定时同步。',
 1, 0),
(1, 2,
 '程序员的日常：那些让你哭笑不得的 Bug',
 '## 序言\n\n每个程序员都有过深夜 debug 的经历。今天分享几个让我印象深刻的 Bug。\n\n## 1. 空指针异常\n\n最经典的 `NullPointerException`。解决方案：养成使用 `Optional` 的好习惯。\n\n## 2. 时区问题\n\n服务器时区和数据库时区不一致导致时间差 8 小时。解决方案：统一设置 `serverTimezone=Asia/Shanghai`。\n\n## 3. 缓存穿透\n\n大量请求查询不存在的数据，直接打穿数据库。解决方案：布隆过滤器 + 空值缓存。\n\n## 总结\n\n做好日志记录，出了问题不慌。',
 '分享几个程序员日常工作中遇到的经典 Bug 及其解决方案。',
 1, 0);

-- ------------------------------------------------------------
-- 5. 文章-标签 关联表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `blog_article_tag`;
CREATE TABLE `blog_article_tag` (
    `article_id` BIGINT NOT NULL COMMENT '文章 ID',
    `tag_id`     BIGINT NOT NULL COMMENT '标签 ID',
    PRIMARY KEY (`article_id`, `tag_id`),
    KEY `idx_tag` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章-标签关联表';

-- 文章1 → Java, SpringBoot, MySQL
INSERT INTO `blog_article_tag` VALUES (1, 1), (1, 2), (1, 4);
-- 文章2 → Java, SpringBoot, Redis
INSERT INTO `blog_article_tag` VALUES (2, 1), (2, 2), (2, 5);
-- 文章3 → 随笔
INSERT INTO `blog_article_tag` VALUES (3, 7);

-- ------------------------------------------------------------
-- 6. 评论表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `article_id`  BIGINT       NOT NULL               COMMENT '所属文章 ID',
    `user_id`     BIGINT                DEFAULT NULL   COMMENT '评论用户 ID（游客为 NULL）',
    `parent_id`   BIGINT                DEFAULT NULL   COMMENT '父评论 ID（根评论为 NULL）',
    `nickname`    VARCHAR(50)  NOT NULL               COMMENT '评论者昵称',
    `email`       VARCHAR(100)          DEFAULT NULL   COMMENT '评论者邮箱',
    `content`     TEXT         NOT NULL               COMMENT '评论内容（已过 XSS 过滤）',
    `status`      TINYINT      NOT NULL DEFAULT 0     COMMENT '状态：0待审核 / 1已通过 / 2已拒绝',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_article` (`article_id`),
    KEY `idx_parent`  (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

INSERT INTO `blog_comment` (`article_id`, `nickname`, `email`, `content`, `status`) VALUES
(1, '张三', 'zhangsan@example.com', '写得很好，对入门很有帮助！', 1),
(1, '李四', 'lisi@example.com',     'MyBatis-Plus 确实很方便，感谢分享。', 1),
(2, '王五', 'wangwu@example.com',   'Redis 缓存这块讲得很清楚，已收藏。', 1);

-- ------------------------------------------------------------
-- 7. 友情链接表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `blog_friend_link`;
CREATE TABLE `blog_friend_link` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        VARCHAR(100) NOT NULL               COMMENT '网站名称',
    `url`         VARCHAR(255) NOT NULL               COMMENT '网站 URL',
    `avatar`      VARCHAR(255)          DEFAULT NULL   COMMENT '网站图标 URL',
    `description` VARCHAR(200)          DEFAULT NULL   COMMENT '网站描述',
    `status`      TINYINT      NOT NULL DEFAULT 0     COMMENT '状态：0待审核 / 1已通过',
    `sort`        INT          NOT NULL DEFAULT 0     COMMENT '排序权重',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='友情链接表';

INSERT INTO `blog_friend_link` (`name`, `url`, `description`, `status`, `sort`) VALUES
('Spring 官网',   'https://spring.io',          'The source for modern Java',           1, 10),
('MyBatis-Plus',  'https://baomidou.com',        '为简化开发而生',                       1, 9),
('Vue.js',        'https://vuejs.org',           '渐进式 JavaScript 框架',              1, 8),
('廖雪峰的官方网站', 'https://www.liaoxuefeng.com', '专注于 Java/Python/JS 等技术教程',   1, 7);

-- ------------------------------------------------------------
-- 8. 系统配置表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `blog_config`;
CREATE TABLE `blog_config` (
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `config_key`   VARCHAR(50)  NOT NULL               COMMENT '配置键',
    `config_value` TEXT         NOT NULL               COMMENT '配置值',
    `description`  VARCHAR(200)          DEFAULT NULL   COMMENT '配置说明',
    `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
                                    ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

INSERT INTO `blog_config` (`config_key`, `config_value`, `description`) VALUES
('blog_name',      '我的技术博客',                   '博客名称'),
('blog_subtitle',  '记录技术，分享生活',              '博客副标题'),
('blog_author',    '博主',                           '博主名称'),
('blog_avatar',    '',                               '博主头像 URL'),
('blog_intro',     '一个热爱技术的程序员',            '博主简介'),
('icp_number',     '',                               'ICP 备案号'),
('github_url',     '',                               'GitHub 主页链接'),
('about_content',  '# 关于我\n\n这里是关于页面的内容。', '关于页面 Markdown 内容');
