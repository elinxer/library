package com.dbn.cloud.oss.server.service;

import com.dbn.cloud.oss.client.domain.vo.response.PutFileRespVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface IFileService {

    /**
     * 上传文件
     *
     * @param files
     * @param bucketName
     * @return
     */
    PutFileRespVo putFile(MultipartFile files, String bucketName) throws IOException;

    /**
     * 删除文件
     *
     * @param bucketName
     * @param fileName
     */
    void removeFile(String bucketName, String fileName);

    /**
     * 获取文件
     *
     * @param bucketName
     * @param fileName
     */
    void getFile(String bucketName, String fileName);

    /**
     * 获取文件url
     *
     * @param bucketName
     * @param fileName
     * @return
     */
    String getFileUrl(String bucketName, String fileName);

    PutFileRespVo putCompressImgs(MultipartFile file, String toLowerCase, Double scale) throws IOException;
}
