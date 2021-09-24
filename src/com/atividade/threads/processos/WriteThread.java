package com.atividade.threads.processos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WriteThread {
    
	private static final Integer LIMITE = 1000000;
    private static final List<Integer> BREAKPOINTS = new ArrayList<Integer>(Arrays.asList(100000, 200000, 300000, 400000, 500000, 600000, 700000, 800000, 900000, 1000000));
    private static int count = 0;

    public static void main(String[] args) {

        try {
            escrever();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static File escrever() throws IOException {
        File file = new File("arquivo.txt");
        FileWriter arquivo = new FileWriter(file);
        BufferedWriter buffer = new BufferedWriter(arquivo);

        try {

            for (int i = 1; i <= LIMITE; i++) {
                buffer.write("-" + i);

                if (BREAKPOINTS.contains(i)) {
                    count += 1;
                    buffer.flush();

                    new Thread(() -> {
                        copiarCMDWindows("arquivo.txt", "arquivo" + count + ".txt");
                    }).start();

                    buffer.newLine();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }


    static void copiarCMDWindows(String arq, String destino) {
        try {
            String[] args = { "CMD", "/C", "COPY", "/Y", arq, destino };
            Process p = Runtime.getRuntime().exec(args);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}