package com.mycompany.Sistema_Solar;

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;

public class GraficoTemperatura {

    private TimeSeries serieSaida, serieEntrada, serieAmbiente, serieIrradiacao, serieVazao, serieReferencia; // Séries de dados
    private JFreeChart chartSaida, chartEntrada, chartAmbiente, chartIrradiacao, chartVazao; // Gráficos
    private ChartPanel panelSaida, panelEntrada, panelAmbiente, panelIrradiacao, panelVazao; // Painéis dos gráficos

    private int tempoSegundos = 0;

    private boolean usarHoraNoEixoX = false;

    public GraficoTemperatura() {
        // Criação das séries
        serieSaida = new TimeSeries("Temperatura de Saída");
        serieEntrada = new TimeSeries("Temperatura de Entrada");
        serieAmbiente = new TimeSeries("Temperatura Ambiente");
        serieIrradiacao = new TimeSeries("Irradiação Solar");
        serieVazao = new TimeSeries("Vazão de entrada (U)");
        serieReferencia = new TimeSeries("Temperatura de referencia (SP)");
        // Criação dos gráficos
        chartSaida = criarGraficoSaida();
        chartEntrada = criarGraficoEntrada();
        chartAmbiente = criarGraficoAmbiente();
        chartIrradiacao = criarGraficoIrradiacao();
        chartVazao = criarGraficoVazao();
        // Criação dos painéis com os gráficos
        panelSaida = new ChartPanel(chartSaida);
        panelEntrada = new ChartPanel(chartEntrada);
        panelAmbiente = new ChartPanel(chartAmbiente);
        panelIrradiacao = new ChartPanel(chartIrradiacao);
        panelVazao = new ChartPanel(chartVazao);
    }

    public void limparDadosGraficos() {
        if (serieSaida != null) {
            serieSaida.clear();
        }
        if (serieEntrada != null) {
            serieEntrada.clear();
        }
        if (serieAmbiente != null) {
            serieAmbiente.clear();
        }
        if (serieIrradiacao != null) {
            serieIrradiacao.clear();
        }

    }

    // Define se o eixo X dos gráficos deve ser exibido em horas
    public void setHora(boolean usarHora) {
        this.usarHoraNoEixoX = usarHora;
    }

    // Retorna se o eixo X está configurado para exibir em horas
    public boolean isHora() {
        return usarHoraNoEixoX;
    }

    private String horaOrSegundos() {
        if (isHora()) {
            return "Tempo (h)";
        } else {
            return "Tempo (s)";
        }
    }

    public void atualizarRotuloEixoX(JFreeChart chart) {
        XYPlot plot = chart.getXYPlot();
        plot.getDomainAxis().setLabel(horaOrSegundos());
    }

