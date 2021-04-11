package top.dj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.dj.service.impl.QiNiuService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author dj
 * @date 2021/4/2
 */
@RestController
public class QiNiuController {

    @Autowired
    private QiNiuService qiniuService;

    @RequestMapping(value = "/testUpload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        if(file.isEmpty()) {
            return "error";
        }

        try {
            String fileUrl=qiniuService.saveImage(file);
            return "success, imageUrl = " + fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }

}
