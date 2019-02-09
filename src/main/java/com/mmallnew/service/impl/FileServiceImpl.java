package com.mmallnew.service.impl;

import com.google.common.collect.Lists;
import com.mmallnew.service.IFileService;
import com.mmallnew.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author ：Y.
 * @date ：Created in 16:59 2019/2/9
 * @version : V1.0
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + fileExtensionName;
        logger.info("开始上传文件，上传的文件名：{}，上传的路径：{}，新文件名：{}", fileName, path, uploadFileName);
        File fileDir = new File(path);
        if (fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            //todo 将文件上传到ftp/cos
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //todo 上传完成只有删除upload下的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.info("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }
}
