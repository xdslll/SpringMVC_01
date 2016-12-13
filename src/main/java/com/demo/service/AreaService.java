package com.demo.service;

import com.demo.model.EnfordProductArea;
import com.demo.model.EnfordProductDepartment;
import com.demo.model.EnfordSystemUser;

import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/1/30
 */
public interface AreaService {

    /**
     * 查询某一个市调清单下所有参与区域的清单
     * @param resId
     * @return
     */
    List<EnfordProductArea> getAreaTree(int resId);

    /**
     * 查询某一个区域下所有部门的明细
     * @param areaId
     * @return
     */
    EnfordProductArea getAreaDeptTree(int areaId);

    List<EnfordProductDepartment> getAreaDept(int resId, String areaName);

    List<EnfordProductArea> getAreaTree2(int resId, EnfordSystemUser user);

    List<EnfordProductArea> getArea(Map<String, Object> param);

    int addArea(EnfordProductArea area);

    int updateArea(EnfordProductArea area);

    int deleteArea(int id);

    EnfordProductArea getAreaDeptTreeByKeyword(int areaId, String keyword, int page, int pageSize);

    int countByKeyword(int areaId, String keyword);
}
