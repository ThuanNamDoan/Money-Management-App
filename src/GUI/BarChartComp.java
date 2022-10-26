package GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler;

public class BarChartComp implements ExampleChart<CategoryChart> {

    private String nameChart;
    private String xTitle;
    private String yTitle;
    private String nameSeries1;
    private List<Double> xData1;
    private List<Double> yData1;
    private String nameSeries2;
    private List<Double> xData2;
    private List<Double> yData2;

    public BarChartComp(String nameChart, String xTitle, String yTitle, String nameSeries1, List<Double> xData1, List<Double> yData1,
                        String nameSeries2, List<Double> xData2, List<Double> yData2) {
        this.nameChart = nameChart;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        this.nameSeries1 = nameSeries1;
        this.xData1 = xData1;
        this.yData1 = yData1;
        this.nameSeries2 = nameSeries2;
        this.xData2 = xData2;
        this.yData2 = yData2;
    }

    @Override
    public CategoryChart getChart() {

        // Create Chart
        CategoryChart chart =
                new CategoryChartBuilder()
                        .width(500)
                        .height(300)
                        .title(nameChart)
                        .xAxisTitle(xTitle)
                        .yAxisTitle(yTitle)
                        .build();

        // Customize Chart
        chart.getStyler().setAvailableSpaceFill(.96);
        chart.getStyler().setPlotGridVerticalLinesVisible(false);
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setToolTipType(Styler.ToolTipType.yLabels);
        chart.getStyler().setChartBackgroundColor(new Color (255,255,255));
        chart.getStyler().setAvailableSpaceFill(0.5);
        chart.getStyler().setYAxisDecimalPattern("");

        // Series
        chart.addSeries(nameSeries1, xData1, yData1);
        chart.addSeries(nameSeries2, xData2, yData2);

        return chart;
    }


    @Override
    public String getExampleChartName() {

        return getClass().getSimpleName() + " - Histogram Not Overlapped with Tool Tips";
    }

    public static void main(String[] args) {
        // Column 1
        List<Double> xData1 = new ArrayList<>(Arrays.asList(new Double[] {1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d, 11d, 12d}));
        List<Double> yData1 = new ArrayList<>();
        yData1.add(1000000d);
        yData1.add(4000000d);
        yData1.add(6000000d);
        yData1.add(2000000d);
        yData1.add(5000000d);
        yData1.add(1000000d);
        yData1.add(2500000d);
        yData1.add(3000000d);
        yData1.add(6000000d);
        yData1.add(7000000d);
        yData1.add(4000000d);
        yData1.add(2000000d);
        // Column 2
        List<Double> xData2 = new ArrayList<>(Arrays.asList(new Double[] {1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d, 11d, 12d}));
        List<Double> yData2 = new ArrayList<Double>();
        yData2.add(1000000d);
        yData2.add(4000000d);
        yData2.add(6000000d);
        yData2.add(2000000d);
        yData2.add(5000000d);
        yData2.add(1000000d);
        yData2.add(2500000d);
        yData2.add(3000000d);
        yData2.add(6000000d);
        yData2.add(7000000d);
        yData2.add(4000000d);
        yData2.add(2000000d);

        ExampleChart<CategoryChart> exampleChart = new BarChartComp("Thống kê tài sản nợ", "Tháng", "Đồng", "Tài sản",
                xData1, yData1, "Khoản nợ", xData2, yData2);
        CategoryChart chart = exampleChart.getChart();
        new SwingWrapper<>(chart).displayChart();
    }
}