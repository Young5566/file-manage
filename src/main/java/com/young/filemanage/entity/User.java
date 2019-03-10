package com.young.filemanage.entity;

//import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/16 9:34
 */
public class User {
    private String uuid;

    @NotNull(message = "用户名不可为空")
    private String username;

    @NotNull(message = "密码不可为空")
    private String password;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
