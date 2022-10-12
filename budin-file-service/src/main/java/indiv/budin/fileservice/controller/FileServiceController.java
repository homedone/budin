package indiv.budin.fileservice.controller;


import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class FileServiceController {
    @RequestMapping("/")
    public String portal(){
        return "index.html";
    }
    @RequestMapping("/download")
    public void download(@RequestParam("fileName") String fileName){

    }

}