    // Métodos de criação dos gráficos
    private JFreeChart criarGraficoSaida() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(serieSaida);
        dataset.addSeries(serieReferencia); // Adiciona a série de referência

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Temperatura de Saída", horaOrSegundos(), "Temperatura (°C)", dataset,
                true, true, false
        );

        // Configurar o eixo Y para definir um intervalo mínimo
        NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        yAxis.setAutoRangeIncludesZero(false); // Garante que o zero não seja sempre incluído
        yAxis.setRange(0, 80); // Defina um intervalo adequado, ajuste conforme necessário

        return chart;

    }
    // Métodos de criação dos gráficos

    private JFreeChart criarGraficoIrradiacao() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(serieIrradiacao);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Irradiancia Solar", "Tempo (s)", "Wh/m^2", dataset,
                true, true, false
        );

        // Configurar o eixo Y para definir um intervalo mínimo
        NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        yAxis.setAutoRangeIncludesZero(false); // Garante que o zero não seja sempre incluído
        yAxis.setRange(0, 1100); // Defina um intervalo adequado, ajuste conforme necessário

        return chart;
    }

    private JFreeChart criarGraficoEntrada() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(serieEntrada);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Temperatura de Entrada", "Tempo (s)", "Temperatura (°C)", dataset,
                true, true, false
        );

        // Configurar o eixo Y para definir um intervalo mínimo
        return chart;
    }

    private JFreeChart criarGraficoVazao() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(serieVazao);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Vazão de Entrada", "Tempo (s)", "Vazão (%)", dataset,
                true, true, false
        );

        // Configurar o eixo Y para definir um intervalo mínimo
        return chart;
    }

    private JFreeChart criarGraficoAmbiente() {
        TimeSeriesCollection datasetTemperatura = new TimeSeriesCollection();
        datasetTemperatura.addSeries(serieAmbiente);

        TimeSeriesCollection datasetIrradiacao = new TimeSeriesCollection();
        datasetIrradiacao.addSeries(serieIrradiacao);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Temperatura Ambiente e Irradiação Solar",
                "Tempo (s)",
                "Temperatura (°C)",
                datasetTemperatura,
                true, true, false
        );

        XYPlot plot = chart.getXYPlot();

        // Eixo da esquerda (Temperatura)
        NumberAxis yAxisEsquerda = (NumberAxis) plot.getRangeAxis();
        yAxisEsquerda.setRange(0, 80);

        // Eixo da direita (Irradiação)
        NumberAxis yAxisDireita = new NumberAxis("Irradiação (W/m²)");
        yAxisDireita.setRange(0, 1200);
        plot.setRangeAxis(1, yAxisDireita);

        plot.setDataset(1, datasetIrradiacao);
        plot.mapDatasetToRangeAxis(1, 1);

        // 🎨 Renderer para Temperatura
        XYItemRenderer rendererTemp = plot.getRenderer();
        rendererTemp.setSeriesPaint(0, Color.RED); // Temperatura em vermelho

        // 🎨 Renderer para Irradiação
        XYItemRenderer rendererIrradiacao = new StandardXYItemRenderer();
        rendererIrradiacao.setSeriesPaint(0, Color.BLUE); // Irradiação em azul
        plot.setRenderer(1, rendererIrradiacao);

        return chart;
    }

    // Métodos públicos para pegar os painéis
    public ChartPanel criarGraficoSaidaPanel() {
        return panelSaida;
    }

    public ChartPanel criarGraficoEntradaPanel() {
        return panelEntrada;
    }

    public ChartPanel criarGraficoAmbientePanel() {
        return panelAmbiente;
    }

    public ChartPanel criarGraficoIrradiacaoPanel() {
        return panelIrradiacao;
    }

    public ChartPanel criarGraficoVazaoPanel() {
        return panelVazao;
    }

    public JFreeChart getChartSaida() {
        return panelSaida.getChart();
    }

    public JFreeChart getChartVazao() {
        return panelVazao.getChart();
    }

    public JFreeChart getChartEntrada() {
        return panelEntrada.getChart();
    }

    public JFreeChart getChartAmbiente() {
        return panelAmbiente.getChart();
    }

    public JFreeChart getChartIrradiacao() {
        return panelIrradiacao.getChart();
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void atualizarGrafico(double tempSaida, double tempEntrada, double tempAmbiente,
            double irradiacao, double tempoAtual, double vazao, double referencia) {

        // Calcula o tempo total em segundos
        int totalSegundos;

        if (isHora()) {
            // Converte horas (double) para segundos (int)
            // Exemplo: tempoAtual = 1.5 horas → 1h30min → 5400 segundos
            totalSegundos = (int) (tempoAtual * 3600); // 1 hora = 3600 segundos
        } else {
            // Modo padrão: usa o contador de segundos
            totalSegundos = tempoSegundos;
        }

        // Calcula horas, minutos e segundos para criar o objeto Second
        int segundos = totalSegundos % 60;
        int minutos = (totalSegundos / 60) % 60;
        int horas = (totalSegundos / 3600) % 24;

        // Cria um objeto Second (sempre usado, independente do modo)
        Second segundo = new Second(segundos, minutos, horas, 1, 1, 2025);

        // Adiciona os dados (sempre usando Second)
        serieSaida.addOrUpdate(segundo, tempSaida);
        serieEntrada.addOrUpdate(segundo, tempEntrada);
        serieAmbiente.addOrUpdate(segundo, tempAmbiente);
        serieIrradiacao.addOrUpdate(segundo, irradiacao);
        serieVazao.addOrUpdate(segundo, vazao);
        serieReferencia.addOrUpdate(segundo, referencia);

        // Incrementa o contador apenas no modo padrão (segundos)
        if (!isHora()) {
            tempoSegundos++;
        }

        // Atualiza os painéis
        panelSaida.repaint();
        panelEntrada.repaint();
        panelAmbiente.repaint();
        panelVazao.repaint();
    }

    public double lerTemperatura(String mensagem) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mensagem);
        while (true) {
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            } else {
                System.out.print("Entrada inválida! Digite novamente: ");
                scanner.next();
            }
        }
    }

}
