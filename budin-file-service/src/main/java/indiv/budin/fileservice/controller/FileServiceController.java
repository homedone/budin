package indiv.budin.fileservice.controller;


import indiv.budin.common.utils.ResultUtil;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Controller
public class FileServiceController {
    @RequestMapping("/")
    public String portal() {
        return "index.html";
    }

    @RequestMapping("/api/download")
    public ResultUtil<String> download(@RequestParam("fileName") String fileName) {
        return ResultUtil.fail();
    }

    @RequestMapping("/api/upload")
    public ResultUtil<String> upload(@RequestParam String fileName, @RequestParam MultipartFile multipartFile) {
        //do something
        return ResultUtil.fail();
    }

}
