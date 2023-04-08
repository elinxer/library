package com.elinxer.cloud.library.server.controller;

import com.dbn.cloud.platform.cache.redis.ICacheService;
import com.dbn.cloud.platform.web.crud.web.result.PagerResult;
import com.dbn.cloud.platform.web.crud.web.result.ResponseMessage;
import com.elinxer.cloud.library.server.domain.dto.CategoryDto;
import com.elinxer.cloud.library.server.service.ICategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.http.HttpRequest;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    ICacheService iCacheService;

    @Resource
    ICategoryService iCategoryService;

    @GetMapping("/listPage")
    public ResponseMessage<PagerResult<CategoryDto>> listPage(@RequestParam("type") String type, @RequestParam
            ("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        if (currentPage <= 0) {
            currentPage =1;
            pageSize = 50;
        }
        return ResponseMessage.ok(iCategoryService.listPage(type, currentPage, pageSize));
    }

    @GetMapping("/test")
    @ApiOperation(value = "test")
    public String test() {
        iCacheService.setCache("test", "xxxxxxxxxxxxxxxxxxxxx");
        return "String";
    }

    @GetMapping("/test2")
    @ApiOperation(value = "test2")
    public String test2() {
        String cache = iCacheService.getCache("test");
        System.out.println(cache);
        return "String";
    }

}
