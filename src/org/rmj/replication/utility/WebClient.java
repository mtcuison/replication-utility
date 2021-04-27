//Recommended combination when using java in web: Jetty, Jersy, Jackson

package org.rmj.replication.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import org.json.simple.JSONObject;

public class WebClient {
   static boolean mode_debug = false; 
   public static boolean isHttpURLConOk(String sURL){
        boolean Ok = false;
        HttpURLConnection conn = null;
        URL url = null;

        try {
            url = new URL(sURL);
            conn = (HttpURLConnection) url.openConnection();
            if(mode_debug) System.out.println(conn.toString());
            Ok = true;
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        conn.disconnect(); 
        return Ok;
    }

    public static boolean isHttpsURLConOk(String sURL){
        boolean Ok = false;
        HttpsURLConnection conn = null;
        URL url = null;

        try {
            url = new URL(sURL);
            conn = (HttpsURLConnection) url.openConnection();
            Ok = true;
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        conn.disconnect(); 
        return Ok;
    }
    
    public static String httpGetJSon(String sURL) throws MalformedURLException, IOException{
        //HttpsURLConnection conn = null;
        HttpURLConnection conn = null;
        StringBuilder lsResponse = new StringBuilder();
        URL url = null;
        
        //Open network IO 
        url = new URL(sURL);

        //opens a connection, then sends GET & set HTTP header nicely
        //conn = (HttpsURLConnection) url.openConnection();
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        //redirects, server errors?
        if (!(conn.getResponseCode() == HttpsURLConnection.HTTP_CREATED ||
              conn.getResponseCode() == HttpsURLConnection.HTTP_OK)) {
            JSONObject oErr = new JSONObject();
            oErr.put("code", String.valueOf(conn.getResponseCode()));
            oErr.put("message", String.valueOf(conn.getResponseMessage()));
                    
            JSONObject oJson = new JSONObject();
            oJson.put("result", "error");
            oJson.put("error", oErr);
            return oJson.toJSONString();            
//            System.setProperty("store.error.code", String.valueOf(conn.getResponseCode()));
//            System.setProperty("store.error.info", String.valueOf(conn.getResponseMessage()));
//            return null;
        }

        //Read response from server
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        while ((output = br.readLine()) != null) {
            lsResponse.append(output);
        }

        conn.disconnect(); 
        
        return lsResponse.toString();
    }

    public static String httpsGetJSon(String sURL) throws MalformedURLException, IOException{
        HttpsURLConnection conn = null;
        StringBuilder lsResponse = new StringBuilder();
        URL url = null;
        
        //Open network IO 
        url = new URL(sURL);

        //opens a connection, then sends GET & set HTTP header nicely
        conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        //redirects, server errors?
        if (!(conn.getResponseCode() == HttpsURLConnection.HTTP_CREATED ||
              conn.getResponseCode() == HttpsURLConnection.HTTP_OK)) {
            JSONObject oErr = new JSONObject();
            oErr.put("code", String.valueOf(conn.getResponseCode()));
            oErr.put("message", String.valueOf(conn.getResponseMessage()));
                    
            JSONObject oJson = new JSONObject();
            oJson.put("result", "error");
            oJson.put("error", oErr);
            return oJson.toJSONString();            
//            System.setProperty("store.error.code", String.valueOf(conn.getResponseCode()));
//            System.setProperty("store.error.info", String.valueOf(conn.getResponseMessage()));
//            return null;
        }

        //Read response from server
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        while ((output = br.readLine()) != null) {
            lsResponse.append(output);
        }

        conn.disconnect(); 
        
        return lsResponse.toString();
    }
    
    public static String httpGet(String sURL) throws MalformedURLException, IOException{
        //HttpsURLConnection conn = null;
        HttpURLConnection conn = null;
        StringBuilder lsResponse = new StringBuilder();
        URL url = null;
        
        //Open network IO 
        url = new URL(sURL);

        //opens a connection, then sends GET & set HTTP header nicely
        //conn = (HttpsURLConnection) url.openConnection();
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        //redirects, server errors?
        if (!(conn.getResponseCode() == HttpsURLConnection.HTTP_CREATED ||
              conn.getResponseCode() == HttpsURLConnection.HTTP_OK)) {
            JSONObject oErr = new JSONObject();
            oErr.put("code", String.valueOf(conn.getResponseCode()));
            oErr.put("message", String.valueOf(conn.getResponseMessage()));
                    
            JSONObject oJson = new JSONObject();
            oJson.put("result", "error");
            oJson.put("error", oErr);
            return oJson.toJSONString();            
//            System.setProperty("store.error.code", String.valueOf(conn.getResponseCode()));
//            System.setProperty("store.error.info", String.valueOf(conn.getResponseMessage()));
//            return null;
        }

        //Read response from server
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        while ((output = br.readLine()) != null) {
            lsResponse.append(output);
        }

        conn.disconnect(); 
        
        return lsResponse.toString();
    }

    public static String httpsGet(String sURL) throws MalformedURLException, IOException{
        HttpsURLConnection conn = null;
        StringBuilder lsResponse = new StringBuilder();
        URL url = null;
        
        //Open network IO 
        url = new URL(sURL);

        //opens a connection, then sends GET & set HTTP header nicely
        //conn = (HttpsURLConnection) url.openConnection();
        conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        //redirects, server errors?
        if (!(conn.getResponseCode() == HttpsURLConnection.HTTP_CREATED ||
              conn.getResponseCode() == HttpsURLConnection.HTTP_OK)) {
            JSONObject oErr = new JSONObject();
            oErr.put("code", String.valueOf(conn.getResponseCode()));
            oErr.put("message", String.valueOf(conn.getResponseMessage()));
                    
            JSONObject oJson = new JSONObject();
            oJson.put("result", "error");
            oJson.put("error", oErr);
            return oJson.toJSONString();            
//            System.setProperty("store.error.code", String.valueOf(conn.getResponseCode()));
//            System.setProperty("store.error.info", String.valueOf(conn.getResponseMessage()));
//            return null;
        }

        //Read response from server
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        while ((output = br.readLine()) != null) {
            lsResponse.append(output);
        }

        conn.disconnect(); 
        
        return lsResponse.toString();
    }

    public static String httpsGet(String sURL, HashMap<String, String> headers) throws MalformedURLException, IOException{
        HttpsURLConnection conn = null;
        StringBuilder lsResponse = new StringBuilder();
        URL url = null;
        
        //Open network IO 
        url = new URL(sURL);

        //opens a connection, then sends GET & set HTTP header nicely
        //conn = (HttpsURLConnection) url.openConnection();
        conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        if(headers != null){
            Set<Map.Entry<String, String>> entrySet = headers.entrySet();

            for(Map.Entry<String, String> entry : entrySet) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }             
        }
        
        if (!(conn.getResponseCode() == HttpURLConnection.HTTP_CREATED ||
              conn.getResponseCode() == HttpURLConnection.HTTP_OK)) {
            JSONObject oErr = new JSONObject();
            oErr.put("code", String.valueOf(conn.getResponseCode()));
            oErr.put("message", String.valueOf(conn.getResponseMessage()));
                    
            JSONObject oJson = new JSONObject();
            oJson.put("result", "error");
            oJson.put("error", oErr);
            return oJson.toJSONString();            
//            System.setProperty("store.error.code", String.valueOf(conn.getResponseCode()));
//            System.setProperty("store.error.info", String.valueOf(conn.getResponseMessage()));
//            return null;
        }        
        
        //Read response from server
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        while ((output = br.readLine()) != null) {
            lsResponse.append(output);
        }

        conn.disconnect(); 
        
        return lsResponse.toString();
    }
    
    public static String httpPostJSon(String sURL, String sJSon) throws MalformedURLException, IOException{
        HttpURLConnection conn = null;
        StringBuilder lsResponse = new StringBuilder();
        URL url = null;
        
        //Open network IO 
        url = new URL(sURL);

        //opens a connection, then sends POST & set HTTP header nicely
        conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
        bw.write(sJSon);
        bw.flush();
        bw.close();

        if (!(conn.getResponseCode() == HttpsURLConnection.HTTP_CREATED ||
              conn.getResponseCode() == HttpsURLConnection.HTTP_OK)) {
            JSONObject oErr = new JSONObject();
            oErr.put("code", String.valueOf(conn.getResponseCode()));
            oErr.put("message", String.valueOf(conn.getResponseMessage()));
                    
            JSONObject oJson = new JSONObject();
            oJson.put("result", "error");
            oJson.put("error", oErr);
            return oJson.toJSONString();            
//            System.setProperty("store.error.code", String.valueOf(conn.getResponseCode()));
//            System.setProperty("store.error.info", String.valueOf(conn.getResponseMessage()));
//            return null;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

        String output;
        while ((output = br.readLine()) != null) {
                lsResponse.append(output);
        }
        conn.disconnect(); 

        return lsResponse.toString();
    }
    
    public static String httpsPostJSon(String sURL, String sJSon) throws MalformedURLException, IOException{
        HttpsURLConnection conn = null;
        StringBuilder lsResponse = new StringBuilder();
        URL url = null;
        
        //Open network IO 
        url = new URL(sURL);

        //opens a connection, then sends POST & set HTTP header nicely
        conn = (HttpsURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
        bw.write(sJSon);
        bw.flush();
        bw.close();
        
        if (!(conn.getResponseCode() == HttpsURLConnection.HTTP_CREATED ||
              conn.getResponseCode() == HttpsURLConnection.HTTP_OK)) {
            JSONObject oErr = new JSONObject();
            oErr.put("code", String.valueOf(conn.getResponseCode()));
            oErr.put("message", String.valueOf(conn.getResponseMessage()));
                    
            JSONObject oJson = new JSONObject();
            oJson.put("result", "error");
            oJson.put("error", oErr);
            return oJson.toJSONString();            
//            System.setProperty("store.error.code", String.valueOf(conn.getResponseCode()));
//            System.setProperty("store.error.info", String.valueOf(conn.getResponseMessage()));
//            return null;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

        String output;
        while ((output = br.readLine()) != null) {
                lsResponse.append(output);
        }
        conn.disconnect(); 

        return lsResponse.toString();
    }

    //kalyptus - 2018.11.22 11:57pm
    //The header part is intended for Knox and the likes
    //If the method returns null, please check the store.error.info 
    public static String httpsPostJSon(String sURL, String sJSon, HashMap<String, String> headers) throws MalformedURLException, IOException{
        if (sURL.substring(0, 5).equalsIgnoreCase("https")){
            System.setProperty("store.error.code", "");
            System.setProperty("store.error.info", "");

            HttpsURLConnection conn = null;
            StringBuilder lsResponse = new StringBuilder();
            URL url = null;

            //Open network IO 
            url = new URL(sURL);

            //opens a connection, then sends POST & set HTTP header nicely
            conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            if(headers != null){
                Set<Map.Entry<String, String>> entrySet = headers.entrySet();

                for(Map.Entry<String, String> entry : entrySet) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }     
            }        

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            bw.write(sJSon);
            bw.flush();
            bw.close();

            if (!(conn.getResponseCode() == HttpsURLConnection.HTTP_CREATED ||
                  conn.getResponseCode() == HttpsURLConnection.HTTP_OK)) {
                JSONObject oErr = new JSONObject();
                oErr.put("code", String.valueOf(conn.getResponseCode()));
                oErr.put("message", String.valueOf(conn.getResponseMessage()));

                JSONObject oJson = new JSONObject();
                oJson.put("result", "error");
                oJson.put("error", oErr);
                return oJson.toJSONString();            
    //            System.setProperty("store.error.code", String.valueOf(conn.getResponseCode()));
    //            System.setProperty("store.error.info", String.valueOf(conn.getResponseMessage()));
    //            return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                    lsResponse.append(output);
            }
            conn.disconnect(); 

            return lsResponse.toString();
        } else {
            return httpPostJSon(sURL, sJSon, headers);
        }
    }
    
    public static String httpPostJSon(String sURL, String sJSon, HashMap<String, String> headers) throws MalformedURLException, IOException{
        HttpURLConnection conn = null;
        StringBuilder lsResponse = new StringBuilder();
        URL url = null;
        
        //Open network IO 
        url = new URL(sURL);

        //opens a connection, then sends POST & set HTTP header nicely
        conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");

        if(headers != null){
            Set<Map.Entry<String, String>> entrySet = headers.entrySet();

            for(Map.Entry<String, String> entry : entrySet) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }     
        }        
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
        bw.write(sJSon);
        bw.flush();
        bw.close();
        
        if (!(conn.getResponseCode() == HttpsURLConnection.HTTP_CREATED ||
              conn.getResponseCode() == HttpsURLConnection.HTTP_OK)) {
            JSONObject oErr = new JSONObject();
            oErr.put("code", String.valueOf(conn.getResponseCode()));
            oErr.put("message", String.valueOf(conn.getResponseMessage()));
                    
            JSONObject oJson = new JSONObject();
            oJson.put("result", "error");
            oJson.put("error", oErr);
            return oJson.toJSONString();            
//            System.setProperty("store.error.code", String.valueOf(conn.getResponseCode()));
//            System.setProperty("store.error.info", String.valueOf(conn.getResponseMessage()));
//            return null;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

        String output;
        while ((output = br.readLine()) != null) {
                lsResponse.append(output);
        }
        conn.disconnect(); 
        //System.out.print("And2 ba?");
        return lsResponse.toString();
    }
}
