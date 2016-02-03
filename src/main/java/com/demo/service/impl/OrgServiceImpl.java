package com.demo.service.impl;

import com.demo.dao.EnfordProductOrganizationMapper;
import com.demo.model.EnfordProductOrganization;
import com.demo.service.OrgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiads
 * @date 16/2/1
 */
@Service("orgService")
public class OrgServiceImpl implements OrgService {

    @Resource
    EnfordProductOrganizationMapper orgMapper;

    @Override
    public List<EnfordProductOrganization> getOrgs() {
        return orgMapper.select();
    }

    @Override
    public int addOrg(EnfordProductOrganization org) {
        return orgMapper.insert(org);
    }

    @Override
    public int updateOrg(EnfordProductOrganization org) {
        return orgMapper.updateByPrimaryKeySelective(org);
    }

    @Override
    public int deleteOrg(int orgId) {
        return orgMapper.deleteByPrimaryKey(orgId);
    }
}
