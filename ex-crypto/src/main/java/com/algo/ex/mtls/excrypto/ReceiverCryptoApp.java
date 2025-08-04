package com.algo.ex.mtls.excrypto;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class ReceiverCryptoApp {
    public static void main(String[] args) throws KeyStoreException, URISyntaxException, IOException, CertificateException, NoSuchAlgorithmException {
//        Certificate certificat=getPublicCertificate();
        System.out.println(KeyStore.getDefaultType());
    }

//    private static Certificate getPublicCertificate() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, URISyntaxException {
//        KeyStore keyStore=KeyStore.getInstance("JKS");
//        URL resourceFile = ReceiverCryptoApp.class.getClassLoader().getResource("receiver_keystore.jks");
//        keyStore.load(new FileInputStream(new File(resourceFile.toURI())), "changeit".toCharArray());
//        Certificate certificate = keyStore.getCertificate("receiverKeyPair");
//        System.out.println(certificate);
//        return certificate;
//    }
//
//    private static Boolean verifySignature(URL urlMessageFile, byte[] encryptedMessageHash) throws NoSuchPaddingException, NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException, URISyntaxException {
//        Cipher cipher = Cipher.getInstance("RSA");
//        Certificate certificat=getPublicCertificate();
//        cipher.init(Cipher.DECRYPT_MODE, certificat.getPublicKey());
//        byte[] decryptedMessageHash = cipher.doFinal(encryptedMessageHash);
//        return null;
//    }
}
