package com.onwelo.practice.bts.ftp;

import org.junit.jupiter.api.*;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.*;
import org.springframework.context.annotation.PropertySource;

import java.io.ByteArrayOutputStream;


import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@PropertySource("classpath:application-test.properties")
public class FtpServiceTest {

    private static final String FILE_CONTENT = "test onwelo practice bts";
    private static FakeFtpServer fakeFtpServer;
    private FtpService ftpService;
    private FtpConfig ftpConfig;


    @BeforeAll
    static void setupFakeFtpServer() {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "c:\\data"));

        FileSystem fileSystem = new WindowsFakeFileSystem();
        fileSystem.add(new DirectoryEntry("c:\\data"));
        fileSystem.add(new FileEntry("c:\\data\\file1.txt", FILE_CONTENT));
        fileSystem.add(new FileEntry("c:\\data\\run.exe"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(2121);

        fakeFtpServer.start();
    }

    @AfterAll
    static void shutdownFakeFTPServer() {
        fakeFtpServer.stop();
    }

    @BeforeEach
    void setupFtpConfig() {
        ftpConfig = getTestFtpConfig();
    }

    @AfterEach
    void cleanFtpConfigAndService(){
        ftpConfig = null;
        ftpService = null;
    }

    @Test
    void open() {
        ftpService = new FtpService(ftpConfig);
        assertTrue(ftpService.openConnection());
    }

   @Test
   void openWithKeepAliveTimout() {


        ftpConfig.setTimeout((short)5);
        ftpService = new FtpService(ftpConfig);
        assertTrue(ftpService.openConnection());
    }

    @Test
    void openWrongCredentialsShouldReturnFalse() {

        ftpConfig.setPassphrase("wrongpassword");
        ftpService = new FtpService(ftpConfig);
        assertFalse(ftpService.openConnection());
    }

    @Test
    void close() {
        ftpService = new FtpService(ftpConfig);
        ftpService.openConnection();
        ftpService.closeConnection();
    }

  @Test
   void closeWhenNotOpen() {
      ftpService = new FtpService(ftpConfig);
      ftpService.openConnection();
      ftpService.closeConnection();
      ftpService.closeConnection();
    }

    @Test
    void checkFileIsCorrectlyStoreOnFtp() {
        ftpService = new FtpService(ftpConfig);
        ftpService.openConnection();
        ftpService.addFileFromLocalDir("test_ftp_file.txt", "test_ftp_file.txt");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ftpService.getFileByRemoteFilePath(outputStream,"test_ftp_file.txt");
        assertEquals(outputStream.toString(),FILE_CONTENT);
    }


    private FtpConfig getTestFtpConfig() {
        FtpConfig ftpConfig = new FtpConfig();
        ftpConfig.setHost("localhost");
        ftpConfig.setUser("user");
        ftpConfig.setPassphrase("password");
        ftpConfig.setPort(2121);
        ftpConfig.setAutostart(true);
        return ftpConfig;
    }
}
