package indi.budin.fileservice.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
