/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.Sistema_Solar;

/**
 *
 * @author Davil
 */
public class Main {
    public static void main(String[] args) {
        // Inicia a interface gráfica
        java.awt.EventQueue.invokeLater(() -> {
            new Sistema_Solar_Interface().setVisible(true);
        });
    }
}
//public class Main {
//
//    public static void main(String[] args) {
//        Ler_csv leitor = new Ler_csv("C:\\Users\\Davil\\OneDrive\\Documentos\\UFSC\\Projeto_coletor\\modelo_coletor_solar_malha_fechada\\src\\main\\java\\com\\mycompany\\Sistema_Solar\\dados.csv");
//        for (double i = 0; i <= 23;) {
//            double[] dados = leitor.retorna_dados(i);
//
//            if (dados[0] != -1) {
//                System.out.println("Irradiação: " + dados[0]);
//                System.out.println("Temperatura: " + dados[1]);
//            } else {
//                System.out.println("Hora não encontrada no CSV.");
//            }
//            
//            i += 0.5;
//        }
//
//    }
//}
