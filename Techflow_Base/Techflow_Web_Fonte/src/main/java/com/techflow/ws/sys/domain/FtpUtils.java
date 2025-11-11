package com.techflow.ws.sys.domain;

import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FtpUtils {
	
	private static final Logger log = LogManager.getLogger(FtpUtils.class);
			
	public FTPClient connect(String server, Integer port, String user, String password) throws SocketException, IOException {
		FTPClient ftpClient = new FTPClient();
        ftpClient.connect(server, port);
        ftpClient.login(user, password);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        
        return ftpClient;
	}
	
	public void disconnect(FTPClient ftpClient) throws IOException {
		ftpClient.logout();
		ftpClient.disconnect();
	}
	
	public static void uploadFile(FTPClient ftpClient, String localPath, String filename, String remotePath) {
		try {
        	
			ftpClient.changeWorkingDirectory(remotePath);
			if(!localPath.endsWith("/"))
				localPath = localPath + "/";
	        File localFile = new File(localPath + filename);
	        
	        String newRemoteDirectory = remotePath + localFile.getName().substring(localPath.length());
	        
	        ftpClient.makeDirectory(newRemoteDirectory);
	        
	        InputStream inputStream = new FileInputStream(localFile);
            ftpClient.storeFile(newRemoteDirectory, inputStream);
            inputStream.close();
        }
        catch(Exception ex) {
        	log.error(ex.getMessage());
        }     
	}
	
	public static void uploadDirectory(FTPClient ftpClient, String localPath, String remotePath) throws IOException {
		
        try {
        	ftpClient.changeWorkingDirectory(remotePath);
	        File localDirectory = new File(localPath);
	        for (File filename : Arrays.asList(localDirectory.listFiles())) {
	            if (filename.isDirectory()) {
	                String newRemoteDirectory = remotePath + "/" + filename.getName();
	                ftpClient.makeDirectory(newRemoteDirectory);
	                uploadDirectory(ftpClient, filename.getPath(), newRemoteDirectory);
	            } else {
	                String remoteFile = remotePath + "/" + filename.getName();
	                InputStream inputStream = new FileInputStream(filename);
	                ftpClient.storeFile(remoteFile, inputStream);
	                inputStream.close();
	            }
	        }

        }
        catch(Exception ex) {
        	log.error(ex.getMessage());
        }        
    }
}
