package indiv.budin.fileservice.controller;


import indiv.budin.common.utils.ResultUtil;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping("/file/upload")
    @ResponseBody
    public ResultUtil<String> upload(MultipartFile multipartFile) {
        logger.info(multipartFile.toString());
        logger.info(multipartFile.getName());
        logger.info(String.valueOf(multipartFile.getSize()));
        return ResultUtil.successWithData("shoudao");
    }

    @RequestMapping("/file/download")
    public ResultUtil<String> download(@RequestParam("fileName") String fileName) {
        return ResultUtil.fail();
    }

    @RequestMapping("/add/file")
    @ResponseBody
    public ResultUtil<String> addFile() {
        //do something

        return ResultUtil.fail();
    }

}
