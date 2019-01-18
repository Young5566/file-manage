package com.young.filemanage.mapper;

import com.young.filemanage.entity.File;
import org.apache.ibatis.jdbc.SQL;

import java.sql.SQLException;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/17 18:48
 */
public class FileSqlProvider {
    /**
     * 生成添加文件的SQL语句
     * @param file 文件信息
     * @return sql语句
     * @throws SQLException 异常
     */
    public String getAddFileSql(File file) throws SQLException{
        return new SQL(){{
            INSERT_INTO("files");
            INTO_COLUMNS("uuid", "name", "groupName", "remoteFileId", "storageIp", "size", "fileUrl", "createTime", "type", "uploader");
            INTO_VALUES("#{uuid}", "#{name}", "#{groupName}", "#{remoteFileId}", "#{storageIp}", "#{size}", "#{fileUrl}",
                    "#{createTime}","#{type}", "#{uploader}");
        }}.toString();
    }

    /**
     * 生成修改文件的SQL语句
     * @param file 修改的文件信息
     * @return sql语句
     * @throws SQLException 异常
     */
    public String getFileUpdateSql(File file) throws SQLException{
        return new SQL() {{
            UPDATE("files");
            if(file.getName() != null){
                SET("name = #{name}");
            }
            if(file.getGroupName() != null){
                SET("groupName = #{groupName}");
            }
            if(file.getRemoteFileId() != null){
                SET("remoteFileId = #{remoteFileId}");
            }
            if(file.getStorageIp() != null){
                SET("storageIp = #{storageIp}");
            }
            if(file.getSize() != null){
                SET("size = #{size}");
            }
            if(file.getFileUrl() != null){
                SET("fileUrl = #{fileUrl}");
            }
            if(file.getCreateTime() != null){
                SET("createTime = #{createTime}");
            }
            if(file.getType() != null){
                SET("type = #{type}");
            }
            if(file.getUploader() != null){
                SET("uploader = #{uploader}");
            }
            WHERE("uuid = #{uuid}");
        }}.toString();
    }
}
