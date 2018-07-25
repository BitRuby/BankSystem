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
        fileSystem.add(new FileEntry("c:\\data\\test_ftp_file.txt", FILE_CONTENT));
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
    void cleanFtpConfigAndService() {
        ftpService.closeConnection();
        ftpConfig = null;
        ftpService = null;
    }

    @Test
    void openConnection() {
        ftpService = new FtpService(ftpConfig);
        assertTrue(ftpService.openConnection());
    }

    @Test
    void checkIsConnectedAfterOpenFtpConnection() {
        ftpService = new FtpService(ftpConfig);
        ftpService.openConnection();
        assertTrue(ftpService.isConnected());
    }

    @Test
    void checkIsNotConnectedAfterCloseFtpConnection() {
        ftpService = new FtpService(ftpConfig);
        assertFalse(ftpService.isConnected());
    }

    @Test
    void openWithTimeout() {
        ftpConfig.setTimeout((short) 5);
        ftpService = new FtpService(ftpConfig);
        assertTrue(ftpService.openConnection());
    }

    @Test
    void openConnectionWithWrongPassword() {

        ftpConfig.setPassphrase("wrongpassword");
        ftpService = new FtpService(ftpConfig);
        assertFalse(ftpService.openConnection());
    }

    @Test
    void openConnectionWithWrongPort() {
        ftpConfig.setPort(21);
        ftpService = new FtpService(ftpConfig);
        assertFalse(ftpService.openConnection());
    }

    @Test
    void closeConnection() {
        ftpService = new FtpService(ftpConfig);
        ftpService.openConnection();
        ftpService.closeConnection();
    }

    @Test
    void closeWhenNotOpenConnection() {
        ftpService = new FtpService(ftpConfig);
        ftpService.openConnection();
        ftpService.closeConnection();
        ftpService.closeConnection();
    }

    @Test
    void getFileFromFtpDoesExist() {
        ftpService = new FtpService(ftpConfig);
        ftpService.openConnection();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean success = ftpService.getFileByRemoteFilePath(outputStream, "test_ftp_file.txt");
        assertTrue(success);
        assertEquals(outputStream.toString(), FILE_CONTENT);
    }

    @Test
    void getFileFromFtpDoesNotExist() {
        ftpService = new FtpService(ftpConfig);
        ftpService.openConnection();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean success = ftpService.getFileByRemoteFilePath(outputStream, "file_not_exist.txt");
        assertFalse(success);
    }

    @Test
    void getFileFromFtpWithWrongConnectionCreditals() {
        ftpConfig.setPort(8523);
        ftpService = new FtpService(ftpConfig);
        assertFalse(ftpService.openConnection());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean success = ftpService.getFileByRemoteFilePath(outputStream, "file_not_exist.txt");
        assertFalse(success);
    }

    @Test
    void getFileFromFtpWhenNotConnected() {
        ftpService = new FtpService(ftpConfig);
        assertThrows(NullPointerException.class, ()-> {ftpService.getFileByRemoteFilePath(new ByteArrayOutputStream(), "file_not_exist.txt");});
    }

    @Test
    void saveFileShouldStoreCorrectly() {
        ftpService = new FtpService(ftpConfig);
        ftpService.openConnection();
        boolean success = ftpService.addFileFromLocalDir("test.txt", "test.txt");
        FileSystem fileSystem = fakeFtpServer.getFileSystem();
        FileSystemEntry entry = fileSystem.getEntry("c:\\data\\test.txt");
        assertTrue(success);
        assertNotNull(entry);
    }

    @Test
    void tryStoreFileDoesNotExist() {
        ftpService = new FtpService(ftpConfig);
        ftpService.openConnection();
        boolean success = ftpService.addFileFromLocalDir("test1.txt", "test1.txt");
        assertFalse(success);
    }

    @Test
    void checkFileIsCorrectlyStoreOnFtp() {
        ftpService = new FtpService(ftpConfig);
        ftpService.openConnection();
        ftpService.addFileFromLocalDir("test_ftp_file.txt", "test_ftp_file.txt");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ftpService.getFileByRemoteFilePath(outputStream, "test_ftp_file.txt");
        assertEquals(outputStream.toString(), FILE_CONTENT);
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
