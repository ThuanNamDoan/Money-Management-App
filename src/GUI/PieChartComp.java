
package GUI;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;
import java.awt.Color;
import java.util.Hashtable;

public class PieChartComp implements ExampleChart<PieChart> {

    private String nameChart;
    private int width;
    private int height;
    private Hashtable<String, Integer> jars = new Hashtable<String, Integer>();

    public PieChartComp(String nameChart, int width, int height, Hashtable<String, Integer> jars) {
        this.nameChart = nameChart;
        this.width = width;
        this.height = height;
        this.jars = jars;
    }

    @Override
    public PieChart getChart() {

        // Create Chart
        PieChart chart =
                new PieChartBuilder().width(width).height(height).title(nameChart).build();

        // Customize Chart
        Color[] sliceColors =
                new Color[] {
                        new Color(253, 3, 185),
                        new Color(54, 43, 240),
                        new Color(255, 163, 0),
                        new Color(128, 0, 255),
                        new Color(88, 191, 48),
                        new Color(186, 28, 67),
                };
        chart.getStyler().setSeriesColors(sliceColors);
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setChartBackgroundColor(new Color (255,255,255));
        chart.getStyler().setPlotBorderVisible(false);

        // Series
        jars.forEach(
                (k, v) -> chart.addSeries(k,v));

        return chart;
    }

    @Override
    public String getExampleChartName() {

        return getClass().getSimpleName() + " - Pie Chart Custom Color Palette";
    }
  
  /*
  public static void main(String[] args) {
    ExampleChart<PieChart> exampleChart = new PieChartComp();
     PieChart chart = exampleChart.getChart();
    
     JFrame frame = new JFrame("JFrame Example"); 
    frame.setSize(500, 500);  
   
    JPanel chartPanel = new XChartPanel(chart);
    frame.add(chartPanel);  
    frame.setVisible(true);  
    //new SwingWrapper<>(chart).displayChart();
  }*/
}