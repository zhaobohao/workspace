

package com.spring.web.core.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 文件上传工具类
 *
 * @author zhaobohao
 * @date 2019/8/21
 * @since 1.2.1-RELEASE
 */
@Slf4j
public final class UploadUtil {

    public static String getExtension(String originalFilename) {
        String fileExtension = "";
        if (!StrUtil.isBlank(originalFilename)) {
            int dot = originalFilename.lastIndexOf('.');
            if ((dot > -1) && (dot < (originalFilename.length() - 1))) {
                fileExtension = originalFilename.substring(dot + 1);
            }
        }
        return fileExtension;
    }

    /**
     * 上传文件，默认文件名格式，yyyyMMddHHmmssS
     *
     * @param uploadPath
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public static String upload(String uploadPath, MultipartFile multipartFile) throws Exception {
        return upload(uploadPath, multipartFile, new DefaultUploadFileNameHandleImpl());
    }

    /**
     * 上传文件
     *
     * @param uploadPath           上传目录
     * @param multipartFile        上传文件
     * @param uploadFileNameHandle 回调
     * @return
     * @throws Exception
     */
    public static String upload(String uploadPath, MultipartFile multipartFile, UploadFileNameHandle uploadFileNameHandle) throws Exception {
        // 获取输入流
        InputStream inputStream = multipartFile.getInputStream();
        // 文件保存目录
        File saveDir = new File(uploadPath);
        // 判断目录是否存在，不存在，则创建，如创建失败，则抛出异常
        if (!saveDir.exists()) {
            boolean flag = saveDir.mkdirs();
            if (!flag) {
                throw new RuntimeException("创建" + saveDir + "目录失败！");
            }
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String saveFileName;
        if (uploadFileNameHandle == null) {
            saveFileName = new DefaultUploadFileNameHandleImpl().handle(originalFilename);
        } else {
            saveFileName = uploadFileNameHandle.handle(originalFilename);
        }
        log.info("saveFileName = " + saveFileName);

        File saveFile = new File(saveDir, saveFileName);
        // 保存文件到服务器指定路径
        FileUtil.writeFromStream(inputStream, saveFile);
        return saveFileName;
    }


    public static interface UploadFileNameHandle {
        String handle(String originalFilename);
    }

    public static class DefaultUploadFileNameHandleImpl implements UploadFileNameHandle {

        @Override
        public String handle(String originalFilename) {
            String fileExtension = UploadUtil.getExtension(originalFilename);

            // 文件后缀
            // 这里可自定义文件名称，比如按照业务类型/文件格式/日期
            // 此处按照文件日期存储
            String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssS"));
            String fileName = dateString + "." + fileExtension;
            return fileName;
        }
    }

}
