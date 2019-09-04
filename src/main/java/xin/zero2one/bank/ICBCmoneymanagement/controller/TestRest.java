package xin.zero2one.bank.ICBCmoneymanagement.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;



/**
 * @author ZJD
 * @date 2019/2/1
 */
@RestController
public class TestRest {

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String filePath = "C:\\Users\\jundo\\Desktop\\test\\";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
//            LOGGER.info("上传成功");
            return "上传成功";
        } catch (IOException e) {
//            LOGGER.error(e.toString(), e);
        }
        return "上传失败！";
    }


}
