package com.demo.service;

import com.demo.exception.BusinessException;
import com.demo.model.EnfordSystemMenu;

import java.util.List;

/**
 * @author xiads
 * @date 16/1/30
 */
public interface MenuService {

    List<EnfordSystemMenu> queryRootMenu() throws BusinessException;

    List<EnfordSystemMenu> queryMenu() throws BusinessException;

    void addMenu(EnfordSystemMenu menu) throws BusinessException;

    void deleteMenu(int id) throws BusinessException;

    void updateMenu(EnfordSystemMenu menu) throws BusinessException;
}
