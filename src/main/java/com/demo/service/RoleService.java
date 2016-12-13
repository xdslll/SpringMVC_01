package com.demo.service;

import com.demo.model.EnfordSystemMenu;
import com.demo.model.EnfordSystemRole;
import com.demo.model.EnfordSystemRoleMenu;

import java.util.List;

/**
 * @author xiads
 * @date 16/2/2
 */
public interface RoleService {

    List<EnfordSystemRole> getRoles();

    List<EnfordSystemMenu> getMenusByRoleId(int roleId);

    int addRole(EnfordSystemRole role);

    int updateRole(EnfordSystemRole role);

    int deleteRole(int roleId);

    int addRoleMenu(EnfordSystemRoleMenu roleMenu);

    int updateRoleMenu(EnfordSystemRoleMenu roleMenu);

    int deleteRoleMenu(int roleMenuId);

    int maxRoleId();

    int addRoleMenu(int roleId, int[] menuIds);

    int updateRoleMenu(int roleId, int[] menuIds);

    int deleteRoleMenuByRoldId(int roleId);

    EnfordSystemRole getRole(int roleId);

}
