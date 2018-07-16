package com.cqmike.wechat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NetWorkHelper {
	
	public String getHttpsResponsePostBody(String hsUrl,String requestMethod,String body) {
        URL url;
        InputStream is = null;
        String resultData = "";
        try {
            url = new URL(hsUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            TrustManager[] tm = {xtm};
 
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);
 
            con.setSSLSocketFactory(ctx.getSocketFactory());
            con.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
 
            con.setDoInput(true); //����������������������
 
            //��android�б��뽫��������Ϊfalse
            con.setDoOutput(true); //������������������ϴ�
            con.setUseCaches(false); //��ʹ�û���
            if(null!=requestMethod && !requestMethod.equals("")) {
                con.setRequestMethod(requestMethod); //ʹ��ָ���ķ�ʽ
            }
            else{
                con.setRequestMethod("GET"); //ʹ��get����
            }
            
            con.setRequestProperty("Content-Type","application/json");  
            
            DataOutputStream out = new DataOutputStream(con.getOutputStream());   
            String content = body;   
            byte[] outputInBytes = content.getBytes("UTF-8");
            out.write(outputInBytes);   
            out.flush();   
            out.close();   
            
            is = con.getInputStream();   //��ȡ����������ʱ��������������
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine = "";
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }
            System.out.println(resultData);
 
            
            Certificate[] certs = con.getServerCertificates();
 
            int certNum = 1;
 
            for (Certificate cert : certs) {
                X509Certificate xcert = (X509Certificate) cert;
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultData;
    }
 
	
	public String getHttpsResponse(String hsUrl,String requestMethod) {
        URL url;
        InputStream is = null;
        String resultData = "";
        try {
            url = new URL(hsUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            TrustManager[] tm = {xtm};
 
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);
 
            con.setSSLSocketFactory(ctx.getSocketFactory());
            con.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
 
            con.setDoInput(true); //����������������������
 
            //��android�б��뽫��������Ϊfalse
            con.setDoOutput(false); //������������������ϴ�
            con.setUseCaches(false); //��ʹ�û���
            if(null!=requestMethod && !requestMethod.equals("")) {
                con.setRequestMethod(requestMethod); //ʹ��ָ���ķ�ʽ
            }
            else{
                con.setRequestMethod("GET"); //ʹ��get����
            }
            
            is = con.getInputStream();   //��ȡ����������ʱ��������������
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine = "";
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }
            System.out.println(resultData);
 
            
            Certificate[] certs = con.getServerCertificates();
 
            int certNum = 1;
 
            for (Certificate cert : certs) {
                X509Certificate xcert = (X509Certificate) cert;
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultData;
    }
 
	X509TrustManager xtm = new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
 
        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub
 
        }
 
        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub
 
        }
    };
}
