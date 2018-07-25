package com.onwelo.practice.bts.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class FtpService implements FtpBaseInterface {

    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(FtpService.class);
    private FtpConfig ftpConfig;
    private FTPClient ftpClient;

    @Autowired
    public FtpService(@Autowired FtpConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }

    @PostConstruct
    public void init() {
        if (this.ftpConfig.isAutostart()) {
            Logger.debug("Autostart connection to FTP server.");
            this.openConnection();
        }
    }

    @Override
    public boolean openConnection() {
        closeConnection();
        Logger.debug("Connecting to FTP server.");
        ftpClient = new FTPClient();
        boolean loggedIn = false;
        try {
            ftpClient.connect(ftpConfig.getHost(), ftpConfig.getPort());
            loggedIn = ftpClient.login(ftpConfig.getUser(), ftpConfig.getPassphrase());
            if (this.ftpConfig.getTimeout() > 0)
                ftpClient.setControlKeepAliveTimeout(ftpConfig.getTimeout());
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
        }
        return loggedIn;
    }

    @Override
    public void closeConnection() {
        if (ftpClient != null) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                Logger.error(e.getMessage(), e);
            }
        }

    }

    @Override
    public boolean getFileByRemoteFilePath(OutputStream outputStream, String remotePath) {
        try {
            Logger.debug("Trying to retrieve a file from remote path " + remotePath);
            return ftpClient.retrieveFile(remotePath, outputStream);
        } catch (IOException e) {
            Logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean getFileFromBankRemoteDir(OutputStream outputStream) {
        try {
            //TODO - Add to properties bank info to set base bank remote directory
            String remotePath = "bank";
            Logger.debug("Trying to retrieve a file from remote path " + remotePath);
            return ftpClient.retrieveFile(remotePath, outputStream);
        } catch (IOException e) {
            Logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean addFile(InputStream inputStream, String outboundPath) {
        try {
            Logger.debug("Trying to store a file to destination path " + outboundPath);
                return ftpClient.storeFile(outboundPath, inputStream);
        } catch (IOException e) {
            Logger.error(e.getMessage(), e);
            return false;
        }
    }


    @Override
    public boolean addFileFromLocalDir(String sourcePath, String outboundPath) {
        InputStream inputStream;
        try {
            inputStream = new ClassPathResource(sourcePath).getInputStream();
        } catch (IOException e) {
            Logger.error(e.getMessage(), e);
            return false;
        }
        return this.addFile(inputStream, outboundPath);
    }

    @Override
    public boolean isConnected() {
        boolean connected = false;
        final String METHODNAME = "<isConnected> : ";
        if (ftpClient != null) {
            try {
                connected = ftpClient.sendNoOp();
            } catch (IOException e) {
                Logger.error(METHODNAME + e.getMessage(), e);
            }
        }
        Logger.debug(METHODNAME + "Current connection status for FTP server -> " + connected);
        return connected;
    }
}
