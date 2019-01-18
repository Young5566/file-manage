package com.young.filemanage.mapper;

import com.young.filemanage.entity.User;
import org.apache.ibatis.annotations.*;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/16 17:58
 */
public interface UserMapper {

    @Insert("INSERT INTO USERS(UUID, USERNAME, PASSWORD) VALUES (#{uuid}, #{username}, #{password})")
    public int insert (@Param("uuid") String uuid, @Param("username") String username, @Param("password") String password) throws SQLException;

    @Select("SELECT * FROM USERS WHERE USERNAME = #{username}")
    public User findByUsername (@Param("username") String username) throws SQLException;

    @Select("SELECT UUID,USERNAME FROM USERS")
    public List<User> getUsers() throws SQLException;

    @Update("UPDATE USERS SET PASSWORD = #{password} WHERE USERNAME = #{username}")
    public int updatePassword(@Param("username") String username, @Param("password") String password) throws SQLException;

    @Delete("delete from users where username = #{username}")
    public int deleteUser(@Param("username") String username) throws SQLException;
}
