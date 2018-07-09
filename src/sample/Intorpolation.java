package sample;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.List;

public class Intorpolation {
    private List<Double> listOfX;
    private List<Double> listOfY;

    public Intorpolation(List<Double> listOfX, List<Double> listOfY){
        this.listOfX=listOfX;
        this.listOfY=listOfY;
    }

    public void drawIntorpolatedGraph(XYChart.Series<Number, Number> series, LineChart lineChart, double min, double max){
        for (double i=min-1;i<max+1;i+=0.1){
            series.getData().add(new XYChart.Data<Number, Number>(i, interpolate(i)));
        }
        lineChart.getData().add(series);
    }

    public double interpolate(double x) {
        double result = 0;
        for (int t = 0; t < listOfX.size (); t++) {
            double yl = listOfY.get (t);
            for (int i = 0; i < listOfX.size () - 1; i++) {
                if (t == 0 || i >= t)
                    yl *= (x - listOfX.get (i + 1)) / (listOfX.get (t) - listOfX.get (i + 1));
                else yl *= (x - listOfX.get (i )) / (listOfX.get (t) - listOfX.get (i ));
            }
            result += yl;
        }
        return result;
    }

}
