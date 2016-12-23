package com.wojdat.aes;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;


public class AesEncryptor {

    private static final int KEY = 0;
    private static final int IV = 1;
    private static final boolean APPEND = true;


    public static String getRandomHexString(int length) {
        SecureRandom r = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() < length) {
            stringBuilder.append(Integer.toHexString(r.nextInt()));
        }
        return stringBuilder.toString().substring(0,length);
    }

    public static byte[] encryptAES(byte[] key, byte[] iv, String cipherMode) {
        try {
            byte[] msg = "Thomas Jefferson powiedział, że zachowanie wolności wymaga od nas „wiecznej czujności”. Zachowanie prywatności i bezpieczeństwa w społeczeństwie, w którym informacja przelicza się na pieniądz wymaga nie mniejszego wysiłku.".getBytes();

            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance(cipherMode);

            if(cipherMode.equals("AES/ECB/PKCS5Padding")) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            }
            else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            }

            return cipher.doFinal(msg);
        } catch (Exception e) {
            System.out.println("Exception during encryption: " + e);
        }
        return null;
    }

    public static void putKeysToFile() throws IOException, URISyntaxException {
        File file = new File("src/com/wojdat/aes/keys.txt");
        Path path = Paths.get("src/com/wojdat/aes/keys.txt");
        if(!file.exists()) {
            byte[] key = DatatypeConverter.parseHexBinary(getRandomHexString(64));
            byte[] iv = DatatypeConverter.parseHexBinary(getRandomHexString(32));
            Files.write(path, key, StandardOpenOption.CREATE_NEW);
            Files.write(path, iv, StandardOpenOption.APPEND);
        }
    }

    public static void putCiphersToFile(byte[]... ciphers) throws IOException {
        File file = new File("src/com/wojdat/aes/ciphers.txt");
        Path path = Paths.get("src/com/wojdat/aes/ciphers.txt");
        if(!file.exists()) {
            file.createNewFile();
            for(byte[] b : ciphers) {
                Files.write(path, b, StandardOpenOption.APPEND);
            }
        }
    }

    public static byte[] getBytesFromFile(int keyOrIv) throws IOException {
        File file = new File("src/com/wojdat/aes/keys.txt");
        int size = keyOrIv == 0 ? 32 : 16;
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] result = new byte[size];
            fileInputStream.skip(keyOrIv * 32);
            fileInputStream.read(result);
            return result;
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        putKeysToFile();
        byte[] key = getBytesFromFile(KEY);
        byte[] iv = getBytesFromFile(IV);
        for(byte b : iv) {
            System.out.print(b);
        }
        byte[] ecb = encryptAES(key, iv, "AES/ECB/PKCS5Padding");
        byte[] cbc = encryptAES(key, iv, "AES/CBC/PKCS5Padding");
        byte[] ctr = encryptAES(key, iv, "AES/CTR/NoPadding");

        putCiphersToFile(ecb, cbc, ctr);
    }
}
