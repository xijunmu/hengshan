package com.hengshan.service.impl;

import com.hengshan.common.enums.ReturnCode;
import com.hengshan.exception.SystemException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class UploadService {

    /**
     * 根据当前时间生成文件路径
     *
     * @param fileName 文件名
     * @return String 文件路径
     */
    public String generateFilePath(String fileName) {
        //根据日期生成路径
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        // test.jpg -> .jpg
        String fileType = fileName.substring(index);
        return datePath + uuid + fileType;
    }

    public void upload(MultipartFile file) {
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && (!originalFilename.endsWith(".png") || !originalFilename.endsWith(".jpg") || !originalFilename.endsWith(".jpeg"))) {
            throw new SystemException(ReturnCode.FILE_TYPE_ERROR);
        }
        String filePath = generateFilePath(originalFilename);
        System.out.println("[ Name:" + originalFilename + "  Size:" + file.getSize() / 1024 + "KB]");
    }
}
