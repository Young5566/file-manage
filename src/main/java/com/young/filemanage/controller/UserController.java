package com.young.filemanage.controller;

import com.young.filemanage.entity.Result;
import com.young.filemanage.entity.User;
import com.young.filemanage.service.UserService;
import com.young.filemanage.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/16 9:32
 */

@CrossOrigin
@RestController
@Validated
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 添加用户
     * @param user 用户信息
     * @param bindingResult 参数验证错误时的返回结果
     * @return 返回是否添加成功
     * @throws Exception 异常
     */
    @PostMapping("/add")
    public Result addUser(@Valid @RequestBody User user,BindingResult bindingResult) throws Exception {
        String uuid = Utils.getUUID();
        user.setUuid(uuid);
        Integer addUserResult = userService.addUser(user);
        return new Result<>(addUserResult);
    }

    /**
     * 获取全部用户
     * @return 全部用户的信息
     * @throws Exception 异常
     */
    @GetMapping("/getAll")
    public Result getUsers() throws Exception {
        return new Result<>(userService.getUsers());
    }

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 返回用户信息
     * @throws Exception 异常
     */
    @GetMapping("/{username}")
    public Result findByUsername(@PathVariable("username") String username) throws Exception {
        return new Result<>(userService.getByUsername(username));
    }

    /**
     * 更新用户信息
     * @param user 更新的信息
     * @return 返回是否更新成功信息
     * @throws Exception 异常
     */
    @PostMapping("/update")
    public Result updateUser(@RequestBody User user) throws Exception {
        return new Result<>(userService.updateUser(user));
    }

    /**
     * 根据用户名删除用户
     * @param username 用户名
     * @return 返回是否删除成功信息
     * @throws Exception 异常
     */
    @DeleteMapping("/delete/{username}")
    public Result deleteUser(@PathVariable("username") String username) throws Exception {
        return new Result<>(userService.deleteUser(username));
    }

    /**
     * 登陆
     * @param user 登陆信息
     * @param bindingResult 参数验证，错误时返回的结果
     * @return 返回token，username, uuid
     * @throws Exception 异常
     */
    @PostMapping("/login")
    public Result login(@Valid @RequestBody User user,BindingResult bindingResult) throws Exception {
        return new Result<>(userService.login(user));
    }
}
