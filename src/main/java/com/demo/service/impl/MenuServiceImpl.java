package com.demo.service.impl;

import com.demo.dao.EnfordSystemMenuMapper;
import com.demo.exception.BusinessException;
import com.demo.model.EnfordSystemMenu;
import com.demo.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiads
 * @date 16/1/30
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

    @Resource
    private EnfordSystemMenuMapper menuMapper;

    @Override
    public List<EnfordSystemMenu> queryRootMenu() {
        List<EnfordSystemMenu> menuList = queryMenu();
        List<EnfordSystemMenu> rootMenu = menuMapper.select(-1);
        rootMenu.get(0).setChildren(menuList);
        return rootMenu;
    }

    @Override
    public List<EnfordSystemMenu> queryMenu() {
        List<EnfordSystemMenu> menuList = menuMapper.select(0);
        for (EnfordSystemMenu menu : menuList) {
            int id = menu.getId();
            List<EnfordSystemMenu> children = menuMapper.select(id);
            menu.setChildren(children);
        }
        return menuList;
    }

    @Override
    @Transactional
    public void addMenu(EnfordSystemMenu menu) throws BusinessException {
        menuMapper.insert(menu);
    }

    @Override
    public void deleteMenu(int id) throws BusinessException {
        menuMapper.delete(id);
    }

    @Override
    public void updateMenu(EnfordSystemMenu menu) throws BusinessException {
        menuMapper.update(menu);
    }
}
