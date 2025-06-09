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

        // 🔸 Arredonda para o mais próximo de 0.5 ou inteiro
        double horaArredondada = Math.round(horaBuscada * 2) / 2.0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            boolean primeiraLinha = true;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(csvDelimiter);
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                double horaCSV = Double.parseDouble(fields[0]);

                if (horaCSV == horaArredondada) {
                    vetor[0] = Double.parseDouble(fields[1]); // Irradiação
                    vetor[1] = Double.parseDouble(fields[2]); // Temperatura
                    break;
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return vetor;
    }
}
