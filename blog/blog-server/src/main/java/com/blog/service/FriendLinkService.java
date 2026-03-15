package com.blog.service;

import com.blog.domain.FriendLink;

import java.util.List;

/**
 * 友链 Service 接口
 */
public interface FriendLinkService {

    /** 前台：获取已通过的友链 */
    List<FriendLink> listApprovedLinks();

    void addOrUpdateLink(FriendLink link);

    void deleteLink(Long id);
}
