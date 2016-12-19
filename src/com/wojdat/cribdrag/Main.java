package com.wojdat.cribdrag;

import com.wojdat.cribdrag.util.FileReader;
import com.wojdat.cribdrag.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        FileReader fileReader = new FileReader();
        List<String> ciphers = fileReader.readFile("ciphers.txt");
        String xored = StringUtils.xor(ciphers.get(0), ciphers.get(1));
        do {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String guessHex = putCribToOneCipher(xored, bufferedReader);

            putCribToAllCiphers(ciphers, xored, guessHex);

            getKeyFromPlaintext(ciphers, xored);
        } while (true);
    }

    private static String putCribToOneCipher(String xored, BufferedReader bufferedReader) throws IOException {
        String s = bufferedReader.readLine();
        String guessHex = StringUtils.utf8ToHex(s);
        for (int i = 0; i <= xored.length() - guessHex.length(); i += 2) {
            String xorrr = StringUtils.xor(xored.substring(i, i + guessHex.length()), guessHex);
            String utf8 = StringUtils.hexToUtf8(xorrr);
            System.out.println(i / 2);
            System.out.println(utf8);
        }
        return guessHex;
    }

    private static void putCribToAllCiphers(List<String> ciphers, String xored, String guessHex) {
        for (int j = 0; j < 11; j++) {
            String first = ciphers.get(1);
            String second = ciphers.get(j);
            xored = StringUtils.xor(first, second);
            String xor = StringUtils.xor(xored.substring(0, 0 + guessHex.length()), guessHex);
            String utf88 = StringUtils.hexToUtf8(xor);
            System.out.println(utf88);
        }
    }

    private static void getKeyFromPlaintext(List<String> ciphers, String xored) {
        String plaintext = "Pułkownik Ujszaszy był od roku 1934 attaché wojskowym Węgier w Pradze. Z uwagi na pełnioną przez niego rolę był naturalnym obiektem zainteresowania czechosłowackiego kontrwywiadu. Pułkownik prowadził zwykłe życie oficera na placówce. Mieszkał w eleganckiej willi, spał ze swoją piękną pokojówką, pił wino i dawał się obwozić szoferowi po okolicznych przybytkach rozkoszy. Jego asystentem był oficer Kovacs, który podzielał zamiłowanie do podobnego stylu życia. To jego obrano za pierwszy cel operacji. Czeski funkcjonariusz po kilku miesiącach ciężkiej dla wątroby pracy zaprzyjaźnił się z Węgrem i któregoś wieczoru zapytał go, co słychać w pracy. Kovacs odpowiedział, że w zasadzie to strasznie się nudzi, tylko co piątek musi kupować pułkownikowi czeskie znaczki pocztowe. Ta z pozoru mało znacząca informacja okazała się być prawdziwą perłą dla czechosłowackiego kontrwywiadu.Czesi wiedzieli, że w piątki do Pragi przyjeżdża kurier z pocztą dyplomatyczną. Znaczki pułkownika Ujszaszy oznaczały, że być może jego zadaniem jest rozsyłanie niektórych listów wewnątrz Czechosłowacji";
        String[] sSentence = plaintext.split("(?<=[a-z])\\.\\s+");
        List<String> strings = Arrays.asList(sSentence);
        for (int i = 0; i < 11; i++) {
            StringBuilder stringBuilder = new StringBuilder(strings.get(i));
            stringBuilder.append(".");
            String ciph = ciphers.get(i);
            String inHex = StringUtils.utf8ToHex(stringBuilder.toString());
            xored = StringUtils.xor(ciph, inHex);
            System.out.println(xored);
        }
    }

    private static void printList(List<String> ciphers) {
        ciphers.forEach(System.out::println);
    }
}