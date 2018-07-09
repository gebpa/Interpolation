package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.List;

public class Drawer {
    public static double function(double x){
        double y=2*x*x-5*x+6;
        return y;
    }
    public static void drawGraph(XYChart.Series<Number, Number> series, LineChart lineChart, double min, double max){
        for (double i=min-1;i<max+1;i+=0.1){
            series.getData().add(new XYChart.Data<Number, Number>(i, function(i)));
        }
        lineChart.getData().add(series);
    }
    public static void drawPoints(XYChart.Series<Number, Number> series, LineChart lineChart,  List<Double> listOfX,  List<Double> listOfY){
        for (int i=0; i<listOfX.size ();i++ ){
            series.getData().add(new XYChart.Data<Number, Number>(listOfX.get (i), listOfY.get (i)));
        }
        lineChart.getData().add(series);
    }

}
