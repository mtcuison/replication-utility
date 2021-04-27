/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rmj.replication.utility;

import com.google.gson.Gson;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

public class MiscReplUtil {
    static final int BUFFER = 2048;
    static final boolean mode_debug = false;
    //hash = md5Hash("D:/ggc_systems/repl/download/zipped/file.zip.gz");
    public static String md5Hash(String filename){
        String md5 = "";
        try{
            FileInputStream fis = new FileInputStream(new File(filename));
            md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
            if(mode_debug) System.out.println(md5);
            fis.close();     
        }catch(Exception ex){
            ex.printStackTrace();
        }        
        
        return md5;
    }
    
    //tar("c:/exer4-courses.json", "d:/");
    public static boolean tar(String fsrawfile, String destpath) throws IOException{
        boolean failed = false;

        File dest = new File(destpath);
        if (!dest.exists()) {
            dest.mkdirs();
        }   

        File source = new File(fsrawfile);
        if (!source.exists()) {
            return false;
        }            
        
        FileOutputStream fOut = null;
        BufferedOutputStream bOut = null;
        GzipCompressorOutputStream gzOut = null;
        TarArchiveOutputStream tOut = null;

        fOut = new FileOutputStream(new File(destpath + source.getName() + ".tar.gz"));
        bOut = new BufferedOutputStream(fOut);
        gzOut = new GzipCompressorOutputStream(bOut);
        tOut = new TarArchiveOutputStream(gzOut);

        TarArchiveEntry entry = new TarArchiveEntry(source, source.getName());            
        tOut.putArchiveEntry(entry);

        FileInputStream fi = new FileInputStream(fsrawfile);
        BufferedInputStream sourceStream = new BufferedInputStream(fi, BUFFER);
        int count;
        byte data[] = new byte[BUFFER];
        while ((count = sourceStream.read(data, 0, BUFFER)) != -1) {
            tOut.write(data, 0, count);
        }
        sourceStream.close();

        tOut.closeArchiveEntry();            
        tOut.close();
        
        return !failed;
    }
    
    //untar("d:/exer4-courses.json.tar.gz", "e:/");
    public static boolean untar(String fstarfile, String destpath) throws IOException{
        boolean failed = false;

        File destpth = new File(destpath);
        if (!destpth.exists()) {
            destpth.mkdirs();
        }           
        
        FileInputStream fin = new FileInputStream(fstarfile);
        BufferedInputStream in = new BufferedInputStream(fin);
        GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
        TarArchiveInputStream tarIn = new TarArchiveInputStream(gzIn);

        TarArchiveEntry entry = null;            

        while ((entry = (TarArchiveEntry) tarIn.getNextEntry()) != null) {

            int count;
            byte data[] = new byte[BUFFER];

            FileOutputStream fos = new FileOutputStream(destpath + entry.getName());
            BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
            while ((count = tarIn.read(data, 0, BUFFER)) != -1) {
                dest.write(data, 0, count);
            }

            dest.close();
        }

        tarIn.close();            
            
        return !failed;
    }
    
