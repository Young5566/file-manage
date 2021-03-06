package com.young.filemanage.service.impl;

import com.young.filemanage.entity.File;
import com.young.filemanage.entity.User;
import com.young.filemanage.exception.YoungException;
import com.young.filemanage.mapper.FileMapper;
import com.young.filemanage.mapper.UserMapper;
import com.young.filemanage.service.FileService;
import com.young.filemanage.utils.ResultEnum;
import com.young.filemanage.utils.Utils;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.*;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/17 20:15
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private UserMapper userMapper;

    // 获取配置文件路径

//    private static final String conf_filename = System.getProperty("user.dir") + "\\src\\main\\resources\\fastdfs_client.conf";
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private static String storageIp = null;
    private static StorageClient storageClient = null;
    private static Properties props = new Properties();

    // 获取storageClient
    static {
        try {
            String conf_filename = new ClassPathResource("fastdfs_client.conf").getFile().getAbsolutePath();
            InputStream in = new BufferedInputStream(new FileInputStream(conf_filename));
            props.load(in);
            String value = props.getProperty("tracker_server");
            storageIp = value.split(":")[0];
            ClientGlobal.init(conf_filename);
            // 连接
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = null;
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 上传文件
     * @param files 文件字节流
     * @param uploader 上传者
     * @param fileName 文件名字
     * @param contentType 文件类型
     * @return 是否上传成功
     * @throws Exception 异常
     */
    @Override
    public File addFile(byte[] files, String uploader, String fileName, String contentType) throws Exception {
        // 验证该用户是否可以上传
        User findUser =userMapper.findByUsername(uploader);
        if(findUser == null){
            throw new YoungException(ResultEnum.NOT_FOUND, "该用户不可上传文件");
        }
        // 设置文件属性
        NameValuePair[] nvp = new NameValuePair[] {
                new NameValuePair("uploader", uploader),
                new NameValuePair("name", fileName),
                new NameValuePair("type", contentType.split("/")[0])
        };
        System.out.println();
        // 上传得到 groupName, remoteFileId
        String[] fileIds = storageClient.upload_appender_file(files, contentType.split("/")[1], nvp);
        // 得到文件的信息
        FileInfo fileInfo = storageClient.get_file_info(fileIds[0], fileIds[1]);
        // 生成文件对象
        File file = new File();
        file.setUuid(Utils.getUUID());
        file.setName(fileName);
        file.setGroupName(fileIds[0]);
        file.setRemoteFileId(fileIds[1]);
        file.setStorageIp(storageIp);
        file.setSize(fileInfo.getFileSize());
        String fileUrl = "http://" + storageIp + "/" + fileIds[0] + "/" + fileIds[1];
        file.setFileUrl(fileUrl);
        file.setCreateTime(fileInfo.getCreateTimestamp());
        file.setType(contentType);
        file.setUploader(uploader);
        Integer addResult = fileMapper.addFile(file);
        if(addResult != 1){
            throw new YoungException(ResultEnum.SQL_ERROR, "添加失败");
        }
        return file;
    }

    /**
     * 获取文件信息
     * @param uuid 请求目标文件的uuid
     * @return 返回文件信息
     * @throws Exception 异常
     */
    @Override
    public File getByUuid(String uuid) throws Exception {
        File findFile = fileMapper.getByUuid(uuid);
        if(findFile == null){
            throw new YoungException(ResultEnum.NOT_FOUND, "该文件不存在");
        }
        return fileMapper.getByUuid(uuid);
    }

    /**
     * 删除文件
     * @param uuid 删除目标文件的uuid
     * @param uploader 操作人
     * @return 返回是否删除成功
     * @throws Exception 异常
     */
    @Override
    public int deleteFile(String uuid, String uploader) throws Exception {
        // 看该用户是否可以删除
        User findUser =userMapper.findByUsername(uploader);
        if(findUser == null){
            throw new YoungException(ResultEnum.NOT_FOUND, "该用户不可删除文件");
        }
        // 该文件是否存在
        File findFile = fileMapper.getByUuid(uuid);
        if(findFile == null){
            throw new YoungException(ResultEnum.NOT_FOUND, "该文件不存在");
        }
        // 删除服务器文件
        int deleteResult = storageClient.delete_file(findFile.getGroupName(), findFile.getRemoteFileId());
        if(deleteResult != 0){
            throw new YoungException(ResultEnum.ERROR, "删除失败");
        }
        // 删除数据库记录
        Integer deleteSqlResult = fileMapper.deleteFile(uuid);
        if(deleteSqlResult != 1){
            throw new YoungException(ResultEnum.SQL_ERROR, "文件删除失败");
        }
        return deleteResult;
    }

    /**
     * 修改文件信息
     * @param file 修改的文件信息
     * @return 是否修改成功
     * @throws Exception 异常
     */
    @Override
    public int updateFile(File file) throws Exception {
        File findFile = fileMapper.getByUuid(file.getUuid());
        if(findFile == null){
            throw new YoungException(ResultEnum.NOT_FOUND, "该文件不存在");
        }
        Integer updateResult = fileMapper.updateFile(file);
        if(updateResult != 1){
            throw new YoungException(ResultEnum.SQL_ERROR, "修改文件失败");
        }
        return updateResult;
    }

    /**
     * 下载文件
     * @param uuid 下载目标文件的uuid
     * @return 是否下载成功
     * @throws Exception 异常
     */
    @Override
    public int downloadFile(String uuid) throws Exception {
        File findFile = fileMapper.getByUuid(uuid);
        if(findFile == null){
            throw new YoungException(ResultEnum.NOT_FOUND, "该文件不存在");
        }
        // 读取桌面路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        java.io.File com=fsv.getHomeDirectory(); //读取桌面路径
        byte[] b = storageClient.download_file(findFile.getGroupName(), findFile.getRemoteFileId());
        if (b == null) {
            throw new YoungException(ResultEnum.NOT_FOUND, "该文件不存在");
        }
        try {
            // 将文件写到桌面
            IOUtils.write(b, new FileOutputStream(com.getPath() + "\\" + UUID.randomUUID().toString()
                    + "." + findFile.getRemoteFileId().split("\\.")[1]));
            return 1;
        } catch (IOException e){
            throw  new YoungException(ResultEnum.ERROR, "下载失败");
        }
    }

    /**
     * 获取全部文件（分页）
     * @param page 请求页数
     * @return 文件信息
     * @throws Exception 异常
     */
    @Override
    public Map getAllFile(Integer page) throws Exception {
        Map resultMap = new HashMap();
        resultMap.put("files", fileMapper.getAllFile(10, (page - 1)*10));
        resultMap.put("count", fileMapper.getFilesCount());
        return resultMap;
    }
}
