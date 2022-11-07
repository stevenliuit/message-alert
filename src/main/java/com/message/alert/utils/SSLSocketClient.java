package com.message.alert.utils;
import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


public class SSLSocketClient {

  public static SSLSocketFactory getSSLSocketFactory() {
    try {
      SSLContext sslContext = SSLContext.getInstance("SSL");
      sslContext.init(null, getTrustManager(), new SecureRandom());
      return sslContext.getSocketFactory();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static TrustManager[] getTrustManager() {
    return new TrustManager[]{
      new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
          return new X509Certificate[]{};
        }
      }
    };
  }

  public static HostnameVerifier getHostnameVerifier() {
    return (s, sslSession) -> true;
  }
}
