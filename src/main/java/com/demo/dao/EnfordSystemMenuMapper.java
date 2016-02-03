package com.demo.dao;

import com.demo.model.EnfordSystemMenu;

import java.util.List;

/**
 * @author xiads
 * @date 16/1/30
 */
public interface EnfordSystemMenuMapper {

    List<EnfordSystemMenu> select(int parentId);

    EnfordSystemMenu selectById(int id);

    int insert(EnfordSystemMenu menu);

    int delete(int id);

    int update(EnfordSystemMenu menu);
}