    public static LinkedList<Map<String, String>> RStoLinkList(ResultSet rs) throws SQLException{
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        LinkedList<Map<String, String>> list = new LinkedList<Map<String, String>>(); 
       
        while(rs.next()){
            Map map = new HashMap();
            for (int i = 1; i <= count; i++) {
                String key = rsmd.getColumnName(i);

                if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
                    map.put(key, rs.getArray(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
                    map.put(key, rs.getLong(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.REAL) {
                    map.put(key, rs.getFloat(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
                    map.put(key, rs.getBoolean(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
                    map.put(key, rs.getBlob(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
                    map.put(key, rs.getDouble(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
                    map.put(key, rs.getDouble(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
                    map.put(key, rs.getInt(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
                    map.put(key, rs.getNString(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
                    map.put(key, rs.getString(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.CHAR) {
                    map.put(key, rs.getString(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.NCHAR) {
                    map.put(key, rs.getNString(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.LONGNVARCHAR) {
                    map.put(key, rs.getNString(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.LONGVARCHAR) {
                    map.put(key, rs.getString(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
                    map.put(key, rs.getByte(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
                    map.put(key, rs.getShort(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
                    map.put(key, rs.getDate(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.TIME) {
                    map.put(key, rs.getTime(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
                    map.put(key, rs.getTimestamp(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.BINARY) {
                    map.put(key, rs.getBytes(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.VARBINARY) {
                    map.put(key, rs.getBytes(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.LONGVARBINARY) {
                    map.put(key, rs.getBinaryStream(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.BIT) {
                    map.put(key, rs.getBoolean(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.CLOB) {
                    map.put(key, rs.getClob(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.NUMERIC) {
                    map.put(key, rs.getBigDecimal(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.DECIMAL) {
                    map.put(key, rs.getBigDecimal(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.DATALINK) {
                    map.put(key, rs.getURL(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.REF) {
                    map.put(key, rs.getRef(i));
                } else if (rsmd.getColumnType(i) == java.sql.Types.STRUCT) {
                    map.put(key, rs.getObject(i)); // must be a custom mapping consists of a class that implements the interface SQLData and an entry in a java.util.Map object.
                } else if (rsmd.getColumnType(i) == java.sql.Types.DISTINCT) {
                    map.put(key, rs.getObject(i)); // must be a custom mapping consists of a class that implements the interface SQLData and an entry in a java.util.Map object.
                } else if (rsmd.getColumnType(i) == java.sql.Types.JAVA_OBJECT) {
                    map.put(key, rs.getObject(i));
                } else {
                    map.put(key, rs.getString(i));
                }
            }                  

            list.add(map);
        }
        rs.close();
       
        return list;
    }
    
    public static String RStoJSON(ResultSet rs) throws SQLException, IOException{
       Gson obj = new Gson();
       String gson = obj.toJson(RStoLinkList(rs));
       if(mode_debug) System.out.println("json created using gson...");
        
       return obj.toJson(gson);
    }
    
//    public static String RStoJSON(ResultSet rs) throws SQLException, IOException {
//        ResultSetMetaData rsmd = rs.getMetaData();
//        int count = rsmd.getColumnCount();
//        LinkedList<Map<String, String>> list = new LinkedList<Map<String, String>>(); 
//       
//        while(rs.next()){
//            Map map = new HashMap();
//            for (int i = 1; i <= count; i++) {
//                String key = rsmd.getColumnName(i);
//
//                if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
//                    map.put(key, rs.getArray(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
//                    map.put(key, rs.getLong(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.REAL) {
//                    map.put(key, rs.getFloat(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
//                    map.put(key, rs.getBoolean(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
//                    map.put(key, rs.getBlob(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
//                    map.put(key, rs.getDouble(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
//                    map.put(key, rs.getDouble(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
//                    map.put(key, rs.getInt(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
//                    map.put(key, rs.getNString(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
//                    map.put(key, rs.getString(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.CHAR) {
//                    map.put(key, rs.getString(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.NCHAR) {
//                    map.put(key, rs.getNString(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.LONGNVARCHAR) {
//                    map.put(key, rs.getNString(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.LONGVARCHAR) {
//                    map.put(key, rs.getString(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
//                    map.put(key, rs.getByte(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
//                    map.put(key, rs.getShort(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
//                    map.put(key, rs.getDate(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.TIME) {
//                    map.put(key, rs.getTime(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
//                    map.put(key, rs.getTimestamp(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.BINARY) {
//                    map.put(key, rs.getBytes(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.VARBINARY) {
//                    map.put(key, rs.getBytes(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.LONGVARBINARY) {
//                    map.put(key, rs.getBinaryStream(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.BIT) {
//                    map.put(key, rs.getBoolean(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.CLOB) {
//                    map.put(key, rs.getClob(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.NUMERIC) {
//                    map.put(key, rs.getBigDecimal(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.DECIMAL) {
//                    map.put(key, rs.getBigDecimal(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.DATALINK) {
//                    map.put(key, rs.getURL(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.REF) {
//                    map.put(key, rs.getRef(i));
//                } else if (rsmd.getColumnType(i) == java.sql.Types.STRUCT) {
//                    map.put(key, rs.getObject(i)); // must be a custom mapping consists of a class that implements the interface SQLData and an entry in a java.util.Map object.
//                } else if (rsmd.getColumnType(i) == java.sql.Types.DISTINCT) {
//                    map.put(key, rs.getObject(i)); // must be a custom mapping consists of a class that implements the interface SQLData and an entry in a java.util.Map object.
//                } else if (rsmd.getColumnType(i) == java.sql.Types.JAVA_OBJECT) {
//                    map.put(key, rs.getObject(i));
//                } else {
//                    map.put(key, rs.getString(i));
//                }
//            }                  
//
//            list.add(map);
//            System.out.println("Record No: " + String.valueOf(list.size()));
//        }
//        rs.close();
//        
//        System.out.println("Creating json using gson...");
//        Gson obj = new Gson();
//        String gson = obj.toJson(list);
//        System.out.println("json created using gson...");
//        
//        return obj.toJson(gson);
//    }           
    
  public static String format(Date date, String format)
  {
//    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sf = new SimpleDateFormat(format);
    return sf.format(date);
  }    
  
  public static String format(Timestamp tme, String format)
  {
//    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sf = new SimpleDateFormat(format);
    return sf.format(tme);
  }    

   public static String fileRead(String filename){
      StringBuilder lsText = new StringBuilder();
      try {
         FileReader reader = new FileReader(filename);
         BufferedReader bufferedReader = new BufferedReader(reader);

         String line;

         while ((line = bufferedReader.readLine()) != null) {
            lsText.append(line);
         }
         reader.close();

      } catch (IOException e) {
         return "";
      }
         return lsText.toString();
   }
   
   public static void fileWrite(String filename, String data){
      try {
          FileWriter writer = new FileWriter(filename, false);
          writer.write(data);
          writer.close();
      } catch (IOException e) {
          e.printStackTrace();
      }      
   }
    
   
    public static void fileWrite(String filename, String data, boolean append){
        try {
            FileWriter writer = new FileWriter(filename, append);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
