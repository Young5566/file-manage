package com.young.filemanage.service.impl;

import com.young.filemanage.entity.User;
import com.young.filemanage.exception.YoungException;
import com.young.filemanage.mapper.UserMapper;
import com.young.filemanage.service.UserService;
import com.young.filemanage.utils.JWT;
import com.young.filemanage.utils.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/16 9:34
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) throws Exception {
        User findUser = userMapper.findByUsername(user.getUsername());
        if(findUser != null){
            throw new YoungException(ResultEnum.EXITED, "该用户名已存在");
        }
        return userMapper.insert(user.getUuid(), user.getUsername(), user.getPassword());
    }

    @Override
    public User getByUsername(String username) throws Exception {
        User user = userMapper.findByUsername(username);
        if(user == null){
            throw new YoungException(ResultEnum.NOT_FOUND, "该用户不存在");
        }
        return user;
    }

    @Override
    public int updateUser(User user) throws Exception {
        User findUser  = userMapper.findByUsername(user.getUsername());
        if(findUser == null){
            throw new YoungException(ResultEnum.NOT_FOUND, "该用户不存在");
        }
        Integer updateResult = userMapper.updatePassword(user.getUsername(), user.getPassword());
        if(updateResult != 1){
            throw new YoungException(ResultEnum.SQL_ERROR, "修改用户失败");
        }
        return updateResult;
    }

    @Override
    public List<User> getUsers() throws Exception {
        return userMapper.getUsers();
    }

    @Override
    public int deleteUser(String username) throws Exception {
        User findUser = userMapper.findByUsername(username);
        if(findUser == null){
            throw new YoungException(ResultEnum.NOT_FOUND, "该用户不存在");
        }
        return userMapper.deleteUser(username);
    }

    /**
     * 登陆
     * @param user 登陆信息
     * @return 返回token， username， uuid
     * @throws Exception 异常
     */
    @Override
    public Map login(User user) throws Exception {
        User findUser = userMapper.findByUsername(user.getUsername());
        Map resultMap = new HashMap();
        if(findUser == null){
            throw new YoungException(ResultEnum.NOT_FOUND, "该用户不存在");
        }
        if(!findUser.getPassword().equals(user.getPassword())){
           throw new YoungException(ResultEnum.LOGIN_ERROR, "密码错误");
        }
        resultMap.put("uuid", findUser.getUuid());
        resultMap.put("token", JWT.createToken(findUser));
        resultMap.put("username", user.getUsername());
        return resultMap;
    }


}
