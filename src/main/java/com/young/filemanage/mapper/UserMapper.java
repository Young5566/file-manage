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

@Mapper
public interface UserMapper {

    @Insert("insert into users(uuid, username, password) values (#{uuid}, #{username}, #{password})")
    public int insert (@Param("uuid") String uuid, @Param("username") String username, @Param("password") String password) throws SQLException;

    @Select("select * from users where username = #{username}")
    public User findByUsername (@Param("username") String username) throws SQLException;

    @Select("select uuid ,username from users")
    public List<User> getUsers() throws SQLException;

    @Update("update users set password = #{password} WHERE USERNAME = #{username}")
    public int updatePassword(@Param("username") String username, @Param("password") String password) throws SQLException;

    @Delete("delete from users where username = #{username}")
    public int deleteUser(@Param("username") String username) throws SQLException;
}
