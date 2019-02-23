package com.young.filemanage.controller;

import com.young.filemanage.entity.File;
import com.young.filemanage.entity.Result;
import com.young.filemanage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/17 20:32
 */
@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     * @param file 上传的文件头
     * @param uploader 上传者，数据库中必须有该用户
     * @return 返回文件信息
     * @throws Exception 异常
     */
    @PostMapping("/upload/{uploader}")
    public Result uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("uploader") String uploader) throws Exception{
        String contentType = file.getContentType();   //图片文件类型
        String fileName = file.getOriginalFilename();  //图片名字
        byte[] files = file.getBytes();
        return new Result<>(fileService.addFile(files, uploader, fileName, contentType));
    }

    /**
     * 修改文件信息
     * @param file 文件信息
     * @return 返回是否成功
     * @throws Exception 异常
     */
    @PostMapping("/update")
    public Result updateFile(@RequestBody File file) throws Exception{
        return new Result<>(fileService.updateFile(file));
    }

    /**
     * 下载文件
     * @param uuid 下载的目标文件 uuid
     * @return 返回是否下载成功
     * @throws Exception 异常
     */
    @GetMapping("/download/{uuid}")
    public Result downloadFile(@PathVariable("uuid") String uuid) throws Exception{
        return new Result<>(fileService.downloadFile(uuid));
    }

    /**
     * 删除文件
     * @param uploader 操作人
     * @param uuid 删除的目标文件 uuid
     * @return 返回是否删除成功
     * @throws Exception 异常
     */
    @DeleteMapping("/delete/{uploader}/{uuid}")
    public Result deleteFile(@PathVariable("uploader") String uploader, @PathVariable("uuid") String uuid) throws Exception {
        return new Result<>(fileService.deleteFile(uuid, uploader));
    }

    /**
     * 获取文件信息
     * @param uuid 获取的目标文件 uuid
     * @return 返回文件信息
     * @throws Exception 异常
     */
    @GetMapping("/{uuid}")
    public Result getByUuid(@PathVariable("uuid") String uuid) throws Exception{
        return new Result<>(fileService.getByUuid(uuid));
    }

    @GetMapping("/getAll")
    public Result getAll(@NotNull(message = "请输入页数") Integer page) throws Exception {
        return new Result<>(fileService.getAllFile(page));
    }
}
