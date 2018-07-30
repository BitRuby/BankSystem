package com.onwelo.practice.bts.ftp;


import com.onwelo.practice.bts.entity.Transfer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public interface FtpBaseInterface {

    /**
     * Connects to ftp server and tries to log in the user.
     *
     * @return boolean true if successful, false otherwise.
     */
    boolean openConnection();

    /**
     * Logouts the active user and disconnects from the server.
     *
     * @return boolean true if successful, false otherwise.
     */
    boolean closeConnection();

    /**
     * Retrieve a file from the ftp server.
     *
     * @param outputStream stream the file is read into.
     * @param remotePath   remote path for the file.
     * @return boolean true if successful, false otherwise.
     */

    boolean getFileByRemoteFilePath(OutputStream outputStream, String remotePath);

    /**
     * Retrieve a file from bank dir on the ftp server.
     *
     * @param outputStream stream the file is read into.
     * @param remotePath   remote path for the file.
     * @return boolean true if successful retrive all files, false otherwise.
     */
    boolean getFileFromBankRemoteDir(OutputStream outputStream, String remotePath);

    /**
     * Store a file on the ftp server.
     *
     * @param inputStream  Stream the new file is read from.
     * @param outboundPath Remote path the file should be placed at.
     * @return boolean true if successful, false otherwise.
     */

    boolean addFile(InputStream inputStream, String outboundPath);

    /**
     * Store a file on the ftp server.
     *
     * @param sourcePath   Local path the file is read from.
     * @param outboundPath Remote path the file should be placed at.
     * @return boolean true if successful, false otherwise.
     */

    boolean addFileFromLocalDir(String sourcePath, String outboundPath);

    /**
     * Verifies that the connection is valid.
     *
     * @return boolean true if connected, false otherwise.
     */

    boolean isConnected();

    /**
     * Create directory on ftp server.
     * @param outboundPath
     * @return boolean true if connected, false otherwise.
     */

    boolean createDirectory(String outboundPath);

    /**
     * Delete all files from directory on ftp server
     * @param deletePath
     * @return boolean true if connected, false otherwise.
     */

    boolean deleteAllFiles(String deletePath) throws IOException;

    /**
     * Get list of all files from directory on ftp server
     * @param path
     * @return list of files name
     */

    List<String> getFilesListFromDirectory(String path) throws IOException;

    /**
     * Get all files from directory on ftp server
     * @param bankDirectoryPath
     * @return list of files name
     */

    ArrayList<Transfer> retriveAllFile(String bankDirectoryPath);
}
