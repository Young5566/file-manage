package com.young.filemanage.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/17 12:55
 */
public class File {
    private String uuid;
    private String name;
    private String groupName;
    private String remoteFileId;
    private String storageIp;
    private Long size;
    private String fileUrl;
    private Date createTime;
    private String type;
    private String uploader;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRemoteFileId() {
        return remoteFileId;
    }

    public void setRemoteFileId(String remoteFiled) {
        this.remoteFileId = remoteFiled;
    }

    public String getStorageIp() {
        return storageIp;
    }

    public void setStorageIp(String storageIp) {
        this.storageIp = storageIp;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
}
