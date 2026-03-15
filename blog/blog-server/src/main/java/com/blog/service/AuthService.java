package com.blog.service;

import com.blog.dto.LoginDTO;
import com.blog.vo.LoginVO;

/**
 * 认证 Service 接口
 */
public interface AuthService {

    /**
     * 登录，返回 JWT Token 和用户信息
     */
    LoginVO login(LoginDTO dto);

    /**
     * 登出（将 Token 加入 Redis 黑名单）
     */
    void logout(String token);
}
