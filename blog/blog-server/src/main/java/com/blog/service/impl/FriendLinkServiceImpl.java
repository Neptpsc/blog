package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.domain.FriendLink;
import com.blog.mapper.FriendLinkMapper;
import com.blog.service.FriendLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 友链 Service 实现
 */
@Service
@RequiredArgsConstructor
public class FriendLinkServiceImpl implements FriendLinkService {

    private final FriendLinkMapper friendLinkMapper;

    @Override
    public List<FriendLink> listApprovedLinks() {
        return friendLinkMapper.selectList(
                new LambdaQueryWrapper<FriendLink>()
                        .eq(FriendLink::getStatus, 1)
                        .orderByDesc(FriendLink::getSort));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateLink(FriendLink link) {
        if (link.getId() == null) {
            if (link.getSort() == null) link.setSort(0);
            friendLinkMapper.insert(link);
        } else {
            friendLinkMapper.updateById(link);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLink(Long id) {
        friendLinkMapper.deleteById(id);
    }
}
