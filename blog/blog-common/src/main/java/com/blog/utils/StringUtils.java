package com.blog.utils;

import cn.hutool.core.util.StrUtil;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 截取摘要：若 content 超过 maxLen 则截断并加省略号
     */
    public static String buildSummary(String content, int maxLen) {
        if (StrUtil.isBlank(content)) {
            return "";
        }
        // 去除 Markdown 语法符号（简单处理）
        String plain = content.replaceAll("#+\\s", "")
                .replaceAll("\\*{1,2}([^*]+)\\*{1,2}", "$1")
                .replaceAll("!?\\[([^]]*)]\\([^)]*\\)", "$1")
                .replaceAll("`{1,3}[^`]*`{1,3}", "")
                .replaceAll("\\r?\\n", " ")
                .trim();
        if (plain.length() <= maxLen) {
            return plain;
        }
        return plain.substring(0, maxLen) + "...";
    }

    private StringUtils() {
    }
}
