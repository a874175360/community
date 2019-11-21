package life.majiang.community.controller;

import com.alibaba.fastjson.JSONObject;
import life.majiang.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController {
    public final static String IMG_PATH_PREFIX = "static/images";

    public static File getImgDirFile() {

        // 构建上传文件的存放 "文件夹" 路径
        String fileDirPath = new String("src/main/resources/" + IMG_PATH_PREFIX);

        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }

    @RequestMapping("/file/upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        System.out.println(request.getContextPath());
        String realPath = "/images/";
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        // 存放上传图片的文件夹
        File fileDir = FileController.getImgDirFile();
        System.out.println(fileDir.getAbsolutePath());

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(realPath + file.getOriginalFilename());
            Files.write(path, bytes);
            // 构建真实的文件路径
            File newFile = new File(fileDir.getAbsolutePath() + File.separator + fileName);
            System.out.println(newFile.getAbsolutePath());
            // 上传图片到 -》 “绝对路径”
            file.transferTo(newFile);
            resultMap.put("success", 1);
            resultMap.put("message", "上传成功！");
            resultMap.put("url", realPath + fileName);
        } catch (Exception e) {
            resultMap.put("success", 0);
            resultMap.put("message", "上传失败！");
            e.printStackTrace();
        }
        System.out.println(resultMap.get("success"));
        return resultMap;
    }
}

