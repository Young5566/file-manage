package com.young.filemanage.service;

import com.young.filemanage.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/16 9:34
 */
public interface UserService {
    public int addUser(User user) throws Exception;
    public User getByUsername (String username) throws Exception;
    public int updateUser (User user) throws Exception;
    public List<User> getUsers() throws Exception;
    public int deleteUser(String username) throws Exception;
    public Map login(User user) throws Exception;
}
