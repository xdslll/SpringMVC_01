package com.demo.service;

import com.demo.model.EnfordProductOrganization;

import java.util.List;

/**
 * @author xiads
 * @date 16/2/1
 */
public interface OrgService {

    List<EnfordProductOrganization> getOrgs();

    int addOrg(EnfordProductOrganization org);

    int updateOrg(EnfordProductOrganization org);

    int deleteOrg(int orgId);
}
