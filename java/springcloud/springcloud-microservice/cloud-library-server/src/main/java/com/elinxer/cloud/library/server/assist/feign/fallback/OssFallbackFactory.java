//package com.elinxer.cloud.library.server.assist.feign.fallback;
//
//
//import com.dbn.cloud.oss.client.domain.vo.request.FileReqVo;
//import com.dbn.cloud.oss.client.domain.vo.response.PutFileRespVo;
//import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
//import com.elinxer.cloud.library.server.assist.feign.OssFeign;
//import feign.hystrix.FallbackFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//@Slf4j
//@Component
//public class OssFallbackFactory implements FallbackFactory<OssFeign> {
//    @Override
//    public OssFeign create(Throwable throwable) {
//        return new OssFeign() {
//            @Override
//            public ResponseMessage<PutFileRespVo> upload(MultipartFile file) {
//                return null;
//            }
//        };
//    }
//}
