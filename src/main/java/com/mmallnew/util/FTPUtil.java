package com.mmallnew.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author ：Y.
 * @date ：Created in 17:10 2019/2/9
 * @description：${description}
 * @modified By：
 * @version: $version$
 */
public class FTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    public static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    public static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    public static String ftpPassword = PropertiesUtil.getProperty("ftp.pass");
    public static String ftpProt = PropertiesUtil.getProperty("ftp.port");

    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;
    private boolean uploaded;

    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }


    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 858, ftpUser, ftpPassword);
        logger.info("开始连接ftp:");
        boolean result = ftpUtil.uploadFile("img", fileList);
        logger.info("开始上传，上传结果{}", result);
        return result;
    }

    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        uploaded = true;
        FileInputStream fis = null;
        if (connectServer(this.getIp(), this.getPort(), this.getUser(), this.getPwd())) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for (File fileItem : fileList) {
                    fis = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(), fis);
                }
            } catch (IOException e) {
                logger.error("上传文件异常", e);
                uploaded = false;
                e.printStackTrace();
            } finally {
                assert fis != null;
                fis.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }

    private boolean connectServer(String ip, int port, String user, String password) {
        boolean isSuccess = false;
        ftpClient = new FTPClient();

        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user, password);
        } catch (IOException e) {
            logger.error("连接ftp异常", e);
            e.printStackTrace();
        }
        return isSuccess;
    }

    public static String getFtpIp() {
        return ftpIp;
    }

    public static void setFtpIp(String ftpIp) {
        FTPUtil.ftpIp = ftpIp;
    }

    public static String getFtpUser() {
        return ftpUser;
    }

    public static void setFtpUser(String ftpUser) {
        FTPUtil.ftpUser = ftpUser;
    }

    public static String getFtpPassword() {
        return ftpPassword;
    }

    public static void setFtpPassword(String ftpPassword) {
        FTPUtil.ftpPassword = ftpPassword;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }


}
