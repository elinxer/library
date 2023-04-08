//package com.elinxer.cloud.library.server.assist.feign;
//
//
//import com.dbn.cloud.oss.client.domain.vo.response.PutFileRespVo;
//import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
//import com.elinxer.cloud.library.server.assist.config.MultiPartSupportConfiguration;
//import com.elinxer.cloud.library.server.assist.feign.fallback.OssFallbackFactory;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.multipart.MultipartFile;
//
//
//@FeignClient(value = "dbn-cloud-oss", configuration = MultiPartSupportConfiguration.class, fallbackFactory = OssFallbackFactory.class, decode404 = true)
//public interface OssFeign {
//
//    @PostMapping(value = "/v1/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    ResponseMessage<PutFileRespVo> upload(@RequestPart("file") MultipartFile file);
//
//
//}
