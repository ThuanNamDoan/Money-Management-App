package GUI;

import java.awt.Color;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.demo.charts.ExampleChart;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class AreaChartComp implements ExampleChart<XYChart> {
    String nameChart;
    String nameSeries;
    List<Double> xData;
    List<Double> yData;

    public AreaChartComp() {
    }
    public AreaChartComp(String nameChart, String nameSeries, List<Double> xData, List<Double> yData) {
        this.nameChart = nameChart;
        this.nameSeries = nameSeries;
        this.xData = xData;
        this.yData = yData;
    }

    @Override
    public XYChart getChart() {
        // Create Chart
        XYChart chart =
                new XYChartBuilder()
                        .width(500)
                        .height(300)
                        .title(nameChart)
                        .xAxisTitle("X")
                        .yAxisTitle("Y")
                        .build();

        // Customize Chart
        chart.getStyler().setAxisTitlesVisible(false);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Area);
        chart.getStyler().setChartBackgroundColor(new Color (255,255,255));
        chart.getStyler().setYAxisDecimalPattern("");

        // Series
        chart.addSeries(nameSeries, xData, yData);

        return chart;
    }

    @Override
    public String getExampleChartName() {
        return getClass().getSimpleName() + " - Histogram Not Overlapped with Tool Tips";
    }

    public static void main(String[] args) {
        // Series
        List<Double> xData = new ArrayList<>(Arrays.asList(new Double[] {1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d, 11d, 12d}));

        List<Double> yData = new ArrayList<Double>();
        yData.add(1000000d);
        yData.add(4000000d);
        yData.add(2500000d);
        yData.add(7000000d);
        yData.add(10000000d);
        yData.add(7000000d);
        yData.add(8000000d);
        yData.add(9000000d);
        yData.add(2000000d);
        yData.add(5000000d);
        yData.add(5000000d);
        yData.add(6000000d);

        ExampleChart<XYChart> exampleChart = new AreaChartComp("Thống kê tài sản", "Tài sản", xData, yData);
        XYChart chart = exampleChart.getChart();
        new SwingWrapper<>(chart).displayChart();
    }
}