package com.dbn.cloud.oss.server.controller;

import com.dbn.cloud.oss.client.domain.vo.request.FileReqVo;
import com.dbn.cloud.oss.client.domain.vo.response.GetBatchFileUrlRespVo;
import com.dbn.cloud.oss.client.domain.vo.response.GetFileUrlRespVo;
import com.dbn.cloud.oss.client.domain.vo.response.PutFileRespVo;
import com.dbn.cloud.oss.client.domain.vo.response.RemoveFileRespVo;
import com.dbn.cloud.oss.server.service.IFileService;
import com.dbn.cloud.oss.server.utils.FileHahUtil;
import com.dbn.cloud.oss.server.utils.FileUploadUtils;
import com.dbn.cloud.oss.server.utils.MinIoUtils;
import com.dbn.cloud.oss.server.validation.FileValid;
import com.dbn.cloud.platform.exception.AppException;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import io.minio.GetObjectResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 网关要移除访问控制
 * <p>
 * http://api.zzkj.dbn.cn/api/api-oss/v1/file/getFileUrl
 *
 * @author elinx
 */
@Slf4j
@Api(value = "文件中心", tags = "文件中心")
@RestController
@RequestMapping("/v1/file")
public class FileController {

    @Resource(name = "fileService")
    private IFileService fileService;

    @Resource(name = "minIoUtils")
    private MinIoUtils minIoUtils;


