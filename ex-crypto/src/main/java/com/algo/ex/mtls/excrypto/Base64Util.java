package com.algo.ex.mtls.excrypto;

//import org.apache.commons.codec.binary.Hex;

import org.bouncycastle.util.encoders.Hex;

import java.util.Base64;
import java.util.HexFormat;

public class Base64Util {
    public static void main(String[] args){
        String msg="{\"alg\":\"HS256\",\"typ\":\"JWT\"}";

        String encodedMsg=Base64.getEncoder().encodeToString(msg.getBytes());
        System.out.println("Base64 Enocder Encoded Message is: "+encodedMsg);

        String encodedMsgUrlSafe=Base64.getUrlEncoder().encodeToString(msg.getBytes());
        System.out.println("Base64 UrlEnocder Encoded Message is: "+encodedMsg);

        byte[] orignMsg=Base64.getDecoder().decode(encodedMsg);
        HexFormat hexFormat=HexFormat.of();

        System.out.println("Decoded Message is: "+ new String(orignMsg));
        String strDecoded=hexFormat.formatHex(orignMsg);
        System.out.println("hexFormat decoded Message is: "+ strDecoded);

    }


//    public static byte[] encode(String message){
//        String enencodedMsg=Base64.getEncoder().encodeToString(message.getBytes());
//        return enencodedMsg.getBytes();
//    }
}
