package com.wojdat.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class AesDecryptor {

    private static byte[] decryptECBPKCS5() {
        try {
            byte[] key = "e84bacfd200589c6f4a82f2c30cb1aa8".getBytes("UTF-8");
            byte[] encrypted = "a933759ad61dbd78ead800af4be7e1baf99046c35a5b0e9c5efd09748efb69c2559c2e0bcc1218824993532eeb295be81782210f9d0e1e445e82359a517979151f85f69a05aeefe9e7925e2239cb2ea69c34b3a3cbd2e896d9815be7e75ba9e4c052421b10ce74a994b8eb0376f9adcfc7856e413a6b07b6ca1db125b8906583a3404bb4e2f62a082493ded36669dc0fb8e08e13e24e8e64122f39137f4919337ca02b75e06693fedd726f6195bf8f277018968b6a3561c362eb96c36887ad7cb10719e2818b91947449664be34b2803f42a6c6df35b6fe487f9a3826be09afe53ca924688ab43cfec12040e8b4febfb22ef79fa856fcaf6f6a0239d72dd8699d72690af3015478f4673292a0c30b2912a631a311b8220389c6128babbe573981fd3185533c7da85f9c1bf08108e734bcf0f52226d71d62221797c2bbda539ae".getBytes("UTF-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decryptCBCPKC5() {
        try {
            byte[] key = "357195aadae63754cb8c5c534e8016a7".getBytes("UTF-8");
            byte[] iv = "a582ce2930d079e7".getBytes("UTF-8");
            byte[] msg = "982382205363da9352cf51e36ba023e57edc12bd8a5c01f904ac4516653bc1b3f642cefc141590ccea3aef77ff20bc03da35f0a09661604e246ab4fdcf80611b62c67c525e104b4cedd8a76aa1748bcc7fb5d74fd42b271b0118c945ee13646d41939f1cad2e212160172c53a312e3b41e362f5844aeb1ad525713310395f62abc94603de8afcb6979b3cd9c1b9fd6566527012abb547c7f77a8c55f3d5bcea0db01d1563c918762a1332db3edc544d8945c0d96a1e229230231c16e15156477910a24c94d812bb41cd881c784bd0f2c1849436e56887b2a66d1f44ce73a27a9".getBytes("UTF-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            return cipher.doFinal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decryptCTR() {
        try {
            byte[] key = "892c8a7b3dcde74bcac72a64ad93072c".getBytes("UTF-8");
            byte[] iv = "ea2f4574b6ab4306".getBytes("UTF-8");
            byte[] msg = "f14379765c8e8433c782e04e1176acabe8a6cde21a7d8b59c62d92d3ea8eefa5594ac43ba123bf9f9bf49196c002c36432794430a77ebdc3fdbe0cf407b7556323ed640b1dfa37460a1de265941b0b8844438b5690cd3b74e8795fe1248339839780cb5b4f0ee3c878e402864c00de050b39a748bf0fbf9e1669fcb2716827523c70fecb1e5d84fa69698e47d28c81c912cc54d962462476ad0a218fc044d469ebee304a5e51490bee8c581da7861ffc18f347d13b28000971837066113815acd529fc9ea74f4d4732288e4ab2a9b51504891461f6e1c752fab2f224bfdec7b377d3f4b3a2d0f507760cfc2c9cc4d6019fb7086837297480405b79f954637ccfb6daa4".getBytes("UTF-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            return cipher.doFinal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String encodeToUTF8(byte[] input) {
        Charset charset = Charset.forName("UTF-8");
        ByteBuffer charBuffer = charset.encode(new String(input));
        return charBuffer.toString();
    }

    public static void main(String[] args) {
        byte[] byteArray = decryptECBPKCS5();
        System.out.println(encodeToUTF8(byteArray));
        byteArray = decryptCBCPKC5();
        System.out.println(encodeToUTF8(byteArray));
        byteArray = decryptCTR();
        System.out.println(encodeToUTF8(byteArray));
    }
}