    /**
     * 上传文件
     *
     * @param file MultipartFile
     * @return 文件url
     */
    @PostMapping(value = "/upload")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public ResponseMessage<PutFileRespVo> upload(@RequestParam("file") MultipartFile file) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String date = formatter.format(new Date());
        String bucketName = ("common-" + date).toLowerCase();
        if (!FileUploadUtils.checkSuffixLegal(file)) {
            throw new AppException("文件格式非法.");
        }
        PutFileRespVo respVo = fileService.putFile(file, bucketName);
        return ResponseMessage.ok(respVo);
    }

    /**
     * 上传文件
     *
     * @param file   MultipartFile
     * @param fileVo -> bucketName FileReqVo
     * @return 文件url
     */
    @PostMapping(value = "/putFile")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public ResponseMessage<PutFileRespVo> putFile(@RequestParam("file") MultipartFile file, @Valid FileReqVo fileVo) throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String date = formatter.format(new Date());

        String bucketName = fileVo.getBucketName() == null ? "common-" + date : fileVo.getBucketName();
        bucketName = bucketName.toLowerCase();

        FileValid.fileNameValid(bucketName);
        FileValid.fileNameValid(file.getOriginalFilename());

        if (!FileUploadUtils.checkSuffixLegal(file)) {
            throw new Exception();
        }

        PutFileRespVo respVo = fileService.putFile(file, bucketName);
        respVo.setHash("");

        if (fileVo.getHash() != null && fileVo.getHash() == 1) {
            // 检验hash
            try (GetObjectResponse response = minIoUtils.getObject(bucketName, respVo.getNewFileName())) {
                String hash = FileHahUtil.md5HashCode(response);
                respVo.setHash(hash);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseMessage.ok(respVo);
    }

    /**
     * 上传文件
     *
     * @param files  MultipartFile
     * @param fileVo -> bucketName
     * @return 文件url
     */
    @PostMapping(value = "/putFiles")
    @ApiOperation(value = "批量上传文件", notes = "批量上传文件")
    public ResponseMessage<List<PutFileRespVo>> putFiles(@RequestParam("file") MultipartFile[] files, @Valid FileReqVo fileVo) {
        FileValid.fileNameValid(fileVo.getBucketName());
        FileValid.fileNameValid(fileVo.getFileName());
        List<PutFileRespVo> respVoList = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                boolean legalFile = FileUploadUtils.checkSuffixLegal(file);
                if (!legalFile) {
                    throw new AppException(String.valueOf(500), "文件非法");
                }
                PutFileRespVo respVo = fileService.putFile(file, fileVo.getBucketName().toLowerCase());
                respVoList.add(respVo);
            } catch (Exception e) {
                log.error("文件上传失败, bucketName={}, fileName={}", fileVo.getBucketName(), fileVo.getFileName(), e);
            }
        }
        return ResponseMessage.ok(respVoList);
    }

    /**
     * 删除文件
     *
     * @param fileVo->bucketName, fileVo->fileName
     * @return 删除是否成功, true-成功 false-失败
     */
    @PostMapping(value = "/removeFile")
    @ApiOperation(value = "删除文件", notes = "删除文件")
    public ResponseMessage<RemoveFileRespVo> removeFile(@RequestBody @Valid FileReqVo fileVo) {
        FileValid.fileNameValid(fileVo.getBucketName());
        FileValid.fileNameValid(fileVo.getFileName());
        fileService.removeFile(fileVo.getBucketName().toLowerCase(), fileVo.getFileName().toLowerCase());
        RemoveFileRespVo respVo = new RemoveFileRespVo();
        respVo.setResult(true);
        return ResponseMessage.ok(respVo);
    }

    /**
     * 获取文件
     *
     * @param fileVo->bucketName, fileVo->fileName
     */
    @PostMapping(value = "/getFile")
    @ApiOperation(value = "获取文件", notes = "获取文件的文件流")
    public ResponseMessage<?> getFile(@RequestBody @Valid FileReqVo fileVo) {
        FileValid.fileNameValid(fileVo.getBucketName().toLowerCase());
        FileValid.fileNameValid(fileVo.getFileName());
        fileService.getFile(fileVo.getBucketName().toLowerCase(), fileVo.getFileName().toLowerCase());
        return ResponseMessage.ok();
    }

    /**
     * 获取文件url
     *
     * @param fileVo->bucketName, fileVo->fileName
     * @return 文件的url
     */
    @PostMapping(value = "/getFileUrl")
    @ApiOperation(value = "获取文件的url", notes = "获取文件7天有效期的url")
    public ResponseMessage<GetFileUrlRespVo> getFileUrl(@RequestBody @Valid FileReqVo fileVo) {
        FileValid.fileNameValid(fileVo.getBucketName());
        FileValid.fileNameValid(fileVo.getFileName());
        String fileUrl = fileService.getFileUrl(fileVo.getBucketName().toLowerCase(), fileVo.getFileName().toLowerCase());
        GetFileUrlRespVo respVo = new GetFileUrlRespVo();
        respVo.setFileUrl(fileUrl);
        return ResponseMessage.ok(respVo);
    }

    /**
     * 批量获取文件url
     *
     * @param fileVoList->bucketName, fileVo->fileName
     * @return 文件的url
     */
    @PostMapping(value = "/getBatchFileUrl")
    @ApiOperation(value = "批量获取文件的url", notes = "批量获取文件7天有效期的url")
    public ResponseMessage<List<GetBatchFileUrlRespVo>> getBatchFileUrl(@RequestBody @Valid List<FileReqVo> fileVoList) {
        List<GetBatchFileUrlRespVo> respVoList = new ArrayList<>();
        for (FileReqVo fileVo : fileVoList) {
            GetBatchFileUrlRespVo respVo = new GetBatchFileUrlRespVo();
            respVo.setBucketName(fileVo.getBucketName().toLowerCase());
            respVo.setFileName(fileVo.getFileName().toLowerCase());
            try {
                FileValid.fileNameValid(fileVo.getBucketName());
                FileValid.fileNameValid(fileVo.getFileName());

                String fileUrl = fileService.getFileUrl(fileVo.getBucketName().toLowerCase(), fileVo.getFileName().toLowerCase());
                respVo.setFileUrl(fileUrl);
                respVoList.add(respVo);
            } catch (Exception e) {
                log.error("获取文件url失败");
                respVoList.add(respVo);
            }
        }

        return ResponseMessage.ok(respVoList);
    }

    /**
     * 批量上传图片并压缩
     *
     * @param files  MultipartFile
     * @param fileVo -> bucketName
     * @return 文件url
     */
    @PostMapping(value = "/putCompressImgs")
    @ApiOperation(value = "批量上传图片并压缩", notes = "上传原图生成原图与缩略图地址")
    public ResponseMessage<List<PutFileRespVo>> putCompressImgs(@RequestParam("file") MultipartFile[] files, @Valid FileReqVo fileVo, @RequestParam(value = "scale", required = false) Double scale) {
        log.info("begin===");
        FileValid.fileNameValid(fileVo.getBucketName());
        FileValid.fileNameValid(fileVo.getFileName());
        List<PutFileRespVo> respVoList = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                boolean legalFile = FileUploadUtils.checkSuffixLegal(file);
                if (!legalFile) {
                    throw new AppException(String.valueOf(500), "文件非法");
                }
                PutFileRespVo respVo = fileService.putCompressImgs(file, fileVo.getBucketName().toLowerCase(), scale);
                respVoList.add(respVo);
            } catch (Exception e) {
                log.error("文件上传失败, bucketName={}, fileName={}", fileVo.getBucketName(), fileVo.getFileName(), e);
            }
        }
        log.info("end===");
        return ResponseMessage.ok(respVoList);
    }

    /**
     * 上传图片并压缩
     *
     * @param file   MultipartFile
     * @param fileVo -> bucketName
     * @return 文件url
     */
    @PostMapping(value = "/putCompressImage")
    @ApiOperation(value = "上传图片并压缩", notes = "上传原图生成原图与缩略图地址")
    public ResponseMessage<PutFileRespVo> putCompressImage(@RequestParam("file") MultipartFile file, @Valid FileReqVo fileVo, @RequestParam(value = "scale", required = false) Double scale) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String date = formatter.format(new Date());

        String bucketName = fileVo.getBucketName() == null ? "common-" + date : fileVo.getBucketName();
        bucketName = bucketName.toLowerCase();

        FileValid.fileNameValid(bucketName);
        if (fileVo.getFileName() != null) {
            FileValid.fileNameValid(fileVo.getFileName());
        }

        PutFileRespVo respVo = PutFileRespVo.builder().build();

        try {
            boolean legalFile = FileUploadUtils.checkSuffixLegal(file);
            if (!legalFile) {
                throw new AppException(String.valueOf(500), "文件非法");
            }
            respVo = fileService.putCompressImgs(file, bucketName, scale);
        } catch (Exception e) {
            log.error("文件上传失败, bucketName={}, fileName={}", fileVo.getBucketName(), fileVo.getFileName(), e);
        }

        return ResponseMessage.ok(respVo);
    }

    /**
     * 上传图片并压缩
     *
     * @param file MultipartFile
     * @return 文件url
     */
    @PostMapping(value = "/uploadImg")
    @ApiOperation(value = "上传图片并压缩", notes = "上传原图生成原图与缩略图地址")
    public ResponseMessage<PutFileRespVo> uploadImg(@RequestParam("file") MultipartFile file) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String date = formatter.format(new Date());

        FileReqVo fileVo = FileReqVo.builder().build();
        Double scale = 0.2;

        String bucketName = fileVo.getBucketName() == null ? "common-" + date : fileVo.getBucketName();
        bucketName = bucketName.toLowerCase();

        FileValid.fileNameValid(bucketName);
        if (fileVo.getFileName() != null) {
            FileValid.fileNameValid(fileVo.getFileName());
        }

        PutFileRespVo respVo = PutFileRespVo.builder().build();

        try {
            boolean legalFile = FileUploadUtils.checkSuffixLegal(file);
            if (!legalFile) {
                throw new AppException(String.valueOf(500), "文件非法");
            }
            respVo = fileService.putCompressImgs(file, bucketName, scale);
        } catch (Exception e) {
            log.error("文件上传失败, bucketName={}, fileName={}", fileVo.getBucketName(), fileVo.getFileName(), e);
        }

        return ResponseMessage.ok(respVo);
    }

}
