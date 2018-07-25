package com.onwelo.practice.bts.ftp;

import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.rules.TemporaryFolder;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.*;
import java.io.*;


import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FtpServiceTest {
    private static final String FILE_CONTENT = "test onwelo practice bts";
    private static FakeFtpServer fakeFtpServer;
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private FtpService ftpService;
    private FtpConfig ftpConfig;
    private File f;

    @BeforeAll
    static void setupFakeFtpServer() {

        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "c:\\data"));

        FileSystem fileSystem = new WindowsFakeFileSystem();
        fileSystem.add(new DirectoryEntry("c:\\data"));
        fileSystem.add(new FileEntry("c:\\data\\ftp.txt", FILE_CONTENT));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(2121);

        fakeFtpServer.start();
    }

    @AfterAll
    static void shutdownFakeFTPServer() {
        fakeFtpServer.stop();
    }

    @BeforeAll
    void createTempDirectoryAndFilesInside() {
        try {
            temporaryFolder.create();
            f = temporaryFolder.newFile();
            temporaryFolder.newFile("test.txt");
            temporaryFolder.newFile("test_ftp_file.txt");
            temporaryFolder.newFile("test_ftp.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    void deleteTempDirectory() {
        temporaryFolder.delete();
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
        assertTrue(ftpService.closeConnection());
    }

    @Test
    void closeWhenNotOpenConnection() {
        ftpService = new FtpService(ftpConfig);
        assertFalse(ftpService.closeConnection());
    }

    @Test
    void getFileFromFtpDoesExist() {
        ftpService = new FtpService(ftpConfig);
        ftpService.openConnection();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        assertTrue(ftpService.getFileByRemoteFilePath(outputStream, "ftp.txt"));
        assertEquals(outputStream.toString(), FILE_CONTENT);
    }

    @Test
    void getFileFromFtpDoesNotExist() {
        ftpService = new FtpService(ftpConfig);
        ftpService.openConnection();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        assertFalse(ftpService.getFileByRemoteFilePath(outputStream, "file_not_exist.txt"));
    }

    @Test
    void getFileFromFtpWithWrongConnectionCreditals() {
        ftpConfig.setPort(8523);
        ftpService = new FtpService(ftpConfig);
        if (ftpService.openConnection()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            assertFalse(ftpService.getFileByRemoteFilePath(outputStream, "file_not_exist.txt"));
        }
    }

    @Test
    void getFileFromFtpWhenNotConnected() {
        ftpService = new FtpService(ftpConfig);
        assertThrows(NullPointerException.class, () -> ftpService.getFileByRemoteFilePath(new ByteArrayOutputStream(), "file_not_exist.txt"));
    }

    @Test
    void tryStoreFileDoesNotExist() {
        ftpService = new FtpService(ftpConfig);
        ftpService.openConnection();
        assertFalse(ftpService.addFileFromLocalDir(temporaryFolder.getRoot()+"/test_ftp_file1.txt", "test1.txt"));
    }

    @Test
    void checkFileIsCorrectlyStoreOnFtp() throws FileNotFoundException {
        ftpService = new FtpService(ftpConfig);
        ftpService.openConnection();
        ftpService.addFile(new FileInputStream(f), "test.txt");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ftpService.getFileByRemoteFilePath(outputStream, "test.txt");
        assertEquals("", outputStream.toString());
    }

    @Test
    @Disabled
    void checkFIleIsCorrectlyStoreOnFtpWithStandardCreditals() {
        ftpService = new FtpService(getStandardTestFtpConfig());
        ftpService.openConnection();
        ftpService.addFileFromLocalDir("test.txt", "test_y.txt");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ftpService.getFileByRemoteFilePath(outputStream, "test_y.txt");
        assertEquals(outputStream.toString(), "dddddddd");
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

    private FtpConfig getStandardTestFtpConfig() {
        FtpConfig ftpConfig = new FtpConfig();
        ftpConfig.setHost("127.0.0.1");
        ftpConfig.setUser("uibank");
        ftpConfig.setPassphrase("passub");
        ftpConfig.setPort(21599);
        ftpConfig.setAutostart(true);
        return ftpConfig;
    }
}
