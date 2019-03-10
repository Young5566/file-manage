//package com.young.filemanage;
//
//import org.apache.commons.io.IOUtils;
//import org.csource.common.MyException;
//import org.csource.common.NameValuePair;
//import org.csource.fastdfs.*;
//import org.junit.Test;
//
//import javax.swing.filechooser.FileSystemView;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.UUID;
//
///**
// * @Author: Young
// * @QQ: 403353323
// * @Date: 2019/1/17 14:31
// */
//public class UploadTest {
//    public String conf_filename = "D:\\JAVA\\JavaFile\\File-manage\\src\\main\\resources\\fastdfs_client.conf";
//    public String local_filename = "C:\\Users\\acer\\Desktop\\something\\Only you\\haha.jpg";
//
//    @Test
//    public void testUpload(){
//        try {
//            // 初始化配置
//            ClientGlobal.init(conf_filename);
//            // 连接
//            TrackerClient trackerClient = new TrackerClient();
//            TrackerServer trackerServer = trackerClient.getConnection();
//            StorageServer storageServer = null;
//            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//
//            // 设置文件属性
//            NameValuePair[] nvp = new NameValuePair[] {
//                    new NameValuePair("name", "Young"),
//                    new NameValuePair("type", "picture")
//            };
//
//            // 上传
//            String[] fileIds = storageClient.upload_appender_file(local_filename, "jpg", nvp);
////            String[] fileIds = storageClient.upload_appender_file();
//            for(String str: fileIds){
//                System.out.println(str);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (MyException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testDownload() throws IOException, MyException {
//        ClientGlobal.init(conf_filename);
//        TrackerClient trackerClient = new TrackerClient();
//        TrackerServer trackerServer = trackerClient.getConnection();
//        StorageServer storageServer = null;
//        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//
//        FileSystemView fsv = FileSystemView.getFileSystemView();
//        File com=fsv.getHomeDirectory(); //读取桌面路径
//        byte[] b = storageClient.download_file("group1", "M00/00/01/wKgAfVxALHyENfBsAAAAAPBzjhw703.jpg");
//        System.out.println(b);
//        IOUtils.write(b, new FileOutputStream(com.getPath() + "\\" + UUID.randomUUID().toString() +".jpg"));
//    }
//
//    @Test
//    public void testGetFileInfo() throws IOException, MyException {
//        ClientGlobal.init(conf_filename);
//
//        TrackerClient tracker = new TrackerClient();
//        TrackerServer trackerServer = tracker.getConnection();
//        StorageServer storageServer = null;
//
//        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//        FileInfo fileInfo = storageClient.get_file_info("group1", "M00/00/01/wKgAfVxALHyENfBsAAAAAPBzjhw703.jpg");
//        System.out.println(fileInfo.toString());
//
//    }
//
//    @Test
//    public void testGetFileMetaData() throws IOException, MyException {
//        ClientGlobal.init(conf_filename);
//
//        TrackerClient tracker = new TrackerClient();
//        TrackerServer trackerServer = tracker.getConnection();
//        StorageServer storageServer = null;
//
//        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//        NameValuePair[] nameValuePairs = storageClient.get_metadata("group1", "M00/00/01/wKgAfVxALHyENfBsAAAAAPBzjhw703.jpg");
//        for(NameValuePair nameValuePair : nameValuePairs){
//            System.out.println(nameValuePair.getName() + ":" + nameValuePair.getValue());
//        }
//    }
//
//    @Test
//    public void testDeleteFile() throws Exception{
//        ClientGlobal.init(conf_filename);
//
//        TrackerClient tracker = new TrackerClient();
//        TrackerServer trackerServer = tracker.getConnection();
//        StorageServer storageServer = null;
//
//        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//
//        int deleteResult = storageClient.delete_file("group1", "M00/00/01/wKgAfVxBPnCEGpwyAAAAAFmioiw127.png");
//        System.out.println(deleteResult);
//    }
//}
