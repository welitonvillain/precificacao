package br.com.angeloni.precos.server.config;

import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

public class SSLConfig {

    public static void execute() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509ExtendedTrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType, Socket socket) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType, Socket socket) throws CertificateException {
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType, SSLEngine sslEngine) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType, SSLEngine sslEngine) throws CertificateException {
                    }

                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType)
                            throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType)
                            throws CertificateException {
                    }
                }
        };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier validHosts = (hostname, session) -> true;
        // All hosts will be valid
        HttpsURLConnection.setDefaultHostnameVerifier(validHosts);
        //Disable LDAPS host name validation
        System.setProperty("com.sun.jndi.ldap.object.disableEndpointIdentification", "true");
    }
}