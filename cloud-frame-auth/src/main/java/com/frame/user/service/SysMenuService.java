package com.frame.user.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.boot.base.enums.YesNo;
import com.frame.user.constant.ModuleKeyConstant;
import com.frame.user.entity.SysMenu;
import com.frame.user.entity.SysMenuModule;
import com.frame.user.entity.SysModule;
import com.frame.user.entity.SysRole;
import com.frame.user.mapper.SysMenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysMenuService extends ServiceImpl<SysMenuMapper, SysMenu> {

    @Autowired
    private SysMenuModuleService sysMenuModuleService;
    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private SysRoleService sysRoleService;

    public SysMenu find(String menuCode, YesNo useable) {
        QueryWrapper query = new QueryWrapper<SysMenu>().eq("code", menuCode);
        if (useable != null) {
            query.eq("useable", useable.name());
        }
        return getOne(query);
    }

    public List<SysMenu> find(Collection menuCodes, YesNo useable) {
        QueryWrapper query = new QueryWrapper<SysMenu>().in("code", menuCodes);
        if (useable != null) {
            query.eq("useable", useable.name());
        }
        return list(query);
    }

    public List<SysMenu> findByModuleCode(String moduleCode, YesNo useable) {
        List<SysMenuModule> sysMenuModules = Optional.ofNullable(sysMenuModuleService.findByModuleCode(moduleCode))
                .orElse(Collections.emptyList());
        Set<String> menuCodes = sysMenuModules.stream().map(SysMenuModule::getMenuCode).collect(Collectors.toSet());
        return find(menuCodes, useable);
    }

    public List<SysMenu> findByModuleCode(Collection moduleCodes, YesNo useable) {
        List<SysMenuModule> sysMenuModules = Optional.ofNullable(sysMenuModuleService.findByModuleCode(moduleCodes))
                .orElse(Collections.emptyList());
        Set<String> menuCodes = sysMenuModules.stream().map(SysMenuModule::getMenuCode).collect(Collectors.toSet());
        return find(menuCodes, useable);
    }

    public List<SysMenu> findByRoleCode(String roleCode, YesNo useable) {
        List<SysModule> sysModules = Optional.ofNullable(sysModuleService.findByRoleCode(roleCode, useable))
                .orElse(Collections.emptyList());
        Set<String> moduleCodes = sysModules.stream().map(SysModule::getCode).collect(Collectors.toSet());
        if (moduleCodes != null && !moduleCodes.isEmpty()) {
            return findByModuleCode(moduleCodes, useable);
        } else {
            return null;
        }
    }

    public List<SysMenu> findByRoleCode(Collection roleCodes, YesNo useable) {
        List<SysModule> sysModules = Optional.ofNullable(sysModuleService.findByRoleCode(roleCodes, useable))
                .orElse(Collections.emptyList());
        Set<String> moduleCodes = sysModules.stream().map(SysModule::getCode).collect(Collectors.toSet());
        if (moduleCodes != null && !moduleCodes.isEmpty()) {
            return findByModuleCode(moduleCodes, useable);
        } else {
            return null;
        }
    }

    public List<SysMenu> findByUserId(String userId, YesNo useable) {
        List<SysRole> roles = sysRoleService.findByUserId(userId, useable);
        if (roles == null || roles.isEmpty()) {
            return null;
        }
        return findByRoleCode(roles.stream().map(SysRole::getCode).collect(Collectors.toList()), useable);
    }

    @Cacheable(value = "user:menuJson:userId", key = "#userId + '_' + #useable")
    public JSONArray findMenuJSONByUsername(String userId, YesNo useable) {
        List<SysMenu> listMenu = findByUserId(userId, useable);
        return getModulesJSON(ModuleKeyConstant.MODULE_CODE_ROOT, listMenu);
    }

    public JSONArray getModulesJSON(String parentCode, Collection<SysMenu> menus) {
        if (menus == null || !StringUtils.hasText(parentCode)) {
            return null;
        }

        // 拷贝一份，防止删除的时候会删除节点
        List<SysMenu> list = new ArrayList<>(menus);
        // 排序
        sort(list);

        // 剩余节点，用于递归
        List<SysMenu> surplusModules = new ArrayList<>(menus);
        // 遍历添加
        JSONArray childrenModulesJSON = new JSONArray();
        for (SysMenu module : list) {
            // 父节点
            if (parentCode.equals(module.getParentCode())) {
                // 添加
                JSONObject moduleJSON = new JSONObject();
//                moduleJSON.put("id", module.getId());
//                moduleJSON.put("optimistic", module.getOptimistic());
                moduleJSON.put("typeCode", module.getTypeCode());
                moduleJSON.put("parentCode", parentCode);
                moduleJSON.put("parentCodes", module.getParentCodes());
                moduleJSON.put("code", module.getCode());
                moduleJSON.put("name", module.getName());
                moduleJSON.put("useable", module.getUseable());
                moduleJSON.put("url", module.getUrl());
                moduleJSON.put("icon", module.getIcon());
                moduleJSON.put("orders", module.getOrders());
                moduleJSON.put("description", module.getDescription());
                // 删除掉已经添加的节点
                surplusModules.remove(module);
                // 添加子节点
                moduleJSON.put("children", getModulesJSON(module.getCode(), surplusModules));

                boolean exit = false;
                for (int i = 0; i < childrenModulesJSON.size(); i++) {
                    JSONObject childModuleJSON = childrenModulesJSON.getJSONObject(i);
                    String childCode = (childModuleJSON == null ? null : childModuleJSON.getString("code"));
                    if (!StringUtils.hasText(childCode) && childCode.equals(module.getCode())) {
                        exit = true;
                        break;
                    }
                }
                if (!exit) {
                    childrenModulesJSON.add(moduleJSON);
                }
            }
        }
        return childrenModulesJSON;
    }

    /**
     * 排序
     *
     * @param menus
     */
    public void sort(List<SysMenu> menus) {
        Collections.sort(menus, new Comparator<SysMenu>() {
            @Override
            public int compare(SysMenu object1, SysMenu object2) {
                if (null == object1 || null == object2 || null == object1.getOrders() || null == object2.getOrders()) {
                    return 0;
                } else {
                    return object1.getOrders() - object2.getOrders();
                }
            }
        });
    }
}
