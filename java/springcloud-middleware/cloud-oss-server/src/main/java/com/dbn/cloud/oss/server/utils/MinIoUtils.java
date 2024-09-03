package com.dbn.cloud.oss.server.utils;

import com.dbn.cloud.oss.server.config.MinIoProperties;
import com.dbn.cloud.platform.exception.AppException;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.InputStream;

@Slf4j
@Component("minIoUtils")
@Configuration
@EnableConfigurationProperties({MinIoProperties.class})
public class MinIoUtils {

    @Resource(name = "minIoProperties")
    private MinIoProperties minIo;

    public MinIoUtils(@Qualifier("minIoProperties") MinIoProperties minIo) {
        this.minIo = minIo;
    }

    private MinioClient minioClient;

    /**
     * 初始化minio配置
     */
    @PostConstruct
    public void init() {
        try {
            minioClient = new MinioClient.Builder().endpoint(minIo.getEndpoint())
                    .credentials(minIo.getAccessKey(), minIo.getSecretKey())
                    .build();
        } catch (Exception e) {
            log.error("文件服务器-连接失败" + e);
            throw new AppException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "文件服务器-连接失败");
        }
    }

    /**
     * 判断 bucket是否存在
     *
     * @param bucketName:桶名
     * @return boolean
     */
    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("文件服务器-检查文件夹是否存在失败, bucketName={}, exception={}", bucketName, e);
            throw new AppException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "文件服务器-检查文件夹是否存在失败");
        }
    }

    /**
     * 创建 bucket
     *
     * @param bucketName:桶名
     */
    public void makeBucket(String bucketName) {
        try {
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            ;
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            log.error("文件服务器-创建文件夹失败, bucketName={}, exception={}", bucketName, e);
            throw new AppException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "文件服务器-创建文件夹失败");
        }
    }


    /**
     * bucket police
     *
     * @param bucketName:桶名
     */
    public void bucketPolice(String bucketName) {
        try {
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExist) {
                throw new AppException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "文件服务器-文件夹不存在");
            }

            StringBuilder builder = new StringBuilder();
            builder.append("{\n");
            builder.append("    \"Statement\": [\n");
//            builder.append("        {\n");
//            builder.append("            \"Action\": [\n");
//            builder.append("                \"s3:GetBucketLocation\",\n");
//            builder.append("                \"s3:ListBucket\"\n");
//            builder.append("            ],\n");
//            builder.append("            \"Effect\": \"Allow\",\n");
//            builder.append("            \"Principal\": \"*\",\n");
//            builder.append("            \"Resource\": \"arn:aws:s3:::my-bucketname\"\n");
//            builder.append("        },\n");
            builder.append("        {\n");
            builder.append("            \"Action\": \"s3:GetObject\",\n");
            builder.append("            \"Effect\": \"Allow\",\n");
            builder.append("            \"Principal\": \"*\",\n");
            builder.append("            \"Resource\": \"arn:aws:s3:::" + bucketName + "/*\"\n");
            builder.append("        }\n");
            builder.append("    ],\n");
            builder.append("    \"Version\": \"2012-10-17\"\n");
            builder.append("}\n");

            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).config(builder.toString()).build());

        } catch (Exception e) {
            log.error("文件服务器-创建文件夹访问策略失败, bucketName={}, exception={}", bucketName, e);
            throw new AppException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "文件服务器-创建文件夹访问策略失败");
        }
    }

    /**
     * 文件上传
     *
     * @param inputStream String
     * @param bucketName  String
     * @param contentType String
     * @param objectName  String
     * @return Boolean
     */
    public Boolean putObject(InputStream inputStream, String bucketName, String contentType, String objectName) {
        try {
            if (!bucketExists(bucketName)) {
                makeBucket(bucketName);
            }

            // 使用putObject上传一个文件到存储桶中
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).contentType(contentType).
                    stream(inputStream, -1, 1024 * 1024 * 10).build());
            return true;
        } catch (Exception e) {
            log.error("文件服务器-上传文件失败, bucketName={}, contentType={}, objectName={}, exception={}", bucketName, contentType, objectName, e);
            throw new AppException(String.valueOf(500), "文件服务器-上传文件失败");
        }
    }

    /**
     * 删除文件
     *
     * @param bucketName String
     * @param objectName String
     */
    public void removeObject(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            log.error("文件服务器-删除文件失败, bucketName={}, objectName={}, exception={}", bucketName, objectName, e);
            throw new AppException(String.valueOf(500), "文件服务器-删除文件失败");
        }
    }

    /**
     * 获取文件（内容）
     *
     * @param bucketName String
     * @param objectName String
     */
    public GetObjectResponse getObject(String bucketName, String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            log.error("文件服务器-获取文件失败, bucketName={}, objectName={}, exception={}", bucketName, objectName, e);
            throw new AppException(String.valueOf(500), "文件服务器-获取文件失败");
        }
    }

    /**
     * 获取文件7 * 365天有效期url
     *
     * @param bucketName String
     * @param objectName String
     * @return String
     */
    public String getFileUrl(String bucketName, String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName).
                    method(Method.GET)
                    .expiry(60 * 60 * 24 * 7)
                    .build());
        } catch (Exception e) {
            log.error("文件服务器-获取文件url失败, bucketName={}, objectName={}, exception={}", bucketName, objectName, e);
            throw new AppException(String.valueOf(500), "文件服务器-获取文件url失败");
        }
    }


}
