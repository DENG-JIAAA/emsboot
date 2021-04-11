package top.dj.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.dj.utils.FileUtil;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;


/**
 * @author dj
 * @date 2021/4/2
 */
@Service
public class QiNiuService {

    private static final Logger logger = LoggerFactory.getLogger(QiNiuService.class);

    // 设置好账号的 ACCESS_KEY 和 SECRET_KEY
    private final String ACCESS_KEY = "J004ewGSWeoJd8PWoX2rbp4YWiapdYrapS98uVOh";
    private final String SECRET_KEY = "7zWqOPZyaYw0H1g2_48IelikMdQqPhpsRSRSvZf5";
    // 要上传的空间
    String bucketName = "djosimon";

    // 密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    // 构造一个带指定Zone对象的配置类,不同的七云牛存储区域调用不同的zone（此处为 -- 华南）
    Configuration cfg = new Configuration(Zone.zone2());
    UploadManager uploadManager = new UploadManager(cfg);

    // 域名
    private final static String QINIU_IMAGE_DOMAIN = "http://img.djosimon.top/";

    // 简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketName);
    }

    public String saveImage(MultipartFile file) throws IOException {
        try {
            // 获取 . 下标
            int dotPos = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
            if (dotPos < 0) return null;

            // 获取文件后缀名，判断是否合法
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (!FileUtil.isFileAllowed(fileExt)) return null;

            // 设置存储在七牛云的文件名
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            // 调用put方法上传
            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());

            // 打印返回的信息
            if (res.isOK() && res.isJson()) {
                // 返回这张存储照片的地址
                return QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");
            } else {
                logger.error("七牛存储异常:" + res.bodyString());
                return null;
            }
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            logger.error("七牛存储异常:" + e.getMessage());
            return null;
        }
    }
}
