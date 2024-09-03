package com.dbn.cloud.oss.server.service.impl;

import com.dbn.cloud.oss.client.domain.vo.response.PutFileRespVo;
import com.dbn.cloud.oss.server.config.MinIoProperties;
import com.dbn.cloud.oss.server.service.IFileService;
import com.dbn.cloud.oss.server.utils.MinIoUtils;
import io.minio.GetObjectResponse;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;


@Slf4j
@RefreshScope
@Service("fileService")
public class FileServiceImpl implements IFileService {

    @Resource(name = "minIoUtils")
    private MinIoUtils minIoUtils;

    @Resource(name = "minIoProperties")
    private MinIoProperties minIo;

    @Value(value = "${minio.outside:}")
    private String outside;

    @Override
    public PutFileRespVo putFile(MultipartFile file, String bucketName) throws IOException {
        //获取文件的完整名称，包括文件名称+文件拓展名
        String oldFileName = file.getOriginalFilename();
        //获取文件的类型，注意是文件的类型，不是文件的拓展名
        String fileContentType = file.getContentType();

        assert oldFileName != null;

        //存储的文件名：UUID + 后缀
        String fileName = UUID.randomUUID() + ((oldFileName.lastIndexOf(".") > 0) ? oldFileName.substring(oldFileName.lastIndexOf(".")) : "");

        minIoUtils.putObject(file.getInputStream(), bucketName, fileContentType, fileName);
        String fileUrl = "/" + bucketName + "/" + fileName;

        PutFileRespVo respVo = new PutFileRespVo();
        respVo.setOldFileName(oldFileName);
        respVo.setNewFileName(fileName);
        respVo.setFileUrl(fileUrl);
        respVo.setFileSize(file.getSize());

        respVo = matchOutside(respVo);

        try {
            minIoUtils.bucketPolice(bucketName);
        } catch (Exception e) {
            log.error("FileServiceImpl.putFile:minIoUtils.bucketPolice异常");
        }
        return respVo;
    }

    @Override
    public void removeFile(String bucketName, String fileName) {
        minIoUtils.removeObject(bucketName, fileName);
    }

    @Override
    public void getFile(String bucketName, String fileName) {

        HttpServletResponse custResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try (GetObjectResponse response = minIoUtils.getObject(bucketName, fileName)) {
            byte[] buf = new byte[1024];
            int len;
            custResponse.setCharacterEncoding("utf-8");
            //设置强制下载不打开
            custResponse.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            custResponse.setContentType("application/force-download");
            try (ServletOutputStream stream = custResponse.getOutputStream()) {

                while ((len = response.read(buf)) != -1) {
                    stream.write(buf, 0, len);
                }
                stream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFileUrl(String bucketName, String fileName) {
        return minIoUtils.getFileUrl(bucketName, fileName);
    }

    @Override
    public PutFileRespVo putCompressImgs(MultipartFile file, String bucketName, Double scale) throws IOException {
        //获取文件的完整名称，包括文件名称+文件拓展名
        String oldFileName = file.getOriginalFilename();
        //获取文件的类型，注意是文件的类型，不是文件的拓展名
        String fileContentType = file.getContentType();

        assert oldFileName != null;
        //存储的文件名：时间戳+后缀
        String fileName = System.currentTimeMillis() + ((oldFileName.lastIndexOf(".") > 0) ? oldFileName.substring(oldFileName.lastIndexOf(".")) : "");

        minIoUtils.putObject(file.getInputStream(), bucketName, fileContentType, fileName);
        String fileUrl = "/" + bucketName + "/" + fileName;

        PutFileRespVo respVo = new PutFileRespVo();
        respVo.setOldFileName(oldFileName);
        respVo.setNewFileName(fileName);
        respVo.setFileUrl(fileUrl);

        //保存缩略图
        int index = fileName.lastIndexOf(".");
        //    log.info("index1={},2={}",fileName.substring(0,index-1) ,fileName.substring(index));
        String compressFileName = fileName.substring(0, index) + "_compress" + fileName.substring(index);

        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        if (scale == null || scale <= 0) {
            scale = 0.2;
        }
        Thumbnails.of(file.getInputStream()).scale(scale).toOutputStream(swapStream);//按比例缩小
        //  Thumbnails.of(file.getInputStream()).size(1060 ,800 ).toOutputStream(swapStream);//按比例缩小
        minIoUtils.putObject(new ByteArrayInputStream(swapStream.toByteArray()), bucketName, fileContentType, compressFileName);
        String compressFileUrl = "/" + bucketName + "/" + compressFileName;
        respVo.setCompressFileUrl(compressFileUrl);

        respVo = matchOutside(respVo);

        try {
            minIoUtils.bucketPolice(bucketName);
        } catch (Exception e) {
            log.error("FileServiceImpl.putFile:minIoUtils.bucketPolice异常");
        }
        return respVo;
    }

    public PutFileRespVo matchOutside(PutFileRespVo respVo) {
        if (outside != null && !"".equals(outside)) {
            if (respVo.getCompressFileUrl() != null) {
                respVo.setOutside(outside + respVo.getCompressFileUrl());
            } else {
                respVo.setOutside(outside + respVo.getFileUrl());
            }
        }
        return respVo;
    }

}
