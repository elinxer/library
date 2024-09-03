package com.dbn.cloud.platform.mqtt.utils;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PasswordFinder;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.X509Certificate;

@Slf4j
public class SslUtils {

    private static X509Certificate getX509Certificate(String fileName) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        PEMReader reader = null;
        try {
            inputStream = SslUtils.class.getClassLoader().getResourceAsStream(fileName);
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new PEMReader(inputStreamReader);
            X509Certificate x509Certificate = (X509Certificate) reader.readObject();
            return x509Certificate;
        } catch (Exception e) {
            log.error("getX509Certificate fileName={},error={}", fileName, e);
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static KeyPair getKeyPair(String fileName, String password) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        PEMReader reader = null;
        try {
            inputStream = SslUtils.class.getClassLoader().getResourceAsStream(fileName);
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new PEMReader(inputStreamReader, new PasswordFinder() {
                @Override
                public char[] getPassword() {
                    return password.toCharArray();
                }
            });
            KeyPair keyPair = (KeyPair) reader.readObject();
            return keyPair;
        } catch (Exception e) {
            log.error("getKeyPair fileName={},error={}", fileName, e);
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static SSLSocketFactory getSocketFactory(final String caCrtFile, final String crtFile, final String keyFile) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        X509Certificate caCert = getX509Certificate(caCrtFile);
        X509Certificate cert = getX509Certificate(crtFile);
        KeyPair key = getKeyPair(keyFile, null);

        // CA certificate is used to authenticate server
        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("ca-certificate", caCert);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(caKs);

        // client key and certificates are sent to server so it can authenticate us
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry("certificate", cert);

        ks.setKeyEntry("private-key", key.getPrivate(), null, new java.security.cert.Certificate[]{cert});
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, null);
        // finally, create SSL socket factory
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        return context.getSocketFactory();
    }

}