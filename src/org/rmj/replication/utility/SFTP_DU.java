/*
 /*
 * Sample Usage 
 * SFTP_DU sftp = new SFTP_DU();
 * sftp.setHost = "192.168.20.100";
 * sftp.setPort = 23;
 * sftp.setUser = "guanzon";
 * sftp.setPassword("secret");
 * sftp.Download("/home/guanzon/download/", "d:/ggc_systems/download/", "file.file");
 * 
 * sftp.Upload("d:/ggc_systems/upload/", "/home/guanzon/upload/", "file.file");
 */

package org.rmj.replication.utility;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SFTP_DU {
    String sFTPHost;
    int    nFTPPort;
    String sFTPUser;
    String sFTPPswd;
    String sHostKey;

    JSch oJsch = null;
    Session     oSession     = null;
    Channel     oChannel     = null;
    ChannelSftp oChannelSftp = null;
    boolean bConnected = false;
    boolean mode_debug = false;
    
    public void setHost(String FTPHost){
        sFTPHost = FTPHost;
    }
    
    public void setPort(int Port){
        nFTPPort = Port;
    }
    
    public void setUser(String User){
        sFTPUser = User;
    }
    
    public void setPassword(String Password){
        sFTPPswd = Password;
    }

    public void setHostKey(String key){
        sHostKey = key;
    }
    
    public boolean xConnect(String fsFPTHost){
        bConnected = false;
        try {
            JSch oJSch = new JSch();
            oSession = oJSch.getSession(sFTPUser,fsFPTHost,nFTPPort);
            oSession.setPassword(sFTPPswd);
            
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            oSession.setConfig(config);
            oSession.connect();
            oChannel = oSession.openChannel("sftp");
            oChannel.connect();
            
            //set active host if connected
            sFTPHost = fsFPTHost;
            
            bConnected = true;
        } catch (JSchException ex) {
            Logger.getLogger(SFTP_DU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bConnected;
    } 

    public void xDisconnect(){
        oSession.disconnect();
        bConnected = false;
    }
    
    public boolean xIsConnected(){
        return bConnected;
    }
    
    public boolean xUpload(String sFTPSrce, String sFTPDest, String sFile)  throws Exception{
        ChannelSftp channelSftp;

        channelSftp = (ChannelSftp)oChannel;
        mkdir(sFTPDest);
        channelSftp.cd(sFTPDest);
        File f = new File(sFTPSrce + "/" + sFile);
        channelSftp.put(new FileInputStream(f), f.getName());
        channelSftp.exit();
        
        return true;
    }
    
    public boolean xDownload(String sFTPSrce, String sFTPDest, String sFile) throws Exception{
        ChannelSftp channelSftp;
        channelSftp = (ChannelSftp)oChannel;
        channelSftp.cd(sFTPSrce);

        channelSftp.get(sFile, sFTPDest);
        channelSftp.exit();
        
        return true;        
    }
    
    public boolean Upload(String sFTPSrce, String sFTPDest, String sFile) throws Exception{
        Session     session     = null;
        Channel     channel     = null;
        ChannelSftp channelSftp = null;

        JSch jsch = new JSch();
        session = jsch.getSession(sFTPUser,sFTPHost,nFTPPort);
        session.setPassword(sFTPPswd);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp)channel;
        
        mkdir(sFTPDest);
        channelSftp.cd(sFTPDest);
        
        File f = new File(sFTPSrce + "/" + sFile);
        if(mode_debug) System.out.println(sFTPSrce + "/" + sFile);
        channelSftp.put(new FileInputStream(f), f.getName());
        channelSftp.exit();
        session.disconnect();
        
        return true;
    }
    
    public boolean Download(String sFTPSrce, String sFTPDest, String sFile) throws Exception{
        Session     session     = null;
        Channel     channel     = null;
        ChannelSftp channelSftp = null;

        JSch jsch = new JSch();
        session = jsch.getSession(sFTPUser,sFTPHost,nFTPPort);
        session.setPassword(sFTPPswd);
        
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        
        session.setConfig(config);
        session.connect();
        channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp)channel;
        if(mode_debug) System.out.println(sFTPSrce);
        channelSftp.cd(sFTPSrce);

        //channelSftp.get(sFile, sFTPDest + "/" + sFTPDest);
        channelSftp.get(sFile, sFTPDest);
        channelSftp.exit();
        session.disconnect();            
        
        return true;
    }
    
    public void mkdir(String path){
      ChannelSftp channelSftp;
      channelSftp = (ChannelSftp)oChannel;
      try {
         SftpATTRS attrs = channelSftp.lstat(path);
      } catch (SftpException ex) {
         try {
            channelSftp.mkdir(path);
         } catch (SftpException ex1) {
            System.out.println(ex1.toString());
         }
      }
   }
    
   public String sendCommand(String command) throws Exception{
      Session     session     = null;
      Channel     channel     = null;
      StringBuilder outputBuffer = new StringBuilder();

      JSch jsch = new JSch();

      //add a certificate key if ever
      if(!sHostKey.isEmpty())
         jsch.addIdentity(sHostKey);
      
      if(mode_debug) System.out.println(sFTPUser + "»" +  sFTPHost + "»" + nFTPPort);
      if(mode_debug) System.out.println(sHostKey);
      session = jsch.getSession(sFTPUser,sFTPHost,nFTPPort);
      session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");

      //add a password if ever
      if(sHostKey.isEmpty())
         session.setPassword(sFTPPswd);

      java.util.Properties config = new java.util.Properties();
      config.put("StrictHostKeyChecking", "no");

      session.setConfig(config);
      session.connect();

      channel = session.openChannel("exec");
      ((ChannelExec)channel).setCommand(command);
      InputStream commandOutput = channel.getInputStream();
      channel.connect();

      int readByte = commandOutput.read();        

      while(readByte != 0xffffffff){
         outputBuffer.append((char)readByte);
         readByte = commandOutput.read();
      } 
      session.disconnect();            

      return outputBuffer.toString();
    }
}
