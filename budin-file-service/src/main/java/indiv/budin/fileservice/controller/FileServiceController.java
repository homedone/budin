package indiv.budin.fileservice.controller;


import indiv.budin.common.constants.FileServiceCode;
import indiv.budin.common.constants.FileServiceConstant;
import indiv.budin.common.utif.ResultCode;
import indiv.budin.common.utils.FileUtil;
import indiv.budin.common.utils.ResultUtil;
import indiv.budin.common.utils.UuidUtil;
import indiv.budin.entity.vo.BudinFolderVO;
import indiv.budin.fileservice.service.api.FileService;
import indiv.budin.fileservice.utils.OSSUtil;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class FileServiceController {
    public Logger logger = LoggerFactory.getLogger(FileServiceController.class);

    @Autowired
    private OSSUtil ossUtil;

    @Autowired
    private FileService fileService;

    @RequestMapping("/")
    public String portal() {
        return "index.html";
    }

    @RequestMapping("/file/upload")
    @ResponseBody
    public ResultUtil<String> fileUpload(@RequestParam("file") MultipartFile multipartFile) {
        String fileOriginalName = multipartFile.getOriginalFilename();
        String uuid = UuidUtil.makeUuid();
        String fileName = new SimpleDateFormat("yyyyMMdd").format(new Date()) + uuid;
        String fileSuffix = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
        //获取用户bucket，当前文件路径
        String bucketName = "budin-oss1";
        String dirName = "/";
        String objectName = dirName + fileName + fileSuffix;
        long fileSize = multipartFile.getSize();
        if (fileSize > FileServiceConstant.MAX_FILE_SIZE * FileUtil.getUnit(FileServiceConstant.FILE_UINT))
            return ResultUtil.failWithExMessage(FileServiceCode.FILE_OVER_SIZE);
        try {
            if (!ossUtil.bucketExists(bucketName) || !ossUtil.checkFolderIsExist(bucketName, dirName))
                return ResultUtil.failWithExMessage(FileServiceCode.PATH_NAME_ERROR);
            String result = ossUtil.upload(bucketName, objectName, multipartFile.getInputStream(), fileSize, multipartFile.getContentType());
            if (result == null) return ResultUtil.fail();

            //存入数据库
            return ResultUtil.successWithData(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.failWithExMessage(FileServiceCode.SYSTEM_ERROR);
        }
    }

    @RequestMapping(value = "/file/build/folder")
    @ResponseBody
    public ResultUtil<String> buildFolder(@RequestBody BudinFolderVO budinFolderVO) {
        //获取用户bucket，当前目录
        String bucketName = "budin-oss1";
        logger.info(budinFolderVO.toString());
        String folderPath = budinFolderVO.getFolderOriginalName() + budinFolderVO.getFolderPath();
        if (ossUtil.checkFolderIsExist(bucketName, folderPath)) return ResultUtil.failWithExMessage(FileServiceCode.FOLDER_EXIST);
        try {
            boolean result = ossUtil.createDirectory(bucketName, folderPath);
            return ResultUtil.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.failWithExMessage(FileServiceCode.SYSTEM_ERROR);
        }
    }


    @RequestMapping("/file/download")
    public ResultUtil<String> fileDownload(@RequestParam("fileName") String fileName) {
        return ResultUtil.fail();
    }




}
