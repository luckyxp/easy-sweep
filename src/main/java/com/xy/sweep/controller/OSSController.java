package com.xy.sweep.controller;

import com.xy.sweep.util.OSSUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * OSS图片存储
 * @author climb.xu
 * @date 2022/3/26 17:08
 */
@Api(tags = "图片存储")
@RestController
@RequestMapping("sweep/oss")
public class OSSController {
    @PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile file) {
        return OSSUtil.upload2Oss(file);
    }
}
