package com.frame.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.boot.base.enums.DataStatus;
import com.frame.boot.base.enums.UserStatus;
import com.frame.boot.base.enums.YesNo;
import com.frame.boot.base.utils.EmptyUtil;
import com.frame.user.client.BossAuthUtil;
import com.frame.user.entity.SysFunction;
import com.frame.user.entity.SysModule;
import com.frame.user.entity.SysRole;
import com.frame.user.entity.SysUser;
import com.frame.user.enums.AuthMsgResult;
import com.frame.user.exception.AuthException;
import com.frame.user.mapper.SysUserMapper;
import com.frame.user.properties.UserProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    @Autowired
    private UserProperties userProperties;

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private SysFunctionService sysFunctionService;

    /**
     * 校验用户状态
     *
     * @param sysUser
     */
    public void validUserStatus(SysUser sysUser) {
        // 校验用户状态
        UserStatus userStatus = sysUser.getUserStatus();
        Optional.ofNullable(userStatus).orElseThrow(() -> new AuthException(AuthMsgResult.USER_STATUS_ERROR));
        switch (userStatus) {
            case DELETED:
                throw new AuthException(AuthMsgResult.USER_DELETED_ERROR);
            case EXPIRED:
                throw new AuthException(AuthMsgResult.USER_EXPIRED_ERROR);
            case DISABLED:
                throw new AuthException(AuthMsgResult.USER_DISABLED_ERROR);
            case LOCKED:
                throw new AuthException(AuthMsgResult.USER_LOCKED_ERROR);
        }
    }

    /**
     * 获取角色
     *
     * @param userId
     * @return
     */
    @Cacheable(value = "user:roles", key = "#userId", condition = "#userId != null", unless = "#result == null")
    public Set<String> getRoles(String userId) {
        List<SysRole> sysRoles = Optional.ofNullable(
                sysRoleService.findByUserId(userId, YesNo.Y)
        ).orElse(Collections.emptyList());

        return sysRoles.stream()
                .map(SysRole::getCode)
                .collect(Collectors.toSet());
    }

    /**
     * 获取权限
     *
     * @param userId
     * @return
     */
    @Cacheable(value = "user:permissions", key = "#userId", condition = "#userId != null", unless = "#result == null")
    public Set<String> getPermissions(String userId) {
        // 获取角色，如果角色为空，则权限为空
        Set<String> roleCodes = getRoles(userId);
        if (roleCodes == null || roleCodes.isEmpty()) {
            return null;
        }

        // 获取模块，如果模块为空，则权限为空
        List<SysModule> sysModules = Optional.ofNullable(
                sysModuleService.findByRoleCode(roleCodes, YesNo.Y)
        ).orElse(Collections.emptyList());
        if (sysModules == null || !sysModules.isEmpty()) {
            return null;
        }

        // 查询可用权限并返回
        List<SysFunction> sysFunctions = Optional.ofNullable(
                sysFunctionService.findByModuleCode(sysModules.stream().map(SysModule::getCode).collect(Collectors.toList()), YesNo.Y)
        ).orElse(Collections.emptyList());
        return sysFunctions.stream().map(SysFunction::getCode).collect(Collectors.toSet());
    }

    @Cacheable(value = "user:user", key = "#username", condition = "#username != null", unless = "#result == null")
    public SysUser findByUsername(String username) {
        return getOne(new QueryWrapper<SysUser>()
                .eq("username", username)
                .eq("status", DataStatus.NORMAL.name()));
    }

    @Cacheable(value = "user:user", key = "#userId", condition = "#userId != null", unless = "#result == null")
    public SysUser findByUserId(String userId) {
        return getOne(new QueryWrapper<SysUser>()
                .eq("user_id", userId)
                .eq("status", DataStatus.NORMAL.name()));
    }

    @Cacheable(value = "user:user", key = "#source + ':' + #sourceId", condition = "#sourceId != null && #source != null", unless = "#result == null")
    public SysUser findBySource(String source, String sourceId) {
        return getOne(new QueryWrapper<SysUser>()
                .eq("user_source", source)
                .eq("source_id", sourceId)
                .eq("status", DataStatus.NORMAL.name()));
    }

    @Override
    public boolean save(SysUser entity) {
        entity.setUserId(UUID.randomUUID().toString());
        if (EmptyUtil.isEmpty(entity.getPassword())) {
            entity.setPassword(passwordEncoder.encode(userProperties.getDefaultPwd()));
        }
        entity.setCreateTime(new Date());
        entity.setCreateUser(BossAuthUtil.getUserId());
        return super.save(entity);
    }

    @Override
    public boolean updateById(SysUser entity) {
        entity.setUpdateTime(new Date());
        entity.setUpdateUser(BossAuthUtil.getUserId());
        return super.updateById(entity);
    }

    public boolean deleteById(Serializable id) {
        SysUser entity = getById(id);
        if (entity != null) {
            entity.setUserStatus(UserStatus.DELETED);
            entity.setUpdateTime(new Date());
            entity.setUpdateUser(BossAuthUtil.getUserId());
        }
        return super.updateById(entity);
    }

    public boolean deleteByIds(Collection<? extends Serializable> idList) {
        Collection<SysUser> users = listByIds(idList);
        if (users != null) {
            users.stream().forEach(entity -> {
                entity.setUserStatus(UserStatus.DELETED);
                entity.setUpdateTime(new Date());
                entity.setUpdateUser(BossAuthUtil.getUserId());
            });
            updateBatchById(users);
        }
        return true;
    }
}
