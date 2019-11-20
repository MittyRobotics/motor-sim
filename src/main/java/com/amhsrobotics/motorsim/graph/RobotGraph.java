package com.amhsrobotics.motorsim.graph;

import com.amhsrobotics.motorsim.math.Conversions;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class RobotGraph extends JFrame{

    XYSeries robotPosSeries;
    XYSeries robotVisualSeries;

    XYPlot plot;

    private static RobotGraph instance = new RobotGraph();

    public static RobotGraph getInstance(){
        return instance;
    }

    private RobotGraph() {

        robotPosSeries = new XYSeries("Robot Pos", false);
        robotVisualSeries = new XYSeries("Robot Visual", false);
        final XYSeriesCollection data = new XYSeriesCollection(robotPosSeries);
        data.addSeries(robotVisualSeries);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Robot Graph",
                "Time (seconds)",
                "Position (in), Velocity (in/s)",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        chart.setBackgroundPaint(new Color(71, 71, 71));
        chart.getTitle().setPaint(new Color(158, 159, 157));

        chart.getLegend().setBackgroundPaint(new Color(71, 71, 71));
        LegendTitle title = chart.getLegend();
        title.setItemPaint(new Color(158, 159, 157));

        XYPlot plot = (XYPlot) chart.getPlot();
        this.plot = plot;
        plot.setRenderer(new CustomColorRenderer(true,true,new Rectangle(2,2)));
        plot.setBackgroundPaint(Color.DARK_GRAY);

        plot.getDomainAxis().setLabelPaint(new Color(158, 159, 157));
        plot.getRangeAxis().setLabelPaint(new Color(158, 159, 157));
        plot.getDomainAxis().setTickLabelPaint(new Color(158, 159, 157));
        plot.getRangeAxis().setTickLabelPaint(new Color(158, 159, 157));

        plot.setDomainGridlinePaint(new Color(0, 0, 0, 180));
        plot.setRangeGridlinePaint(new Color(0, 0, 0, 180));


        final ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(800, 800));
        panel.setBackground(new Color(71, 71, 71));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        resizeGraph(-100,100,-100,100);
    }

    public void resizeGraph(double lowerBound, double upperBound, double leftBound, double rightBound){
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setRange(leftBound, rightBound);
        domain.setVerticalTickLabels(true);
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setRange(lowerBound, upperBound);
    }

    public void graphRobot(double x, double y, double heading, double width, double length) {

        robotVisualSeries.clear();

        heading = -heading;
        double x1 = x + Math.cos(Math.toRadians(heading))*(width/2);
        double y1 = y + Math.sin(Math.toRadians(heading))*(width/2);
        double x3 = x1 + Math.cos(Math.toRadians(90+heading))*(length/2);
        double y3 = y1 + Math.sin(Math.toRadians(90+heading))*(length/2);

        double x2 = x + Math.cos(Math.toRadians(heading))*(width/2);
        double y2 = y + Math.sin(Math.toRadians(heading))*(width/2);
        double x4 = x2 - Math.cos(Math.toRadians(90+heading))*(length/2);
        double y4 = y2 - Math.sin(Math.toRadians(90+heading))*(length/2);


        double x5 = x - Math.cos(Math.toRadians(heading))*(width/2);
        double y5 = y - Math.sin(Math.toRadians(heading))*(width/2);
        double x7 = x5 + Math.cos(Math.toRadians(90+heading))*(length/2);
        double y7 = y5 + Math.sin(Math.toRadians(90+heading))*(length/2);

        double x6 = x - Math.cos(Math.toRadians(heading))*(width/2);
        double y6 = y - Math.sin(Math.toRadians(heading))*(width/2);
        double x8 = x6 - Math.cos(Math.toRadians(90+heading))*(length/2);
        double y8 = y6 - Math.sin(Math.toRadians(90+heading))*(length/2);






        robotVisualSeries.add(x4,y4); //Bottom right
        robotVisualSeries.add(x3,y3); //Top right
        robotVisualSeries.add(x7,y7); //Top left
        robotVisualSeries.add(x8,y8); //Bottom left
        robotVisualSeries.add(x4,y4); //Bottom right


        robotPosSeries.clear();
        XYSeries series = new XYSeries("Robot Pos");
        robotPosSeries.add(x,y);
        robotPosSeries.add(x + (length/3) * Math.cos(Math.toRadians(90+heading)),y + (length/3) * Math.sin(Math.toRadians(90+heading)));


    }


    private class CustomColorRenderer extends XYLineAndShapeRenderer {

        private Shape shape;
        public CustomColorRenderer(boolean lines, boolean shapes, Shape shape) {
            super(lines, shapes);
            this.shape = shape;
        }

        @Override
        public Shape getItemShape(int row, int column) {
            return shape;
        }

        @Override
        public Paint getItemPaint(int row, int col) {
            return super.getItemPaint(row,col);
        }

    }
}
