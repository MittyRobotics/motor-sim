package com.amhsrobotics.motorsim.graph;

import com.amhsrobotics.motorsim.math.Conversions;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class Graph {
	
	
	XYSeries positionSeries;
	XYSeries velocitySeries;
	XYSeries voltageSeries;
	XYSeries setpointSeries;
	XYSeries errorSeries;
	
	public Graph(String name) {
		
		positionSeries = new XYSeries("Position", false);
		velocitySeries = new XYSeries("Velocity", false);
		voltageSeries = new XYSeries("Voltage", false);
		setpointSeries = new XYSeries("Setpoint", false);
		errorSeries = new XYSeries("Error", false);

		
		final XYSeriesCollection data = new XYSeriesCollection(positionSeries);
		data.addSeries(velocitySeries);
		data.addSeries(voltageSeries);
		data.addSeries(setpointSeries);
		data.addSeries(errorSeries);
		
		final JFreeChart chart = ChartFactory.createXYLineChart(
				name,
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
		plot.setBackgroundPaint(Color.DARK_GRAY);
		
		plot.getDomainAxis().setLabelPaint(new Color(158, 159, 157));
		plot.getRangeAxis().setLabelPaint(new Color(158, 159, 157));
		plot.getDomainAxis().setTickLabelPaint(new Color(158, 159, 157));
		plot.getRangeAxis().setTickLabelPaint(new Color(158, 159, 157));
		
		plot.setDomainGridlinePaint(new Color(0, 0, 0, 180));
		plot.setRangeGridlinePaint(new Color(0, 0, 0, 180));
		
		
		final ChartPanel panel = new ChartPanel(chart);
		panel.setPreferredSize(new java.awt.Dimension(800, 600));
		JFrame f = new JFrame();
		panel.setBackground(new Color(71, 71, 71));
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.add(panel);
		
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
	
	public void addPosition(double position, double time) {
		//convert to inches
		positionSeries.add(time, position  * Conversions.M_TO_IN);
	}
	public void addVelocity(double velocity, double time) {
		//convert to inches
		velocitySeries.add(time, velocity * Conversions.M_TO_IN);
	}
	
	public void addVoltage(double voltage, double time) {
		//convert to inches
		voltageSeries.add(time, voltage);
	}
	public void addSetpoint(double setpoint, double time) {
		//convert to inches
		setpointSeries.add(time, setpoint  * Conversions.M_TO_IN);
	}
	public void addError(double error, double time) {
		//convert to inches
		errorSeries.add(time, error);
	}
	private class CustomColorRenderer extends XYLineAndShapeRenderer {
		
		private Shape shape;
		private Color color;
		
		public CustomColorRenderer(boolean lines, boolean shapes, Shape shape, Color color) {
			super(lines, shapes);
			this.shape = shape;
			this.color = color;
		}
		
		@Override
		public Shape getItemShape(int row, int column) {
			return shape;
		}
		
		@Override
		public Paint getItemPaint(int row, int col) {
			return color;
		}
		
	}
}