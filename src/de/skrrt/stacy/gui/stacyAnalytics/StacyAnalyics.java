package de.skrrt.stacy.gui.stacyAnalytics;

import de.skrrt.stacy.gui.changeValue.EditPositionController;
import de.skrrt.stacy.gui.mainScreen.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.fx.ChartCanvas;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.fx.interaction.ChartMouseListenerFX;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.time.*;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.fx.FXGraphics2D;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class StacyAnalyics {

    private StacyAnalyticsController stacyAnalyticsController;
    private AnchorPane pane;
    private Stage stage;
    private Main main;

    public StacyAnalyics(Main main) {
        this.main = main;
        FXMLLoader loader = new FXMLLoader(StacyAnalyics.class.getResource("StacyAnalyticsWindow.fxml"));
        try{
            pane = loader.load();
            stacyAnalyticsController = loader.getController();
        }catch (IOException e){
            e.printStackTrace();
        }
        stage = new Stage();
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartViewer viewer = new ChartViewer(chart);
        viewer.addChartMouseListener(new MouseListener());
        stage.setScene(new Scene(viewer));
        stage.setTitle("S.T.A.C.Y. Analytics");
        stage.setWidth(1152);
        stage.setHeight(648);

        Timeline updater = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
            JFreeChart newChart = createChart(createDataset());
            viewer.setChart(newChart);
        }));
        updater.setCycleCount(Animation.INDEFINITE);
        updater.play();
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "S.T.A.C.Y. - Analystics",    // title
                null,             // x-axis label
                "Price",      // y-axis label
                dataset);
        String fontName = "Palatino";
        chart.getTitle().setFont(new Font(fontName, Font.BOLD, 18));
        chart.addSubtitle(new TextTitle("analyse for 1m Trendtrading",
                new Font(fontName, Font.PLAIN, 14)));
        //chart.setBackgroundPaint(new Color(49, 50, 53));
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.getDomainAxis().setLowerMargin(0.0);
        plot.getDomainAxis().setLabelFont(new Font(fontName, Font.BOLD, 14));
        plot.getDomainAxis().setTickLabelFont(new Font(fontName, Font.PLAIN, 12));
        plot.getRangeAxis().setLabelFont(new Font(fontName, Font.BOLD, 14));
        plot.getRangeAxis().setTickLabelFont(new Font(fontName, Font.PLAIN, 12));
        chart.getLegend().setItemFont(new Font(fontName, Font.PLAIN, 14));
        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.getLegend().setHorizontalAlignment(HorizontalAlignment.CENTER);
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setDefaultShapesVisible(false);
            renderer.setDrawSeriesLineAsPath(true);
            // set the default stroke for all series
            renderer.setAutoPopulateSeriesStroke(false);
            renderer.setDefaultStroke(new BasicStroke(3.0f));
            renderer.setSeriesPaint(0, Color.GREEN);
            renderer.setSeriesPaint(1, Color.RED);
        }
        return chart;
    }

    private XYDataset createDataset() {
        float[] chart = main.getBot().getBitmexAPI().getChart();
        float[] chartReverse = new float[chart.length];
        for (int i = 0; i < chartReverse.length; i++){
            chartReverse[i] = chart[chartReverse.length - i - 1];
        }
        TimeSeries s1 = new TimeSeries("XBT Price");
        for (int i = 0; i < chartReverse.length; i++){
            if(i <= 60){
                s1.add(new Minute(i, new Hour(1, new Day())), chartReverse[i]);
            }else if(i <= 120){
                s1.add(new Minute(i - 60, new Hour(2, new Day())), chartReverse[i]);
            }else  if (i <= 180){
                s1.add(new Minute(i - 120, new Hour(3, new Day())), chartReverse[i]);
            }else  if (i <= 240){
                s1.add(new Minute(i - 180, new Hour(4, new Day())), chartReverse[i]);
            }else if(i <= 300){
                s1.add(new Minute(i - 240, new Hour(5, new Day())), chartReverse[i]);
            }else if(i <= 360){
                s1.add(new Minute(i - 300, new Hour(6, new Day())), chartReverse[i]);
            }else if(i <= 420){
                s1.add(new Minute(i - 360, new Hour(7, new Day())), chartReverse[i]);
            }else if(i <= 480){
                s1.add(new Minute(i - 420, new Hour(8, new Day())), chartReverse[i]);
            }else if(i <= 540){
                s1.add(new Minute(i - 480, new Hour(9, new Day())), chartReverse[i]);
            }else if(i <= 600){
                s1.add(new Minute(i - 540, new Hour(10, new Day())), chartReverse[i]);
            }
        }
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        return dataset;
    }

    public void showStage(){
        stage.show();
    }
}
