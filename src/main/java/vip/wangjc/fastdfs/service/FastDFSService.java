package vip.wangjc.fastdfs.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import vip.wangjc.fastdfs.auto.properties.FastDFSProperties;
import vip.wangjc.fastdfs.exception.FastDFSException;

import java.io.*;
import java.nio.charset.Charset;

/**
 * fastDFS服务
 * @author wangjc
 * @title: FastDFSService
 * @projectName wangjc-vip-fastdfs-starter
 * @date 2021/1/13 - 14:16
 */
public class FastDFSService {

    private final FastFileStorageClient fastFileStorageClient;

    private final FastDFSProperties fastDFSProperties;

    public FastDFSService(FastFileStorageClient fastFileStorageClient, FastDFSProperties fastDFSProperties){
        this.fastFileStorageClient = fastFileStorageClient;
        this.fastDFSProperties = fastDFSProperties;
    }

    /**
     * 获取fastDFS客户端连接
     * @return
     */
    public FastFileStorageClient getFastFileStorageClient(){
        return this.fastFileStorageClient;
    }

    /**
     * web文件上传
     * @param file
     * @return
     */
    public String uploadFile(MultipartFile file) throws FastDFSException {

        if(!this.checkSize(file.getSize()/1024)){
            throw new FastDFSException("文件大小超出配置项："+this.fastDFSProperties.getMaxSize()+"KB");
        }
        try {
            StorePath storePath = this.fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
            return this.getRequestAccessUrl(storePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 普通文件上传
     * @param file
     * @return
     * @throws FastDFSException
     */
    public String uploadFile(File file) throws FastDFSException {

        if(!this.checkSize(file.length()/1024)){
            throw new FastDFSException("文件大小超出配置项："+this.fastDFSProperties.getMaxSize()+"KB");
        }
        try {
            FileInputStream inputStream = new FileInputStream(file);
            StorePath path = fastFileStorageClient.uploadFile(inputStream, file.length(), FilenameUtils.getExtension(file.getName()), null);
            return this.getRequestAccessUrl(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 流文件上传
     * @param is
     * @param size
     * @param fileName
     * @return
     */
    public String uploadFile(InputStream is, long size, String fileName) throws FastDFSException {

        if(!this.checkSize(size/1024)){
            throw new FastDFSException("文件大小超出配置项："+this.fastDFSProperties.getMaxSize()+"KB");
        }
        StorePath path = fastFileStorageClient.uploadFile(is, size, fileName, null);
        return this.getRequestAccessUrl(path);
    }

    /**
     * 将一段文本文件写到fastDFS的服务器上
     * @param content:内容
     * @param fileExtension：扩展名
     * @return
     */
    public String writeFile(String content, String fileExtension){
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath path = fastFileStorageClient.uploadFile(stream, buff.length, fileExtension, null);
        return this.getRequestAccessUrl(path);
    }

    /**
     * 删除fastDFS文件
     * @param fileUrl
     * @return
     */
    public Boolean delete(String fileUrl){
        if(fileUrl == null || fileUrl == ""){
            return false;
        }
        StorePath storePath = StorePath.parseFromUrl(fileUrl);
        fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        return true;
    }

    /**
     * 判断文件大小是否合格
     * @param fileSize
     * @return
     */
    private Boolean checkSize(long fileSize){
        if(this.fastDFSProperties.getMaxSize() == null){
            return true;
        }
        if(this.fastDFSProperties.getMaxSize() >= fileSize ){
            return true;
        }
        return false;
    }

    /**
     * 获取全链接
     * @param storePath
     * @return
     */
    private String getRequestAccessUrl(StorePath storePath){
        StringBuffer sb = new StringBuffer("http://");
        sb.append(this.fastDFSProperties.getRequestHost());
        sb.append("/");
        sb.append(storePath.getFullPath());
        return sb.toString();
    }
}
