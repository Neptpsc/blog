package com.blog.config;

import com.blog.filter.JwtAuthenticationFilter;
import com.blog.handler.AccessDeniedHandlerImpl;
import com.blog.handler.AuthenticationEntryPointImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 安全配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;
    private final AccessDeniedHandlerImpl accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 保护：本应用为无状态 RESTful API，使用 JWT Bearer Token 认证。
            // 认证凭证通过 Authorization 请求头显式传递，浏览器不会自动发送，
            // 且已禁用 Cookie/Session（STATELESS），CSRF 攻击路径不存在，可安全禁用。
            // 注意：若将来增加 Cookie 认证端点，必须重新评估此配置。
            .csrf().disable()
            // 禁用 Session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // 配置跨域
            .cors()
            .and()
            // 配置请求授权规则
            .authorizeRequests()
                // 公开接口（前台访问）
                .antMatchers(HttpMethod.GET,
                        "/api/articles",
                        "/api/articles/**",
                        "/api/categories",
                        "/api/tags",
                        "/api/archives",
                        "/api/config/**",
                        "/api/friend-links",
                        "/api/search"
                ).permitAll()
                // 登录注册
                .antMatchers("/api/auth/login", "/api/auth/register").permitAll()
                // 提交评论（允许游客操作）
                .antMatchers(HttpMethod.POST, "/api/comments").permitAll()
                // 后台管理接口需要 ADMIN 角色
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                // 其余接口需要认证
                .anyRequest().authenticated()
            .and()
            // 异常处理
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
            .and()
            // JWT 过滤器置于 UsernamePasswordAuthenticationFilter 之前
            .addFilterBefore(jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
