package com.wojdat.cribdrag.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class StringUtils {

    private StringUtils() {

    }


    public static String xor(String first, String second) {
        StringBuilder stringBuilder = new StringBuilder("");

        for (int i = 0; i < first.length() && i < second.length(); i++) {
            int firstIntValue, secondIntValue;
            firstIntValue = Integer.parseInt(first.substring(i, i + 1), 16);
            secondIntValue = Integer.parseInt(second.substring(i, i + 1), 16);
            String xoredVal = Integer.toHexString(firstIntValue ^ secondIntValue);
            stringBuilder.append(xoredVal);
        }
        return stringBuilder.toString();
    }

    public static String fillLeft(String xoredVal, int lengthToFill, char character) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int toPrepend = lengthToFill - xoredVal.length(); toPrepend > 0; toPrepend--) {
            stringBuilder.append(character);
        }

        stringBuilder.append(xoredVal);

        return stringBuilder.toString();
    }

    public static String utf8ToHex(String textInUTF8) {
        StringBuilder stringBuilder = new StringBuilder("");
        Charset charset = Charset.forName("UTF-8");
        byte[] bytes = textInUTF8.getBytes(charset);
        for (byte b : bytes) {
            String hexVal = Integer.toHexString(b & 0xFF);
            stringBuilder.append(hexVal);
        }

        return stringBuilder.toString();
    }

    public static String hexToUtf8(String textInHex) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(textInHex.length() / 2);
        for (int i = 0; i < textInHex.length(); i += 2) {
            byteBuffer.put((byte) Integer.parseInt(textInHex.substring(i, i + 2), 16));
        }
        byteBuffer.rewind();
        Charset charset = Charset.forName("UTF-8");
        CharBuffer charBuffer = charset.decode(byteBuffer);
        return charBuffer.toString();
    }
}
