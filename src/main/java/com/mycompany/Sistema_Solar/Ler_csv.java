package com.mycompany.Sistema_Solar;

import java.io.BufferedReader;
import java.io.InputStream; // Importar InputStream
import java.io.InputStreamReader; // Importar InputStreamReader
import java.io.IOException;
import java.nio.charset.StandardCharsets; // Importar para especificar a codificação de caracteres

public class Ler_csv {

    String csvResourcePath; // Mudamos o nome para refletir que é um caminho de recurso
    String line;
    String csvDelimiter = ",";

    public Ler_csv(String csv_in) {
        // O caminho passado para o construtor agora deve ser o caminho do recurso (ex: "/com/mycompany/Sistema_Solar/dados.csv")
        this.csvResourcePath = csv_in;
    }

    public double[] retorna_dados(double horaBuscada) {
        double[] vetor = {-1, -1}; // valores padrão caso não encontre

        // 🔸 Arredonda para o mais próximo de 0.5 ou inteiro
        double horaArredondada = Math.round(horaBuscada * 2) / 2.0;

        try (
            // 💡 A principal mudança está aqui: Usamos getClass().getResourceAsStream() para ler o recurso do classpath
            InputStream is = getClass().getResourceAsStream(csvResourcePath);
            // Sempre especifique a codificação de caracteres do seu CSV (UTF-8 é a mais comum e recomendada)
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
        ) {
            if (is == null) {
                // Se o InputStream for nulo, significa que o recurso não foi encontrado no classpath
                System.err.println("Erro: Recurso CSV não encontrado no classpath: " + csvResourcePath);
                return vetor; // Retorna valores padrão ou lança uma exceção, dependendo da sua necessidade
            }

            boolean primeiraLinha = true;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(csvDelimiter);
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue; // Pula a primeira linha (cabeçalho)
                }

                // Certifique-se de que há campos suficientes antes de tentar acessá-los
                if (fields.length < 3) {
                    System.err.println("Aviso: Linha mal formatada no CSV: " + line);
                    continue; // Pula para a próxima linha
                }

                double horaCSV = Double.parseDouble(fields[0]);

                if (horaCSV == horaArredondada) {
                    vetor[0] = Double.parseDouble(fields[1]); // Irradiação
                    vetor[1] = Double.parseDouble(fields[2]); // Temperatura
                    break; // Encontrou a linha, pode parar de ler
                }
            }
        } catch (IOException | NumberFormatException e) {
            // Imprime o stack trace para depuração. Em produção, você pode querer logar ou tratar de forma diferente.
            e.printStackTrace();
            System.err.println("Ocorreu um erro ao ler o arquivo CSV: " + e.getMessage());
        }

        return vetor;
    }
}