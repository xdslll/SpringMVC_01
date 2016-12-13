package com.demo.service;

import com.demo.model.EnfordSystemUser;

import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/1
 */
public interface UserService {

    EnfordSystemUser login(String username, String password);

    List<EnfordSystemUser> getUsers();

    int addUser(EnfordSystemUser user);

    int addUser2(EnfordSystemUser user);

    int updateUser(EnfordSystemUser user);

    int deleteUser(int userId);

    EnfordSystemUser getUser(int id);

    List<EnfordSystemUser> getUserByParam(Map<String, Object> param);

    int count();
}
