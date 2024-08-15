package com.leohabrom.java.framestotxt;

import java.io.*;

public class TxtToOneFile {
    public static void main(String[] args) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (File file : new File("res/txt").listFiles()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
            stringBuilder.append(";");
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("res/frames.txt")));
        writer.write(stringBuilder.toString());
        writer.close();
    }
}
