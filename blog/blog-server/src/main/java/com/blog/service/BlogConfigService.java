package com.blog.service;

import java.util.Map;

/**
 * 系统配置 Service 接口
 */
public interface BlogConfigService {

    /**
     * 获取所有配置（以 Map 形式返回）
     */
    Map<String, String> getAllConfig();

    /**
     * 批量保存配置
     */
    void saveConfig(Map<String, String> configMap);
}
