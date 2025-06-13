package com.mycompany.Sistema_Solar;

public class Coletor_solar {

    double irradiacao_solar; // G (W/m²)
    private double TEMPERATURA_AMBIENTE; // T_amb (°C)
    private double TEMPERATURA_ENTRADA = TemperaturaAmbiente.chamarTemperaturaAmbiente() - 5; // T_inicial (°C)
    private double vazao; // ṁ (kg/s)
    double ALPHA = 0.5;
    private boolean primeiraExecucao = true;
    private final double vazaoNominal = 0.02;
    private double REFERENCIA;
    private double Kp = 0.5;
    private String CAMINHOCSV;
    private double Ki = 0.1; // Valor inicial, pode ser ajustado
    private double INTEGRAL = 0.0;

    public Coletor_solar(double irradiacao_solar, double temperaturaAmbiente, double porcentagem_vazao, double referencia, String caminhoCSV) {
        this.irradiacao_solar = irradiacao_solar;
        this.TEMPERATURA_AMBIENTE = temperaturaAmbiente;
        this.vazao = (porcentagem_vazao / 100.0) * vazaoNominal;
        this.REFERENCIA = referencia;
        this.CAMINHOCSV = caminhoCSV;
    }

    public void setCaminhoCSV(String caminho) {
        this.CAMINHOCSV = caminho;
    }

    public String getCaminhoCSV() {
        return CAMINHOCSV;
    }

    public double getTemperaturaAmbiente() {

        return TEMPERATURA_AMBIENTE;
    }

    public void setTemperaturaAmbienteManual(double temperatura_ambiente_nova) {
        this.TEMPERATURA_AMBIENTE = temperatura_ambiente_nova;
    }

    public void setTemperaturaAmbiente(double horario, boolean atualizacaoAutomatica) {
        if (!atualizacaoAutomatica) {
            Ler_csv leitor = new Ler_csv(CAMINHOCSV);
            double[] dados = leitor.retorna_dados(horario);
            this.TEMPERATURA_AMBIENTE = dados[1];
        }

    }

    public double getIrradiacao() {
//        if (horario >= 7 && horario < 11) {
//            irradiacao_solar = 250 + (horario - 8) * 180;
//        } else if (horario >= 11 && horario <= 12) {
//            irradiacao_solar = 900 + (horario - 11) * 140;
//        } else if (horario > 12 && horario <= 14) {
//            irradiacao_solar = 1080 - (horario - 12) * 140;
//        } else if (horario > 14 && horario <= 17) {
//            irradiacao_solar = 950 - (horario - 14) * 130;
//        } else if (horario > 17 && horario <= 19) {
//            irradiacao_solar = 250 - (horario - 20) * 110;
//        } else {
//            irradiacao_solar = 50;
//        }

        return irradiacao_solar;
    }

    public void setIrradiacao(double horario) {
        Ler_csv leitor = new Ler_csv(CAMINHOCSV);
        double[] dados = leitor.retorna_dados(horario);
        this.irradiacao_solar = dados[0];
    }

    public void setReferencia(double valorReferencia) {
        this.REFERENCIA = valorReferencia;
    }

    public double getReferencia() {
        return REFERENCIA;
    }

    public double setKp(double Kpnovo) {
        return this.Kp = Kpnovo;
    }

    public void setPorcentagemVazao(double porcentagem) {
        this.vazao = (porcentagem / 100.0) * vazaoNominal;
    }

    public double getPorcentagemVazao() {
        return (vazao / vazaoNominal) * 100.0;
    }

    public void setIrradiacaoSolar(double irradiacao) {
        this.irradiacao_solar = irradiacao;
    }

    public void setVazao(double vazao) {
        this.vazao = vazao;
    }

    private double calcularTemperaturaEntrada(double tempSaida) {
        return (1 - ALPHA) * TEMPERATURA_AMBIENTE + ALPHA * tempSaida;
    }

    public double getTemperaturaEntrada() {
        return TEMPERATURA_ENTRADA;
    }

    public double getKp() {
        return Kp;
    }

    public double getKi() {
        return Ki;
    }

    public void setKi(double KiNovo) {
        this.Ki = KiNovo;
    }

    private double somaErro = 0;

    public void controleVazao(double temperaturaSaida) {
        double erro = temperaturaSaida - REFERENCIA;

        // Faixa morta (histerese)
        double faixa = 0;

        if (Math.abs(erro) > faixa) {
            INTEGRAL += erro; // Ação integral

            double ajuste = Kp * erro + Ki * INTEGRAL;

            double novaPorcentagem = getPorcentagemVazao() + ajuste;

            if (novaPorcentagem > 100) {
                novaPorcentagem = 100;
            } else if (novaPorcentagem < 10) {
                novaPorcentagem = 10;
            }

            setPorcentagemVazao(novaPorcentagem);
            System.out.println("Ajustando vazão para: " + novaPorcentagem + "%");
        } else {
            System.out.println("Dentro da faixa. Vazão mantida em: " + getPorcentagemVazao() + "%");
        }
    }

    public double calcularTemperaturaSaida() {
        final double CALOR_ESPECIFICO_AGUA = 4186;
        final double AREA_COLETOR = 2;
        final double FATOR_REMOVER_CALOR = 0.9;
        final double EFICIENCIA_OPTICA = 0.7;
        final double COEF_PERDA_TERMICA = 4;

        // Calcula energia útil
        double energiaUtil = FATOR_REMOVER_CALOR * AREA_COLETOR
                * (irradiacao_solar * EFICIENCIA_OPTICA - COEF_PERDA_TERMICA * (TEMPERATURA_ENTRADA - TEMPERATURA_AMBIENTE));
        System.out.println("Temperatura de entrada ANTES" + TEMPERATURA_ENTRADA);
        // Calcula saída com base na entrada atual
        double tempSaida = TEMPERATURA_ENTRADA + energiaUtil / (vazao * CALOR_ESPECIFICO_AGUA);

        // Atualiza a temperatura de entrada para a próxima chamada
        TEMPERATURA_ENTRADA = calcularTemperaturaEntrada(tempSaida);
        System.out.println("Temperatura de entrada depois" + TEMPERATURA_ENTRADA);
        return tempSaida;
    }
}
