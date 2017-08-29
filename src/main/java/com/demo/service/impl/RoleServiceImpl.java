package com.demo.service.impl;

import com.demo.dao.EnfordSystemMenuMapper;
import com.demo.dao.EnfordSystemRoleMapper;
import com.demo.dao.EnfordSystemRoleMenuMapper;
import com.demo.model.EnfordProductArea;
import com.demo.model.EnfordSystemMenu;
import com.demo.model.EnfordSystemRole;
import com.demo.model.EnfordSystemRoleMenu;
import com.demo.service.AreaService;
import com.demo.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiads
 * @date 16/2/2
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    EnfordSystemRoleMapper roleMapper;

    @Resource
    EnfordSystemRoleMenuMapper roleMenuMapper;

    @Resource
    EnfordSystemMenuMapper menuMapper;

    @Resource
    AreaService areaService;

    @Override
    public List<EnfordSystemRole> getRoles() {
        List<EnfordSystemRole> roles = roleMapper.select();
        for (EnfordSystemRole role : roles) {
            String areaIds = role.getAreaId();
            String areaNames = "";
            if (areaIds.contains(",")) {
                String[] areaIdsStr = areaIds.split(",");
                for (int i = 0; i < areaIdsStr.length; i++) {
                    String areaIdStr = areaIdsStr[i];
                    int areaId = Integer.valueOf(areaIdStr);
                    String areaName = "";
                    if (areaId == -1) {
                        areaName = "未分配区域";
                    } else if (areaId == 0) {
                        areaName = "全部区域";
                    } else {
                        EnfordProductArea area = areaService.getAreaDeptTree(areaId);
                        if (area != null) {
                            areaName = area.getName();
                        } else {
                            areaName = "未分配区域";
                        }
                    }
                    if (i == areaIdsStr.length - 1) {
                        areaNames += areaName;
                    } else {
                        areaNames += areaName + ",";
                    }
                }
                role.setAreaName(areaNames);
            } else {
                int areaId = Integer.valueOf(areaIds);
                if (areaId == -1) {
                    role.setAreaName("未分配区域");
                } else if (areaId == 0) {
                    role.setAreaName("全部区域");
                } else {
                    EnfordProductArea area = areaService.getAreaDeptTree(areaId);
                    if (area != null) {
                        role.setAreaName(area.getName());
                    } else {
                        role.setAreaName("未分配区域");
                    }
                }
            }
            /*if (areaId == -1) {
                role.setAreaName("未分配区域");
            } else if (areaId == 0) {
                role.setAreaName("全部区域");
            } else {
                EnfordProductArea area = areaService.getAreaDeptTree(areaId);
                if (area != null) {
                    role.setAreaName(area.getName());
                } else {
                    role.setAreaName("未分配区域");
                }
            }*/
        }
        return roles;
    }

    @Transactional
    @Override
    public List<EnfordSystemMenu> getMenusByRoleId(int roleId) {
        List<EnfordSystemMenu> menus = new ArrayList<EnfordSystemMenu>();
        //查询权限和菜单对应关系
        List<EnfordSystemRoleMenu> roleMenus =  roleMenuMapper.selectByRoleId(roleId);
        for (int i = 0; i < roleMenus.size(); i++) {
            EnfordSystemRoleMenu roleMenu = roleMenus.get(i);
            //获取菜单id
            int menuId = roleMenu.getMenuId();
            //获取菜单对象
            EnfordSystemMenu menu = menuMapper.selectById(menuId);
            if (menu == null) {
                System.out.println("menu is null");
            }
            //获取父菜单的id
            int parentId = menu.getParent();
            //获取父菜单
            EnfordSystemMenu parentMenu = menuMapper.selectById(parentId);
            //如果父菜单是顶级菜单,则获取并加入列表
            if (parentMenu.getParent() == 0) {
                if (!menus.contains(parentMenu)) {
                    menus.add(parentMenu);
                } else {
                    int index = menus.indexOf(parentMenu);
                    parentMenu = menus.get(index);
                }
                parentMenu.getChildren().add(menu);
            }
        }
        return menus;
    }

    @Override
    public int addRole(EnfordSystemRole role) {
        return roleMapper.insertSelective(role);
    }

    @Override
    public int updateRole(EnfordSystemRole role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int deleteRole(int roleId) {
        return roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public int addRoleMenu(EnfordSystemRoleMenu roleMenu) {
        return roleMenuMapper.insert(roleMenu);
    }

    @Override
    public int updateRoleMenu(EnfordSystemRoleMenu roleMenu) {
        return roleMenuMapper.updateByPrimaryKeySelective(roleMenu);
    }

    @Override
    public int deleteRoleMenu(int roleMenuId) {
        return roleMenuMapper.deleteByPrimaryKey(roleMenuId);
    }

    @Override
    public int maxRoleId() {
        return roleMapper.max();
    }

    @Transactional
    @Override
    public int addRoleMenu(int roleId, int[] menuIds) {
        int count = 0;
        for (int i = 0; i < menuIds.length; i++) {
            //获取menu数据
            EnfordSystemMenu menu = menuMapper.selectById(menuIds[i]);
            //判断menu id是否存在,且不为根节点
            if (menu != null && menu.getParent() != 0) {
                EnfordSystemRoleMenu roleMenu = new EnfordSystemRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuIds[i]);
                roleMenuMapper.insertSelective(roleMenu);
                count++;
            }
        }
        return count;
    }

    @Transactional
    @Override
    public int updateRoleMenu(int roleId, int[] menuIds) {
        int count = 0;
        for (int i = 0; i < menuIds.length; i++) {
            int menuId = menuIds[i];
            //获取menu数据
            EnfordSystemMenu menu = menuMapper.selectById(menuId);
            //判断menu id是否存在,且不为根节点
            if (menu != null && menu.getParent() != 0) {
                EnfordSystemRoleMenu roleMenu = new EnfordSystemRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                int ret = roleMenuMapper.insertSelective(roleMenu);
                count += ret;
            }
        }
        return count;
    }

    @Override
    public int deleteRoleMenuByRoldId(int roleId) {
        return roleMenuMapper.deleteByRoleId(roleId);
    }

    @Override
    public EnfordSystemRole getRole(int roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

}
