package com.wojdat.rsa;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * Created by sw on 2017-01-14.
 */
public class RSA {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        byte[] pk = publicKey.getEncoded();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String pkBase64 = base64Encoder.encode(pk);
        Files.write(Paths.get("src/com/wojdat/rsa/publicKey.pk"), Arrays.asList(pkBase64), Charset.forName("UTF-8"), StandardOpenOption.CREATE);
        String givenKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjv6/p0FXuUR+G8cp1eKkWe6IjofcftdktHPlE+26dpapV4cf96SUHtNg0bQql4i9xSqLZZNX3I8lLmzF9bv1xlnMnpg330ym7uHEeImwVaw4AruUkeiEg0kbCN/56UctmTQRNLz8Ts84kVVTvaCPlDCJN3qVxxGP687yhBKIrtsbi6IuIR2iq1J9Jteszy4FQhJgoj8GzysT4P/VHo9AG8ETdoAnrSnDcVZNF5yCY5BhJy5PlAv1C6ZJCTP3iHFfjCaN0shaRh6Jm6QWpRVX/seppPHtPflc9b0Uu1Kd0zG5p5goNmqVid6zLnMDac1iNS73/dkpfhGT9KM+ASiabwIDAQAB";
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] givenDecoded = base64Decoder.decodeBuffer(givenKey);
        PublicKey givenPK = KeyFactory
                .getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(givenDecoded));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, givenPK);

        byte[] inputFile = Files.readAllBytes(Paths.get("src/com/wojdat/rsa/input1.txt"));
        cipher.update(inputFile);
        byte[] ciphered = cipher.doFinal();
        String cipheredHex = HexBin.encode(ciphered);
        Files.write(Paths.get("src/com/wojdat/rsa/cipheredHex.txt"), Arrays.asList(cipheredHex), StandardOpenOption.CREATE);
    }
}
