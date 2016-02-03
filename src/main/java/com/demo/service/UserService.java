package com.demo.service;

import com.demo.model.EnfordSystemUser;

import java.util.List;

/**
 * @author xiads
 * @date 16/2/1
 */
public interface UserService {

    EnfordSystemUser login(String username, String password);

    List<EnfordSystemUser> getUsers();

    int addUser(EnfordSystemUser user);

    int updateUser(EnfordSystemUser user);

    int deleteUser(int userId);
}
