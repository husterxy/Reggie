package reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reggie.common.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传和下载
 *
 * @author hust_xy
 */

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value(("${reggie.path}"))
    private String basePath;

    /**
     * 文件上传
     *
     * @param file file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {

        log.info(file.toString());

        //原始文件名
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用 UUID 重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID() + substring;

        //创建一个目录对象
        File directory = new File(basePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //将临时文件转存到指定位置
        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success(fileName);
    }

    /**
     * 文件下载并回显在浏览器上
     *
     * @param response
     * @param name
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response, String name) {

        FileInputStream inputStream = null;
        ServletOutputStream outputStream = null;
        byte[] bytes;
        response.setContentType("image/jpeg");

        try {
            inputStream = new FileInputStream(basePath + name);
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);

            outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
