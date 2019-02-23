package com.young.filemanage.service;

import com.young.filemanage.entity.File;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/17 20:09
 */
public interface FileService {
    public File addFile(byte[] files, String uploader, String fileName, String contentType) throws Exception;
    public File getByUuid(String uuid) throws Exception;
    public int deleteFile(String uuid, String uploader) throws Exception;
    public int updateFile(File file) throws Exception;
    public int downloadFile(String uuid) throws Exception;
    public Map getAllFile(Integer page) throws Exception;
}
