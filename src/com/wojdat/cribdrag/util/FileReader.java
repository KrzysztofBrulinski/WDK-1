package com.wojdat.cribdrag.util;

import jdk.internal.util.xml.impl.ReaderUTF8;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {
    
    public List<String> readFile(String filePath) {
        
        InputStream inputStream = this.getClass().getResourceAsStream("ciphers.txt");
        BufferedReader bufferedReader = new BufferedReader(new ReaderUTF8(inputStream));
        return bufferedReader
                .lines()
                .collect(Collectors.toList());
    }
}