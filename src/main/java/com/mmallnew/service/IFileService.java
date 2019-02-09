package com.mmallnew.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：Y.
 * @version : V1.0
 * @date ：Created in 16:58 2019/2/9
 */
public interface IFileService {
    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 路径
     * @return String
     * @author Y.
     * @date 23:26 2019/2/9
     */
    String upload(MultipartFile file, String path);
}
