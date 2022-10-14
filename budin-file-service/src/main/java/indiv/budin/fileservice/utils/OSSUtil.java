package indiv.budin.fileservice.utils;


import indiv.budin.common.utif.ResultCode;
import indiv.budin.common.utils.ResultUtil;
import indiv.budin.common.utils.UuidUtil;
import indiv.budin.fileservice.config.MinioConfig;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.apache.commons.lang.StringUtils;

import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author homedone
 * @date 2022/10/11 17 30
 * discription
 */
@Component
public class OSSUtil {
    @Resource
    private MinioClient minioClient;

    private MinioConfig minioConfig;

    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除存储bucket
     *
     * @return Boolean
     */
    public boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取全部bucket
     */
    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param bucketName
     * @param dirPathName
     * @discript 新建空文件或者空目录, 文件目录则以"/"结尾
     */
    public boolean createDirectory(String bucketName, String dirPathName) throws InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucketName).object(dirPathName).stream(
                            new ByteArrayInputStream(new byte[]{}), 0, -1)
                    .build();
            minioClient.putObject(putObjectArgs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件是否存在
     * 暂时不能使用，不能根据异常判断是否存在
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
     * @return true存在, 反之
     */
    public Boolean checkFileIsExist(String bucketName, String objectName) {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @param folderName 文件夹名称
     * @return
     */
    public boolean checkFolderIsExist(String bucketName, String folderName) {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs
                            .builder()
                            .bucket(bucketName)
                            .prefix(folderName)
                            .recursive(false)
                            .build());
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.objectName() == null) continue;
                String name = item.objectName();
                if (name.charAt(name.length() - 1) == '/' && (folderName.equals(name) || folderName.equals(name.substring(0, name.length() - 1)))) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @return message
     */
    public String upload(MultipartFile file, String bucketName, String dirPathName, String fileFullName) {
        String objectName = dirPathName + fileFullName;
        return upload(file, bucketName, objectName);
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @return message
     */
    public String upload(MultipartFile file, String bucketName, String objectName) {
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(bucketName).object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build();
            //文件名称相同会覆盖
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return objectName;
    }

    /**
     * @return url
     */
    public String preview(String bucketName, String fileName) {
        // 查看文件地址
        GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(fileName).method(Method.GET).build();
        try {
            return minioClient.getPresignedObjectUrl(build);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 文件下载
     *
     * @param fileName 文件名称
     * @param response response
     * @return void
     */
    public void download(String bucketName, String fileName, HttpServletResponse response) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucketName)
                .object(fileName).build();
        try (GetObjectResponse objectResponse = minioClient.getObject(objectArgs)) {
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
                while ((len = objectResponse.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                response.setCharacterEncoding("utf-8");
                // 设置强制下载不打开
                // res.setContentType("application/force-download");
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                try (ServletOutputStream stream = response.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看桶内信息
     *
     * @return 存储bucket内文件对象信息
     */
    public List<Item> listObjects(String buketName) {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(buketName).build());
        List<Item> items = new ArrayList<>();
        try {
            for (Result<Item> result : results) {
                items.add(result.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return items;
    }

    /**
     * 删除文件或文件夹
     *
     * @param fileName
     * @return
     */
    public boolean remove(String bucketName, String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
