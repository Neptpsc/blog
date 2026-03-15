package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.config.JwtConfig;
import com.blog.constant.RedisConstants;
import com.blog.constant.SystemConstants;
import com.blog.domain.User;
import com.blog.dto.LoginDTO;
import com.blog.exception.BusinessException;
import com.blog.mapper.UserMapper;
import com.blog.service.AuthService;
import com.blog.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 认证 Service 实现
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public LoginVO login(LoginDTO dto) {
        // 查询用户
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, dto.getUsername())
                .eq(User::getDeleted, SystemConstants.NOT_DELETED));
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(401, "用户名或密码错误");
        }
        if (SystemConstants.USER_STATUS_DISABLED.equals(user.getStatus())) {
            throw new BusinessException(403, "账号已被禁用，请联系管理员");
        }

        // 生成 Token
        String token = jwtConfig.generateToken(user.getId(), user.getUsername(), user.getRole());

        LoginVO vo = new LoginVO();
        vo.setToken(token);
        vo.setUserId(user.getId());
        vo.setNickname(user.getNickname());
        vo.setAvatar(user.getAvatar());
        vo.setRole(user.getRole());
        return vo;
    }

    @Override
    public void logout(String token) {
        if (token == null || !jwtConfig.validateToken(token)) {
            return;
        }
        // 将 Token 存入黑名单，有效期与 Token 过期时间一致
        String blacklistKey = RedisConstants.JWT_BLACKLIST + token;
        redisTemplate.opsForValue().set(
                blacklistKey, 1, jwtConfig.getExpiration(), TimeUnit.MILLISECONDS);
    }
}
