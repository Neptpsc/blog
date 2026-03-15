package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.domain.BlogConfig;
import com.blog.mapper.BlogConfigMapper;
import com.blog.service.BlogConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统配置 Service 实现
 */
@Service
@RequiredArgsConstructor
public class BlogConfigServiceImpl implements BlogConfigService {

    private final BlogConfigMapper blogConfigMapper;

    @Override
    public Map<String, String> getAllConfig() {
        List<BlogConfig> configs = blogConfigMapper.selectList(null);
        return configs.stream()
                .collect(Collectors.toMap(BlogConfig::getConfigKey, BlogConfig::getConfigValue));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveConfig(Map<String, String> configMap) {
        configMap.forEach((key, value) -> {
            BlogConfig existing = blogConfigMapper.selectOne(
                    new LambdaQueryWrapper<BlogConfig>()
                            .eq(BlogConfig::getConfigKey, key));
            if (existing != null) {
                existing.setConfigValue(value);
                blogConfigMapper.updateById(existing);
            } else {
                BlogConfig config = new BlogConfig();
                config.setConfigKey(key);
                config.setConfigValue(value);
                blogConfigMapper.insert(config);
            }
        });
    }
}
