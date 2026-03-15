package com.blog.controller;

import com.blog.result.Result;
import com.blog.service.BlogConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统配置接口
 */
@RestController
@RequiredArgsConstructor
public class BlogConfigController {

    private final BlogConfigService blogConfigService;

    /**
     * 获取所有配置（前台/后台均可访问）
     */
    @GetMapping("/api/config/all")
    public Result<Map<String, String>> getAllConfig() {
        return Result.success(blogConfigService.getAllConfig());
    }

    /**
     * 后台：批量保存配置
     */
    @PutMapping("/api/admin/config")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> saveConfig(@RequestBody Map<String, String> configMap) {
        blogConfigService.saveConfig(configMap);
        return Result.success();
    }
}
