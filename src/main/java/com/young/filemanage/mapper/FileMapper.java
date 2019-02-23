package com.young.filemanage.mapper;

import com.young.filemanage.entity.File;
import org.apache.ibatis.annotations.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/17 16:54
 */

public interface FileMapper {

    /**
     * 添加文件，用InsertProvider提供SQL语句
     * @param file 文件信息
     * @return 是否添加成功
     * @throws Exception 数据库操作异常
     */
    @InsertProvider(type = FileSqlProvider.class, method = "getAddFileSql")
    @Options(keyProperty="uuid",keyColumn="uuid",useGeneratedKeys=true)
    public int addFile(File file) throws Exception;

    @Select("select * from files where uuid = #{uuid}")
    public File getByUuid(@Param("uuid") String uuid) throws SQLException;

    @Delete("delete from files where uuid = #{uuid}")
    public int deleteFile(@Param("uuid") String uuid) throws SQLException;

    /**
     *修改文件，用UpdateProvider 提供SQL语句
     * @param file 修改文件的信息
     * @return 是否修改成功
     * @throws SQLException 异常
     */
    @UpdateProvider(type = FileSqlProvider.class, method = "getFileUpdateSql")
    public int updateFile(File file) throws SQLException;

    @Select("select * from files limit #{pageSize} offset #{offset}")
    public List<File> getAllFile(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset) throws SQLException;

    @Select("select count(*) from files")
    public int getFilesCount() throws SQLException;
}
