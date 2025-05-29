package com.mycompany.Sistema_Solar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ler_csv {

    String csvFile;
    String line;
    String csvDelimiter = ",";

    public Ler_csv(String csv_in) {
        this.csvFile = csv_in;
    }

    public double[] retorna_dados(double horaBuscada) {
        double[] vetor = {-1, -1}; // valores padrão caso não encontre

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            boolean primeiraLinha = true;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(csvDelimiter);
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String horaSolar = fields[0];

                if (horaSolar.equals(String.format("%02.0f", horaBuscada))) { 
                    vetor[0] = Double.parseDouble(fields[1]); // Irradiação
                    vetor[1] = Double.parseDouble(fields[2]); // Temperatura
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vetor;
    }
}
