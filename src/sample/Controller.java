package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Controller {
    @FXML LineChart<Number, Number> lineChart;

    @FXML TextField newX;

    @FXML Label newY;

    Intorpolation intorpolation;

    @FXML
    GridPane tblXY;

    @FXML
    void initialize(){
        XYChart.Series<Number, Number> series= new XYChart.Series<Number, Number>();
        Drawer.drawGraph(series, lineChart, -3, 5.5);
    }

    public void btnAction(ActionEvent actionEvent) {
        try {
            while(lineChart.getData ().size ()!=0) {
                lineChart.getData ().remove (lineChart.getData ().size () - 1);
            }
            intorpolation=null;
            List<Double> listOfX = new ArrayList<> ();
            List<Double> listOfY = new ArrayList<> ();
            ObservableList<Node> childrenOfTbl = tblXY.getChildren ();
            for (int i = 2; i < 30; i++) {
                TextField field = (TextField) childrenOfTbl.get (i);
                if (field.getText ().isEmpty () || field.getText () == null) {
                    continue;
                }
                if (i % 2 == 0) {
                    if (((TextField) childrenOfTbl.get (i + 1)).getText ().isEmpty ()) {
                        throw new Exception ();
                    }
                    double xvalue=Double.parseDouble (field.getText ());
                    if(listOfX.contains (xvalue)){
                     throw new Exception ();
                    }
                    listOfX.add (xvalue);
                } else {
                    if (((TextField) childrenOfTbl.get (i - 1)).getText ().isEmpty ()) {
                        throw new Exception ();
                    }
                    listOfY.add (Double.parseDouble (field.getText ()));
                }
            }
            intorpolation = new Intorpolation (listOfX, listOfY);
            int minIndex = listOfX.indexOf(Collections.min(listOfX));
            int maxIndex = listOfX.indexOf(Collections.max(listOfX));
            double min=listOfX.get (minIndex);
            double max=listOfX.get (maxIndex);
            XYChart.Series<Number, Number> series= new XYChart.Series<Number, Number>();
            Drawer.drawGraph(series, lineChart, min, max);
            XYChart.Series<Number, Number> series1 = new XYChart.Series<Number, Number> ();
            intorpolation.drawIntorpolatedGraph (series1, lineChart, min, max);
            XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number> ();
            Drawer.drawPoints(series2, lineChart, listOfX, listOfY);
            newY.setText("y=");
            newX.setText ("");


        } catch (Exception e) {
            XYChart.Series<Number, Number> series= new XYChart.Series<Number, Number>();
            Drawer.drawGraph(series, lineChart, -3, 5.5);
            DialogManager.showErrorDialog ("error", "X and Y have to be in pairs, have to be numbers, Xs have to be unique");
        }
    }

    public void btnFindNewY(ActionEvent actionEvent) {
        try{
            if (intorpolation==null){
                newY.setText("there is no function!!!");
            }
            else {
                double valueOfNewX = Double.parseDouble (newX.getText ());
                double valueOfNewY = intorpolation.interpolate (valueOfNewX);
                newY.setText ("y = " + String.valueOf (valueOfNewY));
            }
        }
        catch (Exception e){
            newY.setText("x should be a number");
        }
    }
}
