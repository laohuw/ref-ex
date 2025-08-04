package com.algo.ex.mtls.excrypto;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Arrays;


public class SenderCryptoApp {

    public static void main(String[] args) throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, URISyntaxException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        URL urlMessageFile = SenderCryptoApp.class.getClassLoader().getResource("message.txt");

        byte[] encryptedMessage=digitalDigest(urlMessageFile);

        Boolean result=verifySignature(urlMessageFile, encryptedMessage);
        System.out.println(result);
    }



    private static byte[] digitalDigest(URL urlMessageFile) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, URISyntaxException, UnrecoverableKeyException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        PrivateKey privateKey = getPrivateKey();

        byte[] messageBytes= Files.readAllBytes(Paths.get(urlMessageFile.toURI()));
        byte[] messageHash = digestMessage(messageBytes);

        byte[] hashToEncrypt = encodeMessage(messageHash);

        byte[] encryptedMessageHash = encryptMessage(privateKey, hashToEncrypt);
        System.out.println(encryptedMessageHash);
        return encryptedMessageHash;
    }

    private static byte[] encryptMessage(PrivateKey privateKey, byte[] hashToEncrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptedMessageHash = cipher.doFinal(hashToEncrypt);
        return encryptedMessageHash;
    }

    private static byte[] encodeMessage(byte[] messageHash) throws IOException {
        DigestAlgorithmIdentifierFinder hashAlgorithmFinder =new DefaultDigestAlgorithmIdentifierFinder();
        AlgorithmIdentifier hashAlgorithmIdentifier =hashAlgorithmFinder.find("SHA-256");
        DigestInfo digestInfo=new DigestInfo(hashAlgorithmIdentifier, messageHash);
        byte[] hashToEncrypt= digestInfo.getEncoded();
        return hashToEncrypt;
    }

    private static byte[] digestMessage(byte[] messageBytes) throws NoSuchAlgorithmException {
        MessageDigest md=MessageDigest.getInstance("SHA-256");
        byte[] messageHash=md.digest(messageBytes);
        return messageHash;
    }

    private static PrivateKey getPrivateKey() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, URISyntaxException, UnrecoverableKeyException {
        KeyStore keyStore=KeyStore.getInstance("JKS");
//        InputStream keyStoreStream= ExCryptoApplication.class.getClassLoader().getResourceAsStream("sender_keystore.jks");
        URL resourceFile = SenderCryptoApp.class.getClassLoader().getResource("sender_keystore.jks");
        keyStore.load(new FileInputStream(new File(resourceFile.toURI())), "changeit".toCharArray());
        PrivateKey privateKey= (PrivateKey) keyStore.getKey("senderKeyPair", "changeit".toCharArray());
        System.out.println("privateKey==============");
        System.out.println(privateKey);
        return privateKey;
    }



    private static Boolean verifySignature(URL urlMessageFile, byte[] encryptedMessageHash) throws NoSuchPaddingException, IllegalBlockSizeException, CertificateException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, IOException, BadPaddingException, InvalidKeyException {
        byte[]  decryptMessageHash=decryptMessageHash(encryptedMessageHash);

        byte[] messageBytes= Files.readAllBytes(Paths.get(urlMessageFile.toURI()));
        byte[] messageHash = digestMessage(messageBytes);
        byte[] hashToEncrypt = encodeMessage(messageHash);

        Boolean isCorrect = Arrays.equals(decryptMessageHash, hashToEncrypt);
        return isCorrect;
    }


    private static byte[]  decryptMessageHash(byte[] encryptedMessageHash) throws NoSuchAlgorithmException, NoSuchPaddingException, KeyStoreException, URISyntaxException, IOException, CertificateException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        Certificate certificat=getCertificate();
        cipher.init(Cipher.DECRYPT_MODE, certificat.getPublicKey());
        System.out.println("publicKey==============");
        System.out.println(certificat.getPublicKey());

        byte[] decryptedMessageHash = cipher.doFinal(encryptedMessageHash);
        return decryptedMessageHash;
    }
    public static Certificate getCertificate() throws KeyStoreException, URISyntaxException, IOException, CertificateException, NoSuchAlgorithmException {
        KeyStore keyStore=KeyStore.getInstance("JKS");
        URL resourceFile = ReceiverCryptoApp.class.getClassLoader().getResource("receiver_keystore.jks");
        keyStore.load(new FileInputStream(new File(resourceFile.toURI())), "changeit".toCharArray());
        Certificate certificate = keyStore.getCertificate("receiverKeyPair");
        System.out.println(certificate);
        return certificate;
    }
}
