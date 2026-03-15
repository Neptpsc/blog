package com.blog.controller;

import com.blog.domain.FriendLink;
import com.blog.result.Result;
import com.blog.service.FriendLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 友链接口
 */
@RestController
@RequiredArgsConstructor
public class FriendLinkController {

    private final FriendLinkService friendLinkService;

    /**
     * 前台：获取已通过的友链
     */
    @GetMapping("/api/friend-links")
    public Result<List<FriendLink>> listApprovedLinks() {
        return Result.success(friendLinkService.listApprovedLinks());
    }

    /**
     * 后台：新增友链
     */
    @PostMapping("/api/admin/friend-links")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> addLink(@RequestBody FriendLink link) {
        link.setId(null); // 确保新增时不携带 id
        friendLinkService.addOrUpdateLink(link);
        return Result.success();
    }

    @PutMapping("/api/admin/friend-links")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> updateLink(@RequestBody FriendLink link) {
        friendLinkService.addOrUpdateLink(link);
        return Result.success();
    }

    /**
     * 后台：删除友链
     */
    @DeleteMapping("/api/admin/friend-links/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Void> deleteLink(@PathVariable Long id) {
        friendLinkService.deleteLink(id);
        return Result.success();
    }
}
