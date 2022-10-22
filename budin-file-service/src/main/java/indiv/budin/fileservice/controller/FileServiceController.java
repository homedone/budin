package indiv.budin.fileservice.controller;


import indiv.budin.common.utils.ResultUtil;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Controller
public class FileServiceController {
    public Logger logger = LoggerFactory.getLogger(FileServiceController.class);
    @RequestMapping("/")
    public String portal() {
        return "index.html";
    }
    @RequestMapping("/test")
    @ResponseBody
    public ResultUtil<String> test(){
        logger.info("shoudao");
        return ResultUtil.successWithData("shoudao");
    }

    @RequestMapping("/api/download")
    public ResultUtil<String> download(@RequestParam("fileName") String fileName) {
        return ResultUtil.fail();
    }

    @RequestMapping("/file/upload")
    @ResponseBody
    public ResultUtil<String> upload(@RequestParam String fileName, @RequestParam MultipartFile multipartFile) {
        //do something
        return ResultUtil.fail();
    }

}
