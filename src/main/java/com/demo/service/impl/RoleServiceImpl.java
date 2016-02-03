package com.demo.service.impl;

import com.demo.dao.EnfordSystemMenuMapper;
import com.demo.dao.EnfordSystemRoleMapper;
import com.demo.dao.EnfordSystemRoleMenuMapper;
import com.demo.model.EnfordSystemMenu;
import com.demo.model.EnfordSystemRole;
import com.demo.model.EnfordSystemRoleMenu;
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

    @Override
    public List<EnfordSystemRole> getRoles() {
        return roleMapper.select();
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
        return roleMapper.insert(role);
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
                roleMenuMapper.insert(roleMenu);
                count++;
            }
        }
        return count;
    }

    @Override
    public int deleteRoleMenuByRoldId(int roleId) {
        return roleMenuMapper.deleteByRoleId(roleId);
    }

}
