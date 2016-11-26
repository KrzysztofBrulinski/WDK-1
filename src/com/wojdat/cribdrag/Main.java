package com.wojdat.cribdrag;

import com.wojdat.cribdrag.util.FileReader;
import com.wojdat.cribdrag.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        FileReader fileReader = new FileReader();
        List<String> ciphers = fileReader.readFile("ciphers.txt");

        do {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String s = bufferedReader.readLine();
                String guessHex = StringUtils.utf8ToHex(s);
                String first = ciphers.get(9);
                String second = ciphers.get(10);
                String xored = StringUtils.xor(first, second);
                for (int i = 0; i <= xored.length() - guessHex.length(); i += 2) {
                    String xorrr = StringUtils.xor(xored.substring(i, i + guessHex.length()), guessHex);
                    String utf8 = StringUtils.hexToUtf8(xorrr);
                    System.out.println(i / 2);
                    System.out.println(utf8);
                }
            } catch (IOException e) {
            }
        } while (true);
    }
}